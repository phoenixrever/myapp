package com.phoenixhell.app.util;

import java.util.ResourceBundle;
import com.phoenixhell.app.service.LocaleService;

public class I18n {
  private static ResourceBundle bundle = ResourceBundle.getBundle("App", LocaleService.getLocale());

  public static void reloadBundle() {
    bundle = ResourceBundle.getBundle("App", LocaleService.getLocale());
  }

  // 统一获取 key
  public static String get(String key) {
    return bundle.getString(key);
  }

  // 可选：获取带默认值的 key
  public static String getOrDefault(String key, String defaultValue) {
    return bundle.containsKey(key) ? bundle.getString(key) : defaultValue;
  }
}
