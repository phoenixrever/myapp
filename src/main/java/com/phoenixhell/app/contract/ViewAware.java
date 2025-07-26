package com.phoenixhell.app.contract;

import javafx.scene.Parent;

/**
 * @PostConstruct 和initialize 方法类似，用于在控件注入完成后进行初始化。
 */
public interface ViewAware {
    Parent getView();

    default void initialize() {
        // 默认实现可以是空的，子类可以覆盖这个方法来进行初始化
    }
}
