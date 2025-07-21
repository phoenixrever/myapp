package com.phoenixhell.app.model;

/**
 * User 数据模型
 *
 * <p>
 * 常见 Java 数据对象类型说明：
 * <ul>
 * <li><b>Entity</b>：实体类，通常与数据库表一一对应，包含数据库映射注解（如 @Entity）。用于 ORM 持久化。</li>
 * <li><b>DTO（Data Transfer Object）</b>：数据传输对象，用于服务层/接口间传递数据，通常只包含需要传输的字段。</li>
 * <li><b>VO（View Object）</b>：视图对象，专为前端界面展示设计，可能包含组合字段或格式化数据。</li>
 * <li><b>POJO（Plain Old Java Object）</b>：普通 Java 对象，无特定约束，通常指简单的 Java
 * Bean。</li>
 * <li><b>Model</b>：泛指业务数据结构，可以是 Entity、DTO、VO、POJO 的统称。</li>
 * </ul>
 * 本类为简单的 Model/POJO 示例。
 * </p>
 */
public class User {
  private String name;
  private int age;

  public User() {
    // 默认构造函数
  }

  public User(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }
}