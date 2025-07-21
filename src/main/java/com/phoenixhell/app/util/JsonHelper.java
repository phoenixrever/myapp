package com.phoenixhell.app.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.phoenixhell.app.exception.JsonException;

/**
 * JSON 工具类，基于 Jackson 封装
 *
 * <p>
 * 示例：
 * </p>
 * 
 * <pre>{@code
 * // Java Bean 示例
 * public class User {
 *   public String name;
 *   public int age;
 *   // 构造函数、get/set 可选
 * }
 *
 * // 序列化
 * User user = new User();
 * user.name = "Alice";
 * user.age = 25;
 * String json = JsonHelper.toJson(user);
 * System.out.println(json);
 *
 * // 反序列化
 * User parsed = JsonHelper.fromJson(json, User.class);
 * System.out.println(parsed.name);
 *
 * // 格式化输出
 * String pretty = JsonHelper.toPrettyJson(user);
 * System.out.println(pretty);
 * }</pre>
 */
public class JsonHelper {

  private static final ObjectMapper DEFAULT_MAPPER;

  static {
    DEFAULT_MAPPER = new ObjectMapper();

    // 设置日期格式
    DEFAULT_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

    // 忽略未知属性
    DEFAULT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    // 序列化时包含 null 值字段
    DEFAULT_MAPPER.setSerializationInclusion(JsonInclude.Include.ALWAYS);

    // 允许字段名不加引号
    DEFAULT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

    // 允许使用单引号
    DEFAULT_MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

    // 注册自定义模块（可扩展）
    SimpleModule module = new SimpleModule();
    DEFAULT_MAPPER.registerModule(module);

    // 设置过滤器（非必须）
    DEFAULT_MAPPER.setFilterProvider(new SimpleFilterProvider().setFailOnUnknownId(false));
  }

  /**
   * 将对象序列化为 JSON 字符串
   */
  public static String toJson(Object obj) {
    if (obj == null)
      return null;
    try {
      return DEFAULT_MAPPER.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new JsonException("JSON 序列化失败: " + e.getMessage(), e);
    }
  }

  /**
   * 将对象序列化为格式化的 JSON 字符串
   */
  public static String toPrettyJson(Object obj) {
    if (obj == null)
      return null;
    try {
      return DEFAULT_MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new JsonException("格式化 JSON 序列化失败: " + e.getMessage(), e);
    }
  }

  /**
   * 将 JSON 字符串反序列化为指定类型对象
   */
  public static <T> T fromJson(String json, Class<T> clazz) {
    if (json == null || json.trim().isEmpty())
      return null;
    try {
      return DEFAULT_MAPPER.readValue(json, clazz);
    } catch (IOException e) {
      throw new JsonException("JSON 反序列化失败: " + e.getMessage(), e);
    }
  }

  /**
   * 将 JSON 字符串反序列化为泛型类型对象
   */
  public static <T> T fromJson(String json, TypeReference<T> typeRef) {
    if (json == null || json.trim().isEmpty())
      return null;
    try {
      return DEFAULT_MAPPER.readValue(json, typeRef);
    } catch (IOException e) {
      throw new JsonException("JSON 泛型反序列化失败: " + e.getMessage(), e);
    }
  }

  /**
   * 将 JSON 字符串转为 Map
   */
  public static Map<String, Object> toMap(String json) {
    return fromJson(json, new TypeReference<Map<String, Object>>() {
    });
  }

  /**
   * 将 Map 对象转为 JSON 字符串
   */
  public static String fromMap(Map<String, Object> map) {
    return toJson(map);
  }

  /**
   * 将 JSON 字符串转为 JsonNode（树结构）
   */
  public static JsonNode toTree(String json) {
    try {
      return DEFAULT_MAPPER.readTree(json);
    } catch (IOException e) {
      throw new JsonException("JSON 解析为 JsonNode 失败: " + e.getMessage(), e);
    }
  }

  /**
   * 获取底层 ObjectMapper 实例（用于特殊用途）
   */
  public static ObjectMapper getMapper() {
    return DEFAULT_MAPPER;
  }
}
