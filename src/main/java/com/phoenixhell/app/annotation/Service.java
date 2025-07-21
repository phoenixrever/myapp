package com.phoenixhell.app.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Service {
  // 注入业务服务组件
}
