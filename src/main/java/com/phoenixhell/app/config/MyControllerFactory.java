package com.phoenixhell.app.config;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

import com.phoenixhell.app.annotation.Control;
import com.phoenixhell.app.annotation.View;
import com.phoenixhell.app.api.ViewAware;
import com.phoenixhell.app.annotation.Service;

import javafx.scene.Node;
import javafx.scene.Parent;
import org.reflections.Reflections;

/**
 * MyControllerFactory 是一个简易的 IoC 容器实现。
 * 
 * 功能：
 * 1. 扫描指定包中所有 Controller 类（类名包含 "Controller" 的类）。
 * 2. 为 Controller 创建单例实例（bean）。
 * 3. 注入 Controller 中标注了 @Service 的业务服务。
 * 4. 注入 Controller 中标注了 @View 的 JavaFX 根视图节点（例如 VBox、AnchorPane）。
 * 5. 注入 Controller 中标注了 @Control 的具体 JavaFX 控件。
 * 
 * 注入规则：
 * - @Service：自动创建并注入单例服务实例。
 * - @View：自动创建并注入视图根节点的实例，视图实例缓存，避免重复创建。
 * - @Control：在 @View 注入的视图中查找对应控件并注入。
 * 支持通过控件 id 精准注入，否则必须视图中唯一匹配对应类型，否则报错。
 * 
 * 设计目标：
 * - 简单、直观、易用。
 * - 支持基本的 Controller-Service-View 模式。
 * - 避免复杂的 Spring 依赖，轻量实现。
 */
public class MyControllerFactory {

  /**
   * Bean 缓存，存储 Controller 和 Service 的单例实例
   * key: 类对象，value: 该类的单例实例
   */
  private static final Map<Class<?>, Object> beanCache = new HashMap<>();

  /**
   * View 缓存，存储 View 根节点实例（例如 VBox、AnchorPane 等）
   * key: View 类对象，value: View 根节点实例
   */
  private static final Map<Class<? extends Parent>, Parent> viewCache = new HashMap<>();

  /**
   * 扫描指定包路径，自动发现并实例化所有 Controller 类
   * 并对其进行视图和控件注入
   * 
   * @param basePackage 要扫描的包名（例如 "com.phoenixhell.app.controller"）
   * @throws Exception 扫描或注入过程中可能抛出的异常
   */
  public static void scan(String basePackage) {
    // 使用 Reflections 库扫描指定包下的所有类
    Reflections reflections = new Reflections(basePackage);

    // 获取所有ViewAware子类
    Set<Class<? extends ViewAware<?>>> viewAwareClasses = reflections
        .getSubTypesOf((Class<ViewAware<?>>) (Class<?>) ViewAware.class);

    // 遍历所有类，找出类名包含 Controller 的类进行处理
    for (Class<?> clazz : viewAwareClasses) {
      if (ViewAware.class.isAssignableFrom(clazz)) {
        // 创建 Controller 实例（或从缓存获取）
        Object controller = createBean(clazz);
        try {
          // 注入 Controller 中的 @View 标记字段（视图根节点）
          injectViews(controller);
          // 注入 Controller 中的 @Control 标记字段（具体控件）
          injectControls(controller);
          // 注入完毕后，调用初始化方法（initialize() 或 @PostConstruct）
          invokeInitMethods(controller);
        } catch (Exception e) {
          throw new RuntimeException("注入 Controller 失败：" + clazz.getName(), e);
        }
      }
    }
  }

  /**
   * 创建某个类的实例（支持 Controller 和 Service）
   * 并缓存到 beanCache 避免重复创建
   * 
   * @param clazz 目标类类型
   * @param <T>   泛型类型
   * @return 实例对象
   * @throws RuntimeException 创建失败时抛出
   */
  public static <T> T createBean(Class<T> clazz) {
    try {
      // 如果已缓存，直接返回
      if (beanCache.containsKey(clazz)) {
        return clazz.cast(beanCache.get(clazz));
      }

      // 新建实例
      T instance = clazz.getDeclaredConstructor().newInstance();
      beanCache.put(clazz, instance);

      // 递归注入该实例的 @Service 字段
      injectServices(instance);

      return instance;
    } catch (Exception e) {
      throw new RuntimeException("创建 Bean 失败：" + clazz.getName(), e);
    }
  }

