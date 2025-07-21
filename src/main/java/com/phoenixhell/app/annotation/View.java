package com.phoenixhell.app.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD) // 改为作用在字段上
@Retention(RetentionPolicy.RUNTIME)
public @interface View {
  // Class<? extends Parent> value() default Parent.class; // 指定 View 类型
}