package com.phoenixhell.app.service;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.CupertinoLight;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import org.tinylog.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 管理用户设置：负责加载、保存和修改应用程序的设置。
 */
public class UserSettingsService {

    // 主题常量：深色与浅色
    public static final String DARK = "DARK";
    public static final String LIGHT = "LIGHT";

    // jackson 用于 JSON 序列化与反序列化
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 各种设置项的 key 常量
    private static final String WINDOW_X = "WINDOW_X";
    private static final String WINDOW_Y = "WINDOW_Y";
    private static final String WINDOW_WIDTH = "WINDOW_WIDTH";
    private static final String WINDOW_HEIGHT = "WINDOW_HEIGHT";
    private static final String WINDOW_MAXIMIZED = "WINDOW_MAXIMIZED";
    private static final String SCREENS_HASH = "SCREENS_HASH";
    private static final String LOCALE = "LOCALE";
    private static final String THEME = "THEME";
    private static final String DEMO = "DEMO";

    // 保存所有设置的 Map
    private static Map<String, String> settings = new HashMap<>();

    // 设置文件名（不带扩展名）
    public static final String APP_SETTINGS_FILE = "settings";

    /**
     * 获取设置文件的完整路径
     */
    private static File getSettingsFile() throws IOException {
        String path = LocalDirService.getUserDataDirPath(); // 获取用户目录路径
        return new File(path + File.separator + APP_SETTINGS_FILE); // 构造文件路径
    }

    /**
     * 从文件中加载设置（只在第一次访问或为空时加载）
     */
    private static synchronized void loadSettings() {
        try {
            File file = getSettingsFile();
            if (!file.exists()) {
                // 如果文件不存在，创建空文件
                file.createNewFile();
            }

            // 指定读取的类型为 Map<String, String>
            FileReader f = new FileReader(file);
            settings = objectMapper.readValue(f, new TypeReference<Map<String, String>>() {
            });

            // 如果读取失败或为空则重新初始化为空 Map
            if (settings == null) {
                settings = new HashMap<>();
            }

        } catch (IOException e) {
            // 如果读取出错，初始化为默认空设置
            settings = new HashMap<>();
        }
    }

    /**
     * 将当前设置保存到文件中（覆盖）
     */
    public static synchronized void saveSettings() {
        try {
            File filePath = getSettingsFile();
            FileWriter f = new FileWriter(filePath);
            objectMapper.writeValue(f, settings); // 写入 JSON 格式 自动 flush 自动 close

        } catch (IOException e) {
            Logger.error(" caught a " + e.getClass() + "\n with message: " + e.getMessage());
            throw new RuntimeException(e); // 抛出异常中断程序
        }
    }

    /**
     * 获取当前设置（首次访问时加载）
     */
    private static Map<String, String> getSettings() {
        if (settings == null || settings.isEmpty()) {
            loadSettings(); // 延迟加载
        }
        return settings;
    }

    // **************************************************
    // * 窗口设置
    // **************************************************

    /**
     * 设置窗口的位置、大小、最大化状态和屏幕 Hash（用于多显示器配置）
     */
    public static void setWindowPosition(double x, double y, double width, double height, boolean maximized,
            String screensHash) {
        getSettings().put(WINDOW_X, String.valueOf(x));
        getSettings().put(WINDOW_Y, String.valueOf(y));
        getSettings().put(WINDOW_WIDTH, String.valueOf(width));
        getSettings().put(WINDOW_HEIGHT, String.valueOf(height));
        getSettings().put(WINDOW_MAXIMIZED, String.valueOf(maximized));
        getSettings().put(SCREENS_HASH, screensHash);
    }

    public static Optional<Double> getWindowX() {
        return Optional.ofNullable(getSettings().get(WINDOW_X)).map(Double::valueOf);
    }

    public static Optional<Double> getWindowY() {
        return Optional.ofNullable(getSettings().get(WINDOW_Y)).map(Double::valueOf);
    }

    public static Optional<Double> getWindowWidth() {
        return Optional.ofNullable(getSettings().get(WINDOW_WIDTH)).map(Double::valueOf);
    }

    public static Optional<Double> getWindowHeight() {
        return Optional.ofNullable(getSettings().get(WINDOW_HEIGHT)).map(Double::valueOf);
    }

    public static Optional<Boolean> getWindowMaximized() {
        return Optional.ofNullable(getSettings().get(WINDOW_MAXIMIZED)).map(Boolean::valueOf);
    }

    public static Optional<String> getScreensHash() {
        return Optional.ofNullable(getSettings().get(SCREENS_HASH));
    }

    // **************************************************
    // * 本地语言设置
    // **************************************************

    public static Optional<String> getLocale() {
        return Optional.ofNullable(getSettings().get(LOCALE));
    }

    public static void setLocale(String str) {
        getSettings().put(LOCALE, str);
    }

    // **************************************************
    // * 主题设置（浅色 / 深色）
    // **************************************************

    public static String getTheme() {
        return Optional.ofNullable(getSettings().get(THEME)).orElse(LIGHT); // 默认 LIGHT
    }

    public static void setTheme(String theme) {
        getSettings().put(THEME, theme);
        initTheme(); // 设置主题时同时应用
    }

    /**
     * 初始化当前主题样式（修改 JavaFX 样式）
     */
    public static void initTheme() {
        if (UserSettingsService.LIGHT.equals(UserSettingsService.getTheme())) {
            Application.setUserAgentStylesheet(new CupertinoLight().getUserAgentStylesheet());
        } else {
            Application.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        }
    }

    // **************************************************
    // * DEMO 示例文本（可用于临时存储）
    // **************************************************

    public static String getSavedText() {
        return Optional.ofNullable(getSettings().get(DEMO)).orElse("");
    }

    public static void setSavedText(String str) {
        getSettings().put(DEMO, str);
    }
}
