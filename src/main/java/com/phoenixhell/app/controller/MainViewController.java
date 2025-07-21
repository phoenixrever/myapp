package com.phoenixhell.app.controller;

import java.util.Locale;

import com.phoenixhell.app.annotation.Control;
import com.phoenixhell.app.annotation.PostConstruct;
import com.phoenixhell.app.annotation.Service;
import com.phoenixhell.app.annotation.View;
import com.phoenixhell.app.api.ViewAware;
import com.phoenixhell.app.service.LocaleService;
import com.phoenixhell.app.service.UserSettingsService;
import com.phoenixhell.app.view.MainView;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class MainViewController implements ViewAware<MainView> {
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
        localChoiceBox.getItems().addAll(Locale.ENGLISH, Locale.FRENCH);
        localChoiceBox.setValue(LocaleService.getLocale());
    }

    private void initEvent() {
        textArea.textProperty().addListener((e, o, n) -> UserSettingsService.setSavedText(n));
        localChoiceBox.valueProperty().addListener((e, o, n) -> {
            LocaleService.setLocale(n);
            // 可根据需要刷新界面或标题
        });
        System.err.println("darkButton: " + darkButton);
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
}
