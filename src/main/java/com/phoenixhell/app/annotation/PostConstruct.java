package com.phoenixhell.app.annotation;

package com.phoenixhell.app.annotation;

import java.lang.annotation.*;

/**
 * 用于标记 Controller 中的初始化方法（类似 Spring 的 @PostConstruct）。
 * 会在控件注入完成后自动调用。
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PostConstruct {
}
