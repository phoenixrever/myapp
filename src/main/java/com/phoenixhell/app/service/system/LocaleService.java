package com.phoenixhell.app.service.system;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

import com.phoenixhell.app.Launcher;
import com.phoenixhell.app.config.MyControllerFactory;
import com.phoenixhell.app.contract.Translatable;
import com.phoenixhell.app.util.I18n;

/**
 * Locale.getISO3Language()：返回当前语言的 ISO 3 字母缩写，比如：
 * 
 * 英语 "eng"
 * 
 * 日语 "jpn"
 * 
 * 中文 "zho"
 * 
 * Objects.equals(a, b)：等价于 a != null && a.equals(b)，但避免空指针错误。
 * 
 * Optional.ifPresent()：如果值存在，就执行括号里的 lambda 表达式。
 */
public class LocaleService {

    public final static List<Locale> locales = List.of(Locale.ENGLISH, Locale.JAPANESE);
    private static Locale currentLocale;

    public static Locale getLocale() {
        if (currentLocale == null) {
            // default english
            currentLocale = Locale.ENGLISH;

            // 遍历支持的语言列表（locales），检查当前电脑的语言是否在支持列表中
            locales.forEach(locale -> {
                // 如果某个支持语言的 ISO3 语言码（比如 "eng"）与当前系统默认语言相同
                if (Objects.equals(locale.getISO3Language(), Locale.getDefault().getISO3Language())) {
                    currentLocale = locale;
                }
            });

            // 从用户设置中读取语言（例如设置文件中指定 "eng" 或 "jpn"）
            // 如果用户有设置，则尝试用该设置覆盖当前的 currentLocale
            UserSettingsService.getLocale().ifPresent(iso3Lang -> locales.forEach(locale -> {
                // 如果某个支持的 locale 的 ISO3 语言码与设置中的语言码相同
                if (Objects.equals(locale.getISO3Language(), iso3Lang)) {
                    currentLocale = locale;
                }
            }));

        }
        return currentLocale;
    }

    public static void setLocale(Locale locale) {
        currentLocale = locale;
        UserSettingsService.setLocale(currentLocale.getISO3Language());
        I18n.reloadBundle();
        Launcher.updateAppWindowTitle();
        MyControllerFactory.getControllers().forEach(Translatable::updateTexts);
    }
}
