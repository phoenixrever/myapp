package com.phoenixhell.app;

import org.junit.jupiter.api.Test;

/**
 * 为什么会找不到类？
 * 因为使用Main的适合 需要 test里面也写 module-info.java
 * 你的项目是模块化的（有 module-info.java），模块名是 myapp（举例）
 * 
 * 你直接用 VS Code 右键“运行”ExampleTest，VS Code 启动 Java 时会尝试：
 * 
 * java --module-path ... --module com.phoenix.app.ExampleTest
 * 但其实你的模块名是 myapp，类名是 com.phoenix.app.ExampleTest
 * 
 * 这里 VS Code 没告诉 Java 运行时“模块名”，导致模块系统找不到类。
 * 
 * 1. JUnit 运行测试的原理
 * JUnit 不依赖 main 方法来启动测试。
 * 
 * 它有自己的测试运行器（Test Runner），会自动扫描测试类和测试方法，然后调用它们执行。
 * 
 * VS Code 的 Java Test Runner 插件或 Maven 的 mvn test 会调用 JUnit 运行器，绕过了传统 main
 * 方法启动流程。
 * 
 * 2. 直接用 main 启动的局限
 * 模块化项目启动时，必须明确告诉 JVM 模块名 和 类的全限定名，否则 JVM 无法找到类（尤其测试类通常没模块声明）。
 */
public class ExampleTest {

  @Test
  public void testExample() {
    // 这里可以写你的测试逻辑 注意 vscode 输出在 Debug Console里面
    System.out.println("Example test executed successfully.");
  }
}