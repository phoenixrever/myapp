package com.phoenixhell.app.controller;

import java.util.Locale;

import com.phoenixhell.app.annotation.Control;
import com.phoenixhell.app.annotation.PostConstruct;
import com.phoenixhell.app.annotation.Service;
import com.phoenixhell.app.annotation.View;
import com.phoenixhell.app.contract.Translatable;
import com.phoenixhell.app.contract.ViewAware;
import com.phoenixhell.app.service.system.LocaleService;
import com.phoenixhell.app.service.system.UserSettingsService;
import com.phoenixhell.app.util.I18n;
import com.phoenixhell.app.view.MainView;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

/**
 * 继承ViewAware接口的controller才会被factory创建
 */
public class MainViewController implements ViewAware<MainView>, Translatable {
    @View
    private MainView view;

    @Service
    private UserSettingsService userSettingsService;

    @Control
    public BorderPane mainPane;

    @Control("darkButton")
    public Button darkButton;

    @Control("lightButton")
    public Button lighButton;

    @Control
    public TextArea textArea;

    @Control
    public ChoiceBox<Locale> localChoiceBox;

    // 方法 2：不使用注解，直接命名为 initialize()
    // public void initialize() { ... }

    @PostConstruct
    public void init() {
        // 注册当前控制器为 Translatable 接口的实现 这样可以在语言切换时更新文本
        initView();
        initEvent();
    }

    // 方法 2：不使用注解，直接命名为 initialize()
    // public void initialize() { ... }

    public MainView getView() {
        return view;
    }

    private void initView() {
        textArea.setText(UserSettingsService.getSavedText());
        localChoiceBox.getItems().addAll(Locale.ENGLISH, Locale.JAPANESE);

        /**
         * localChoiceBox.setConverter(new StringConverter<>() {
         * 
         * @Override
         *           public String toString(Locale locale) {
         *           // 显示语言名
         *           return switch (locale.getLanguage()) {
         *           case "en" -> "ENGLISH";
         *           case "ja" -> "日本語";
         *           default -> locale.getDisplayLanguage();
         *           };
         *           }
         * 
         * @Override
         *           public Locale fromString(String s) {
         *           return switch (s) {
         *           case "ENGLISH" -> Locale.ENGLISH;
         *           case "日本語" -> Locale.JAPANESE;
         *           default -> Locale.ENGLISH;
         *           };
         *           }
         *           });
         */

        localChoiceBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locale locale) {
                return locale.getDisplayLanguage(locale); // 显示为“English”、“日本語”
            }

            @Override
            public Locale fromString(String s) {

                return null; // 通常不会用到 fromString，可以返回 null 或抛异常
            }
        });

        localChoiceBox.setValue(LocaleService.getLocale());
    }

    private void initEvent() {
        textArea.textProperty().addListener((e, o, n) -> UserSettingsService.setSavedText(n));
        localChoiceBox.valueProperty().addListener((e, o, n) -> {
            System.out.println("Locale changed to: " + n);
            LocaleService.setLocale(n);
        });
        darkButton.setOnAction(event -> toDark());
        lighButton.setOnAction(event -> toLight());
    }

    public void toDark() {
        System.out.println("切换到暗黑模式");
        UserSettingsService.setTheme(UserSettingsService.DARK);
    }

    public void toLight() {
        System.out.println("切换到明亮模式");
        UserSettingsService.setTheme(UserSettingsService.LIGHT);
    }

    @Override
    public void updateTexts() {
        darkButton.setText(I18n.get("ui_dark"));
        lighButton.setText(I18n.get("ui_light"));
    }
}
