package com.phoenixhell.app;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.ResourceBundle;

import com.phoenixhell.app.config.MyControllerFactory;
import com.phoenixhell.app.controller.MainViewController;
import com.phoenixhell.app.handler.DefaultExceptionHandler;
import com.phoenixhell.app.service.LocaleService;
import com.phoenixhell.app.service.UserSettingsService;
import com.phoenixhell.app.service.WindowService;
import com.phoenixhell.app.util.Resources;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {
  // requires transitive javafx.graphics; // UI、组件使用的?
  public static final List<KeyCodeCombination> SUPPORTED_HOTKEYS = List.of(
      new KeyCodeCombination(KeyCode.SLASH),
      new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN),
      new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));

  @Override
  public void start(Stage primaryStage) {
    Thread.currentThread().setUncaughtExceptionHandler(new DefaultExceptionHandler(primaryStage));
    loadApplicationProperties();

    UserSettingsService.initTheme();
    updateAppWindowTitle(primaryStage);

    // 扫描并注入Controller中的Service、View、Control
    MyControllerFactory.scan("com.phoenixhell.app.controller");

    MainViewController controller = MyControllerFactory.getBean(MainViewController.class);
    Scene scene = new Scene(controller.getView(), 400, 300);

    primaryStage.getIcons()
        .add(new Image(Objects.requireNonNull(Launcher.class.getResourceAsStream("/myapp/icon-64.png"))));
    primaryStage.setMinHeight(300);
    primaryStage.setMinWidth(400);
    primaryStage.initStyle(StageStyle.DECORATED);
    primaryStage.setScene(scene);
    primaryStage.setTitle("智能 IoC 工厂 示例");
    WindowService.setup(primaryStage);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
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

  public static void updateAppWindowTitle(Stage primaryStage) {
    ResourceBundle resourceBundle = ResourceBundle.getBundle("App", LocaleService.getLocale());
    primaryStage.setTitle(resourceBundle.getString("name"));
  }
  // private void dispatchHotkeys(KeyEvent event) {
  // for (KeyCodeCombination k : SUPPORTED_HOTKEYS) {
  // if (k.match(event)) {
  // DefaultEventBus.getInstance().publish(new HotkeyEvent(k));
  // return;
  // }
  // }
  // }

  // @Listener
  // private void onBrowseEvent(BrowseEvent event) {
  // getHostServices().showDocument(event.getUri().toString());
  // }

  // @Listener
  // private void openDevTools(Stage primaryStage, ThemeManager tm) {
  // var prefs = new Preferences(getHostServices());
  // prefs.setDarkMode(tm.getTheme().isDarkMode());
  // prefs.setCollapseControls(true);
  // prefs.setCollapsePanes(true);

  // GUI.openToolStage(primaryStage, prefs, "AtlantaFX");
  // }
}
