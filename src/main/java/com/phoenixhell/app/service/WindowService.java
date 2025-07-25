package com.phoenixhell.app.service;

import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 窗口服务类，用于管理应用窗口的状态和位置。
 * 包括设置初始位置、处理窗口关闭事件等。
 * 需要在应用启动时调用 setup 方法进行初始化。
 * 该服务会根据屏幕信息和用户设置来决定窗口的初始位置和大小。
 * 如果屏幕信息与上次相同，则使用上次保存的位置和大小，否则
 * 将窗口最大化。
 * 窗口关闭时会保存当前窗口的位置、大小和最大化状态。
 * 注意：此服务依赖于 UserSettingsService 来保存和读取窗口状态。
 * 使用示例：
 * 
 * <pre>
 * 
 * WindowService.setup(primaryStage);
 * </pre>
 * 
 * @see UserSettingsService
 * 
 * @author phoenixhell
 * @version 1.0
 */
public class WindowService {

    private static String computeScreensHash() {
        return Screen.getScreens().stream()
                .map(Screen::hashCode)
                .map(String::valueOf)
                .collect(Collectors.joining(" "));
    }

    private static void setInitialStagePosition(Stage stage) {

        Boolean screensAreSameAsLastTime = UserSettingsService.getScreensHash()
                .map(previousScreenHash -> Objects.equals(computeScreensHash(), previousScreenHash))
                .orElse(false);

        if (screensAreSameAsLastTime) {
            Optional<Double> windowX = UserSettingsService.getWindowX();
            Optional<Double> windowY = UserSettingsService.getWindowY();
            Optional<Double> windowWidth = UserSettingsService.getWindowWidth();
            Optional<Double> windowHeight = UserSettingsService.getWindowHeight();
            Optional<Boolean> windowMaximized = UserSettingsService.getWindowMaximized();

            if (windowX.isPresent() &&
                    windowY.isPresent() &&
                    windowWidth.isPresent() &&
                    windowHeight.isPresent() &&
                    windowMaximized.isPresent()) {
                stage.setX(windowX.get());
                stage.setY(windowY.get());
                stage.setWidth(windowWidth.get());
                stage.setHeight(windowHeight.get());
                stage.setMaximized(windowMaximized.get());
            }

        } else {
            stage.setMaximized(true);
        }

    }

    private static void closeWindowEvent(WindowEvent event, Stage stage) {

        Window window = (Window) event.getSource();

        String screens = computeScreensHash();

        UserSettingsService.setWindowPosition(
                window.getX(),
                window.getY(),
                window.getWidth(),
                window.getHeight(),
                stage.isMaximized(),
                screens);

        UserSettingsService.saveSettings();
    }

    public static void setup(Stage stage) {

        setInitialStagePosition(stage);

        stage.getScene().getWindow().addEventFilter(
                WindowEvent.WINDOW_CLOSE_REQUEST,
                event -> WindowService.closeWindowEvent(event, stage));

    }
}
