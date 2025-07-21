package com.phoenixhell.app.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Control {
  String value() default ""; // 指定控件 id（可选）
}