  /**
   * 注入标注了 @Service 的字段
   * - 遍历实例的字段，找到所有 @Service 标注
   * - 为每个字段创建或获取对应的服务实例
   * - 注入到字段中
   * 
   * @param instance 需要注入的实例对象
   * @throws IllegalAccessException 反射设置字段值时可能抛出
   */
  private static void injectServices(Object instance) throws IllegalAccessException {
    Class<?> clazz = instance.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(Service.class))
        continue;

      Class<?> type = field.getType();
      field.setAccessible(true);

      // 从缓存中获取 Service 实例，如果没有就创建
      Object serviceInstance = beanCache.get(type);
      if (serviceInstance == null) {
        try {
          serviceInstance = type.getDeclaredConstructor().newInstance();
          beanCache.put(type, serviceInstance);
        } catch (Exception e) {
          throw new RuntimeException("创建 Service 失败：" + type.getName(), e);
        }
      }

      // 注入到字段
      field.set(instance, serviceInstance);
    }
  }

  /**
   * 注入标注了 @View 的字段（视图根节点）
   * - 获取字段类型（必须是 JavaFX Parent 的子类）
   * - 从 viewCache 获取视图实例，没有则创建并缓存
   * - 注入到 Controller 的字段
   * 
   * @param controller Controller 实例
   * @throws IllegalAccessException 反射设置字段值时可能抛出
   */
  private static void injectViews(Object controller) throws IllegalAccessException {
    Class<?> clazz = controller.getClass();
    for (Field field : clazz.getDeclaredFields()) {
      if (!field.isAnnotationPresent(View.class))
        continue;

      Class<? extends Parent> viewClass = (Class<? extends Parent>) field.getType();

      // 获取或创建视图实例
      Parent viewInstance = viewCache.computeIfAbsent(viewClass, (vc) -> {
        try {
          return vc.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
          throw new RuntimeException("创建 View 实例失败: " + vc.getName(), e);
        }
      });

      // 类型安全检查
      if (!field.getType().isAssignableFrom(viewClass)) {
        throw new RuntimeException("字段类型不兼容：" + field.getType() + " <- " + viewClass);
      }

      field.setAccessible(true);
      field.set(controller, viewInstance);
    }
  }

  /**
   * 注入 Controller 中标注了 {@code @Control} 的 JavaFX 控件字段。
   * 
   * 支持以下 3 种注入方式：
   * 
   * <pre>
   * {
   *   &#64;code
   *   // 1. 注入单个控件（推荐用法）
   *   &#64;Control("submitBtn") // 指定控件 ID
   *   private Button submit;
   *
   *   // 若未指定 ID，则尝试按字段名作为控件 ID 匹配
   *   @Control
   *   private TextField username;
   *
   *   // 若字段名也未匹配，则在视图中查找唯一匹配的类型（不唯一时报错）
   * }
   * </pre>
   *
   * <pre>{@code
   * // 2. 注入多个同类型控件（按类型查找）
   * @Control
   * private List<Button> buttons; // 查找所有 Button 控件注入到列表
   * }</pre>
   *
   * <pre>{@code
   * // 3. 注入多个控件为 Map（按控件 id 匹配）
   * @Control
   * private Map<String, Label> labelMap; // key: id, value: Label 控件
   * }</pre>
   * 
   * 控件的查找顺序如下（仅适用于单个控件注入）：
   * <ol>
   * <li>若 {@code @Control("id")} 指定 id，则直接通过 {@code lookup("#id")} 查找</li>
   * <li>若未指定 id，则尝试使用字段名作为控件 id 查找</li>
   * <li>若仍未找到，则在视图中查找唯一匹配字段类型的控件</li>
   * </ol>
   * 
   * <b>注意：</b>
   * <ul>
   * <li>所有被注入的字段必须是 JavaFX {@code Node} 类型或其子类。</li>
   * <li>控件必须存在于该 Controller 标注了 {@code @View} 的 JavaFX 视图中。</li>
   * <li>若为 {@code List<T>}，将查找视图中所有 T 类型控件；</li>
   * <li>若为 {@code Map<String, T>}，将查找视图中所有具有 id 的 T 类型控件，键为 id。</li>
   * </ul>
   *
   * @param controller Controller 实例对象
   * @throws Exception 控件查找失败或类型不匹配时抛出
   */
  private static void injectControls(Object controller) throws Exception {
    Class<?> clazz = controller.getClass();

    for (Field field : clazz.getDeclaredFields()) {
      Control controlAnno = field.getAnnotation(Control.class);
      if (controlAnno == null)
        continue;

      field.setAccessible(true);
      String id = controlAnno.value().trim(); // 控件 ID（可选）
      Class<?> fieldType = field.getType();

      // ========= 1. 处理 List<T> 类型 =========
      if (List.class.isAssignableFrom(fieldType)) {
        Class<?> genericType = getGenericType(field);
        if (!Node.class.isAssignableFrom(genericType)) {
          throw new RuntimeException("List 的泛型必须是 JavaFX Node 类型: " + field.getName());
        }

        List<Node> collected = new ArrayList<>();
        for (Parent view : getViewsFromController(controller)) {
          Set<Node> matches = view.lookupAll("*").stream()
              .filter(genericType::isInstance)
              .map(genericType::cast)
              .map(n -> (Node) n)
              .collect(Collectors.toSet());
          collected.addAll(matches);
        }

        field.set(controller, collected);
        continue;
      }

      // ========= 2. 处理 Map<String, T> 类型 =========
      if (Map.class.isAssignableFrom(fieldType)) {
        Class<?> valueType = getMapValueType(field);
        if (!Node.class.isAssignableFrom(valueType)) {
          throw new RuntimeException("Map 的值类型必须是 JavaFX Node 类型: " + field.getName());
        }

        Map<String, Node> resultMap = new HashMap<>();
        for (Parent view : getViewsFromController(controller)) {
          collectAllNodes(view).forEach(node -> {
            String nodeId = node.getId();
            if (nodeId != null && valueType.isInstance(node)) {
              resultMap.put(nodeId, node);
            }
          });
        }

        field.set(controller, resultMap);
        continue;
      }

      // ========= 3. 注入单个控件（普通字段） =========
      Node injectedNode = null;

      for (Parent view : getViewsFromController(controller)) {
        if (!id.isEmpty()) {
          // 优先使用注解中指定的 id 查找
          injectedNode = (Node) view.lookup("#" + id);
        } else {
          // 尝试使用字段名作为 id 查找
          injectedNode = (Node) view.lookup("#" + field.getName());

          if (injectedNode == null) {
            // 最后按类型唯一匹配
            Set<Node> candidates = view.lookupAll("*").stream()
                .filter(fieldType::isInstance)
                .map(fieldType::cast)
                .map(n -> (Node) n)
                .collect(Collectors.toSet());

            if (candidates.size() == 1) {
              injectedNode = candidates.iterator().next();
            } else if (candidates.size() > 1) {
              throw new RuntimeException("View " + view.getClass().getSimpleName() +
                  " 中存在多个 " + fieldType.getSimpleName() + "，请使用 @Control(\"id\") 精确注入");
            }
          }
        }

        if (injectedNode != null) {
          field.set(controller, injectedNode);
          break;
        }
      }

      // 注入失败则抛出异常
      if (field.get(controller) == null) {
        throw new RuntimeException("无法注入控件字段: " + field.getName());
      }
    }
  }

  /**
   * 根据类获取对应的单例 Bean（Controller 或 Service）
   * 
   * @param clazz 类对象
   * @param <T>   泛型
   * @return 缓存中的 Bean 实例，可能为 null
   */
  public static <T> T getBean(Class<T> clazz) {
    return clazz.cast(beanCache.get(clazz));
  }

  /**
   * 根据 View 类获取对应的视图实例
   * 
   * @param clazz View 类对象
   * @param <T>   泛型，继承 Parent
   * @return 缓存中的 View 实例，可能为 null
   */
  public static <T> T getView(Class<T> clazz) {
    return clazz.cast(viewCache.get(clazz));
  }

  /**
   * 获取 List<T> 字段的 T 类型（泛型类型）。
   *
   * @param field 字段对象
   * @return 泛型类型 T 的 Class
   */
  private static Class<?> getGenericType(Field field) {
    return (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType())
        .getActualTypeArguments()[0];
  }

  /**
   * 获取 Map<String, T> 字段中 T 的类型。
   *
   * @param field 字段对象
   * @return Map 值的泛型类型 T
   */
  private static Class<?> getMapValueType(Field field) {
    return (Class<?>) ((java.lang.reflect.ParameterizedType) field.getGenericType())
        .getActualTypeArguments()[1];
  }

  /**
   * 提取 Controller 中所有带 @View 注解的字段，并返回其 Parent 视图对象。
   *
   * @param controller 控制器实例
   * @return 所有注入的视图对象列表
   * @throws IllegalAccessException 反射访问异常
   */
  private static List<Parent> getViewsFromController(Object controller) throws IllegalAccessException {
    List<Parent> views = new ArrayList<>();
    for (Field field : controller.getClass().getDeclaredFields()) {
      if (!field.isAnnotationPresent(View.class))
        continue;
      field.setAccessible(true);
      Parent view = (Parent) field.get(controller);
      if (view != null)
        views.add(view);
    }
    return views;
  }

  /**
   * 递归收集某个视图中的所有 Node 控件（用于 Map 注入）。
   *
   * @param root 根节点
   * @return 所有子节点集合
   */
  private static Set<Node> collectAllNodes(Parent root) {
    Set<Node> allNodes = new HashSet<>();
    Queue<Parent> queue = new LinkedList<>();
    queue.add(root);

    while (!queue.isEmpty()) {
      Parent parent = queue.poll();
      for (Node child : parent.getChildrenUnmodifiable()) {
        allNodes.add(child);
        if (child instanceof Parent) {
          queue.add((Parent) child);
        }
      }
    }
    return allNodes;
  }

  /**
   * 在 Controller 中查找并调用初始化方法（支持两种方式）：
   * 
   * 1. 若存在 @PostConstruct 注解的方法（无参），则调用所有此类方法。
   * 2. 否则查找 initialize() 方法（无参）并调用。
   * 
   * 注意：只调用无参方法，若有参数将被忽略。
   *
   * 用法示例：
   * 
   * <pre>
   * {@code
   * &#64;PostConstruct
   * public void initData() { ... }
   * 
   * public void initialize() { ... }
   * }
   * </pre>
   *
   * @param controller 控制器实例
   */
  private static void invokeInitMethods(Object controller) {
    boolean postConstructInvoked = false;

    for (var method : controller.getClass().getDeclaredMethods()) {
      if (method.isAnnotationPresent(com.phoenixhell.app.annotation.PostConstruct.class) &&
          method.getParameterCount() == 0) {
        try {
          method.setAccessible(true);
          method.invoke(controller);
          postConstructInvoked = true;
        } catch (Exception e) {
          throw new RuntimeException("调用 @PostConstruct 方法失败: " + method.getName(), e);
        }
      }
    }

    // 如果没有使用 @PostConstruct 注解，则尝试调用 initialize()
    if (!postConstructInvoked) {
      try {
        var initMethod = controller.getClass().getMethod("initialize"); // public method
        if (initMethod.getParameterCount() == 0) {
          initMethod.invoke(controller);
        }
      } catch (NoSuchMethodException e) {
        // 忽略，表示未定义 initialize 方法
      } catch (Exception e) {
        throw new RuntimeException("调用 initialize() 方法失败: " + controller.getClass().getName(), e);
      }
    }
  }

}
