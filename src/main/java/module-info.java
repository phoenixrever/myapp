/**
 * module-info.java
 * 
 * 这是 Java 模块系统的模块声明文件，定义了本模块的依赖和对外暴露的包。
 * 
 * - open module myapp: 使用 open 关键字，允许反射访问（如依赖框架、序列化等场景）。
 * - requires ...: 声明本模块依赖的其他模块。
 * - exports ...: 声明本模块对外暴露的包，允许其他模块访问这些包中的 public 类。
 * 
 * 只有当你写的模块要让“依赖你的模块的其他模块”也自动获得 JavaFX 时，才需要 requires transitive，这不是常见场景。
 * 
 * 不同包：只要在同一个模块里，不需要 requires。
 * 不同模块：必须 requires，否则无法访问。
 * 
 * 建议：
 * 你只需要为用到的第三方库的模块名加 requires，不用为自己模块下的包加。
 * 
 * 
 * 备注
 * 这个错误 java.lang.module.FindException: Module myapp not found
 * 确认下target/classes/module-info.class 存在。
 * mvn clean package
 * 
 * vscode 不加 transitive 会编译警告 重点解释：
 * 
 * 这个警告其实是 IDE（Java 编译器）提示你，如果将来别人依赖你的模块，可能会访问不到 Button 类。
 * 但 如果你当前的项目里没有其他模块依赖你，你不用管这个警告，程序照样能跑。
 * 只有当你做库或者模块被别人用时，才严格处理 requires transitive。
 */
module myapp {
  // JavaFX 控件模块，包含 UI 控件（如 Button、Label、Stage 等）
  requires transitive javafx.controls;
  // JavaFX 图形模块，包含图形渲染相关的类（如 Scene、Canvas 等）
  // 你模块直接访问 javafx.graphics，没问题。使用你模块的模块也能访问到 javafx.graphics 里的类。
  requires transitive javafx.graphics;
  // JavaFX FXML 模块，支持 FXML 文件（如不用 FXML 可移除）
  requires javafx.fxml;
  // Java 标准桌面 API，部分 Swing/AWT 相关功能需要
  requires java.desktop;

  // 工具类依赖
  requires org.apache.commons.lang3; // Apache Commons Lang 工具类库
  requires org.apache.commons.io; // Apache Commons IO 工具类库
  requires org.apache.commons.collections4; // Apache Commons Collections 工具类库
  // requires org.apache.logging.log4j; // Apache Log4j 日志库
  requires org.reflections; // Reflections 反射扫描库

  // JSON 相关
  requires transitive com.fasterxml.jackson.databind; // Jackson Databind
  // requires com.fasterxml.jackson.core; // jackson JSON 解析库

  // 其他依赖
  requires atlantafx.base; // AtlantisFX 主题库
  requires org.kordamp.ikonli.core; // Ikonli 图标库核心
  requires org.kordamp.ikonli.fontawesome5; // Ikonli FontAwesome5 图标支持
  requires org.kordamp.ikonli.javafx; // Ikonli JavaFX 支持
  requires org.kordamp.ikonli.materialdesign2; // Ikonli MaterialDesign2 图标支持
  requires net.harawata.appdirs; // AppDirs 跨平台应用目录库
  requires org.tinylog.api; // Tinylog 日志库
  requires org.tinylog.impl;
  requires javafx.base; // Tinylog 实现

  // 对外暴露的包，允许其他模块访问这些包中的 public 类

  /**
   * exports 只导出指定包，不包含子包。
   * 同一个模块内不用 requires 自己的包，但要 exports 需要被外部访问的包。
   * 访问第三方库，必须 requires。
   */
  exports com.phoenixhell.app;
  exports com.phoenixhell.app.service;
  exports com.phoenixhell.app.controller;
  exports com.phoenixhell.app.view;
  exports com.phoenixhell.app.util;
  exports com.phoenixhell.app.exception;
  exports com.phoenixhell.app.config;
  exports com.phoenixhell.app.model;
  exports com.phoenixhell.app.api;
  // exports com.phoenixhell.app.i18n;

}
