package com.phoenixhell.app.api;

/**
 * Parent 是 JavaFX 里的类名，也用作泛型名容易混淆。建议你把泛型参数改成 T 或 V 比较清晰：
 */
public interface ViewAware<T extends javafx.scene.Parent> {
  T getView();

  default void initialize() {
    // 默认实现可以是空的，子类可以覆盖这个方法来进行初始化
  }
}