package com.phoenixhell.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.prefs.Preferences;

import com.phoenixhell.app.config.MyControllerFactory;
import com.phoenixhell.app.event.BrowseEvent;
import com.phoenixhell.app.event.DefaultEventBus;
import com.phoenixhell.app.event.DevToolsEvent;
import com.phoenixhell.app.event.HotkeyEvent;
import com.phoenixhell.app.event.Listener;
import com.phoenixhell.app.handler.DefaultExceptionHandler;
import com.phoenixhell.app.service.system.UserSettingsService;
import com.phoenixhell.app.service.system.WindowService;
import com.phoenixhell.app.ui.layout.ApplicationWindow;
import com.phoenixhell.app.ui.theme.ThemeManager;
import com.phoenixhell.app.util.I18n;
import com.phoenixhell.app.util.Resources;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {

    private static Stage stage;
    // requires transitive javafx.graphics; // UI、组件使用的?
    public static final List<KeyCodeCombination> SUPPORTED_HOTKEYS = List.of(
            new KeyCodeCombination(KeyCode.SLASH),
            new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN),
            new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));

    public static final boolean IS_DEV_MODE = "DEV".equalsIgnoreCase(
            Resources.getPropertyOrEnv("atlantafx.mode", "ATLANTAFX_MODE"));

    @Override
    public void start(Stage primaryStage) {
        Launcher.stage = primaryStage;
        Thread.currentThread().setUncaughtExceptionHandler(new DefaultExceptionHandler(primaryStage));
        loadApplicationProperties();

        UserSettingsService.initTheme();
        updateAppWindowTitle();

        // 扫描并注入Controller中的Service、View、Control
        MyControllerFactory.scan("com.phoenixhell.app.controller");

        // MainViewController controller =
        // MyControllerFactory.getBean(MainViewController.class);
        // Scene scene = new Scene(controller.getView(), 400, 300);

        // OverviewController controller =
        // MyControllerFactory.getBean(OverviewController.class);
        // Scene scene = new Scene(controller.getView(), 400, 300);

        var root = new ApplicationWindow();

        var scene = new Scene(root, ApplicationWindow.MIN_WIDTH + 80, 768);
        scene.setOnKeyPressed(this::dispatchHotkeys);
        var tm = ThemeManager.getInstance();
        tm.setScene(scene);
        tm.setTheme(tm.getDefaultTheme());

        // tm.setTheme scene.getStylesheets().setAll(theme.getAllStylesheets()); 先清空
        // scene 原有的所有样式表 然后再添加 theme.getAllStylesheets() 中的那些路径
        // addAll(...) 是在原有基础上追加
        // TODO 具体细节以后分析 总之这个不能少
        scene.getStylesheets().add(Resources.getResourceStr("/assets/styles/index.css"));
        scene.getStylesheets().forEach(s -> System.out.println("✅ 成功加载样式: " + s));

        // primaryStage.getIcons()
        // .add(new
        // Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("/myapp/icon-64.png"))));
        loadIcons(primaryStage);
        stage.setResizable(true);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(400);
        primaryStage.initStyle(StageStyle.DECORATED);
        primaryStage.setScene(scene);
        WindowService.setup(primaryStage);

        // register event listeners
        DefaultEventBus.getInstance().subscribe(BrowseEvent.class, this::onBrowseEvent);
        // DefaultEventBus.getInstance().subscribe(DevToolsEvent.class, e ->
        // openDevTools(stage, tm));

        Platform.runLater(() -> {
            stage.show();
            stage.requestFocus();
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void loadIcons(Stage stage) {
        int iconSize = 16;
        while (iconSize <= 1024) {
            // we could use the square icons for Windows here
            stage.getIcons()
                    .add(new Image(Resources.getResourceAsStream("assets/images/icon-rounded-" + iconSize + ".png")));
            iconSize *= 2;
        }
    }

    private void loadApplicationProperties() {
        Properties properties = new Properties();
        try (InputStreamReader in = new InputStreamReader(Resources.getResourceAsStream("application.properties"),
                "UTF-8")) {
            properties.load(in);
            properties.forEach((key, value) -> System.setProperty(
                    String.valueOf(key),
                    String.valueOf(value)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void dispatchHotkeys(KeyEvent event) {
        for (KeyCodeCombination k : SUPPORTED_HOTKEYS) {
            if (k.match(event)) {
                DefaultEventBus.getInstance().publish(new HotkeyEvent(k));
                return;
            }
        }
    }

    public static void updateAppWindowTitle() {
        stage.setTitle(I18n.get("name"));
    }

    @Listener
    private void onBrowseEvent(BrowseEvent event) {
        getHostServices().showDocument(event.getUri().toString());
    }

    // @Listener
    // private void openDevTools(Stage primaryStage, ThemeManager tm) {
    // var prefs = new Preferences(getHostServices());
    // prefs.setDarkMode(tm.getTheme().isDarkMode());
    // prefs.setCollapseControls(true);
    // prefs.setCollapsePanes(true);

    // GUI.openToolStage(primaryStage, prefs, "AtlantaFX");
    // }
}
