package com.phoenixhell.app.ui;

import org.kordamp.ikonli.javafx.FontIcon;

import com.phoenixhell.app.util.I18n;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainView extends BorderPane {

  private TextArea textArea;
  private ChoiceBox<String> localChoiceBox;

  public MainView() {
    // 初始化 UI 组件
    initUI();
  }

  private void initUI() {
    textArea = new TextArea();
    textArea.setPrefSize(200, 200);
    BorderPane.setMargin(textArea, new Insets(10));

    VBox vBox = new VBox();
    vBox.setPrefSize(100, 200);

    HBox themeBox = new HBox();
    VBox.setMargin(themeBox, new Insets(20, 0, 0, 0));

    Pane spacer1 = new Pane();
    HBox.setHgrow(spacer1, Priority.ALWAYS);

    Button darkButton = new Button(I18n.get("ui_dark"));
    darkButton.setId("darkButton");
    darkButton.setGraphic(new FontIcon("mdi2m-moon-waxing-crescent"));

    Pane spacer2 = new Pane();
    HBox.setHgrow(spacer2, Priority.ALWAYS);

    Button lightButton = new Button(I18n.get("ui_light"));
    lightButton.setId("lightButton");

    lightButton.setGraphic(new FontIcon("mdi2w-weather-sunny"));

    Pane spacer3 = new Pane();
    HBox.setHgrow(spacer3, Priority.ALWAYS);

    themeBox.getChildren().addAll(spacer1, darkButton, spacer2, lightButton, spacer3);

    HBox localeBox = new HBox();
    VBox.setMargin(localeBox, new Insets(20, 0, 0, 0));

    Pane spacer4 = new Pane();
    HBox.setHgrow(spacer4, Priority.ALWAYS);

    localChoiceBox = new ChoiceBox<>();
    localChoiceBox.setPrefWidth(150);

    Pane spacer5 = new Pane();
    HBox.setHgrow(spacer5, Priority.ALWAYS);

    localeBox.getChildren().addAll(spacer4, localChoiceBox, spacer5);

    vBox.getChildren().addAll(themeBox, localeBox);

    setPrefSize(1200, 900);
    setTop(textArea);
    setCenter(vBox);
  }
}
