package com.phoenixhell.app.ui.view;

import org.kordamp.ikonli.javafx.FontIcon;

import com.phoenixhell.app.fake.domain.Metric;

import atlantafx.base.controls.Breadcrumbs;
import atlantafx.base.controls.Calendar;
import atlantafx.base.controls.CustomTextField;
import atlantafx.base.controls.MaskTextField;
import atlantafx.base.controls.PasswordTextField;
import atlantafx.base.controls.RingProgressIndicator;
import atlantafx.base.controls.Tile;
import atlantafx.base.controls.ToggleSwitch;
import atlantafx.base.layout.InputGroup;
import javafx.collections.FXCollections;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Pagination;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Overview extends GridPane {

    private ToggleGroup group1;
    private ToggleGroup group2;
    private MaskTextField phoneTf;
    private Breadcrumbs breadcrumbs;

    public Overview() {
        // 创建主GridPane
        this.setHgap(40.0);
        this.setVgap(20.0);
        this.setPadding(new Insets(20.0));

        // 设置列约束
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        this.getColumnConstraints().addAll(col1, col2);

        // 设置行约束
        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            this.getRowConstraints().add(row);
        }

        // 初始化ToggleGroup
        group1 = new ToggleGroup();
        group2 = new ToggleGroup();

        // 创建各个区域
        this.add(createInputFieldsGrid(), 0, 0);
        this.add(createButtonGrid(), 0, 1);
        this.add(createCheckBoxRadioGrid(), 0, 2);
        this.add(createSliderGrid(), 1, 0);
        this.add(createControlsGrid(), 1, 1);
        this.add(createTabPaneAndTextArea(), 1, 2, 1, 2);
        this.add(createCalendar(), 0, 3);
        this.add(createProgressBars(), 0, 4);
        this.add(createTableViewAndPagination(), 1, 4, 1, 3);
        this.add(createProgressIndicators(), 0, 5);
        this.add(createAccordion(), 0, 6);
    }

    private GridPane createInputFieldsGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10.0);
        grid.setVgap(10.0);

        // 列约束
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        grid.getColumnConstraints().addAll(col1, col2);

        // 行约束
        for (int i = 0; i < 5; i++) {
            RowConstraints row = new RowConstraints();
            if (i == 4)
                row.setVgrow(Priority.ALWAYS);
            else
                row.setVgrow(Priority.NEVER);
            grid.getRowConstraints().add(row);
        }

        // 添加文本字段
        TextField textField1 = new TextField();
        textField1.setPromptText("Text");
        grid.add(textField1, 0, 0);

        TextField textField2 = new TextField();
        textField2.setPromptText("Prompt");
        grid.add(textField2, 1, 0);

        TextField readonlyField = new TextField();
        readonlyField.setText("Readonly");
        readonlyField.setEditable(false);
        grid.add(readonlyField, 0, 1);

        TextField disabledField = new TextField();
        disabledField.setPromptText("Disabled");
        disabledField.setDisable(true);
        grid.add(disabledField, 1, 1);

        PasswordTextField passwordField = new PasswordTextField();
        passwordField.setText("password");
        grid.add(passwordField, 0, 2);

        CustomTextField searchField = new CustomTextField();
        searchField.setPromptText("Search");
        searchField.setLeft(new FontIcon("mdmz-search"));
        searchField.setRight(new FontIcon("mdal-clear"));
        grid.add(searchField, 1, 2);

        phoneTf = new MaskTextField();
        phoneTf.setMask("(NNN) NNN-NN-NN");
        grid.add(phoneTf, 0, 3);

        breadcrumbs = new Breadcrumbs();
        breadcrumbs.setMaxWidth(Double.MAX_VALUE);
        grid.add(breadcrumbs, 0, 4, 2, 1);

        return grid;
    }

    private GridPane createButtonGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10.0);
        grid.setVgap(10.0);

        // 列约束
        for (int i = 0; i < 4; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.ALWAYS);
            col.setMaxWidth(Double.MAX_VALUE);
            grid.getColumnConstraints().add(col);
        }

        // 行约束
        for (int i = 0; i < 6; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.NEVER);
            grid.getRowConstraints().add(row);
        }

        // 第一行 - 常规按钮
        Button[] regularButtons = {
                createButton("Regular", null),
                createButton("Accent", "accent"),
                createButton("Success", "success"),
                createButton("Danger", "danger")
        };

        for (int i = 0; i < regularButtons.length; i++) {
            regularButtons[i].setMaxWidth(Double.MAX_VALUE);
            grid.add(regularButtons[i], i, 0);
        }

        // 第二行 - 轮廓按钮
        Button[] outlinedButtons = {
                createButton("Regular", "button-outlined"),
                createButton("Accent", "accent", "button-outlined"),
                createButton("Success", "success", "button-outlined"),
                createButton("Danger", "danger", "button-outlined")
        };

        for (int i = 0; i < outlinedButtons.length; i++) {
            outlinedButtons[i].setMaxWidth(Double.MAX_VALUE);
            grid.add(outlinedButtons[i], i, 1);
        }

        // 第三行 - 扁平按钮
        Button[] flatButtons = {
                createButton("Regular", "flat"),
                createButton("Accent", "accent", "flat"),
                createButton("Success", "success", "flat"),
                createButton("Danger", "danger", "flat")
        };

        for (int i = 0; i < flatButtons.length; i++) {
            flatButtons[i].setMaxWidth(Double.MAX_VALUE);
            grid.add(flatButtons[i], i, 2);
        }

        // ToggleButton组
        InputGroup toggleGroup = new InputGroup();
        toggleGroup.setMaxWidth(Double.MAX_VALUE);

        ToggleButton toggle1 = new ToggleButton("Toggle1");
        toggle1.setMaxWidth(Double.MAX_VALUE);
        toggle1.setToggleGroup(group2);
        HBox.setHgrow(toggle1, Priority.ALWAYS);

        ToggleButton toggle2 = new ToggleButton("Toggle2");
        toggle2.setMaxWidth(Double.MAX_VALUE);
        toggle2.setSelected(true);
        toggle2.setToggleGroup(group2);
        HBox.setHgrow(toggle2, Priority.ALWAYS);

        ToggleButton toggle3 = new ToggleButton("Toggle3");
        toggle3.setMaxWidth(Double.MAX_VALUE);
        toggle3.setToggleGroup(group2);
        HBox.setHgrow(toggle3, Priority.ALWAYS);

        toggleGroup.getChildren().addAll(toggle1, toggle2, toggle3);
        grid.add(toggleGroup, 0, 3, Integer.MAX_VALUE, 1);

        // 菜单栏和工具栏
        VBox menuToolbarBox = new VBox();
        GridPane.setMargin(menuToolbarBox, new Insets(10.0, 0, 0, 0));

        // 菜单栏
        MenuBar menuBar = new MenuBar();
        menuBar.setMaxWidth(Double.MAX_VALUE);

        Menu fileMenu = new Menu("File");
        fileMenu.getItems().add(new MenuItem("Close"));

        Menu editMenu = new Menu("Edit");
        editMenu.getItems().add(new MenuItem("Delete"));

        Menu helpMenu = new Menu("Help");
        helpMenu.getItems().add(new MenuItem("About"));

        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);

        // 工具栏
        ToolBar toolBar = new ToolBar();
        toolBar.setMaxWidth(Double.MAX_VALUE);

        Button boldBtn = new Button();
        boldBtn.setGraphic(new FontIcon("mdoal-format_bold"));

        Button italicBtn = new Button();
        italicBtn.setGraphic(new FontIcon("mdoal-format_italic"));

        Button underlineBtn = new Button();
        underlineBtn.setGraphic(new FontIcon("mdoal-format_underlined"));

        Button strikeBtn = new Button();
        strikeBtn.setGraphic(new FontIcon("mdoal-format_strikethrough"));

        Separator separator = new Separator(Orientation.VERTICAL);

        Button attachBtn = new Button();
        attachBtn.setGraphic(new FontIcon("mdoal-attach_file"));

        Button linkBtn = new Button();
        linkBtn.setGraphic(new FontIcon("mdoal-insert_link"));

        toolBar.getItems().addAll(boldBtn, italicBtn, underlineBtn, strikeBtn, separator, attachBtn, linkBtn);

        menuToolbarBox.getChildren().addAll(menuBar, toolBar);
        grid.add(menuToolbarBox, 0, 4, Integer.MAX_VALUE, 1);

        return grid;
    }

    private Button createButton(String text, String... styleClasses) {
        Button button = new Button(text);
        button.setMnemonicParsing(false);
        if (styleClasses != null) {
            button.getStyleClass().addAll(styleClasses);
        }
        return button;
    }

    private GridPane createCheckBoxRadioGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10.0);
        grid.setVgap(10.0);

        // 列约束
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHalignment(HPos.CENTER);
        col3.setHgrow(Priority.NEVER);
        col3.setPrefWidth(100.0);
        grid.getColumnConstraints().addAll(col1, col2, col3);

        // 行约束
        for (int i = 0; i < 3; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.NEVER);
            grid.getRowConstraints().add(row);
        }

        // CheckBox
        CheckBox uncheckedBox = new CheckBox("Unchecked");
        uncheckedBox.setMnemonicParsing(false);
        grid.add(uncheckedBox, 0, 0);

        CheckBox indeterminateBox = new CheckBox("Indeterminate");
        indeterminateBox.setMnemonicParsing(false);
        indeterminateBox.setAllowIndeterminate(true);
        indeterminateBox.setIndeterminate(true);
        indeterminateBox.setSelected(true);
        grid.add(indeterminateBox, 0, 1);

        CheckBox checkedBox = new CheckBox("Checked");
        checkedBox.setMnemonicParsing(false);
        checkedBox.setSelected(true);
        grid.add(checkedBox, 0, 2);

        // RadioButton
        RadioButton radio1 = new RadioButton("Option 1");
        radio1.setMnemonicParsing(false);
        radio1.setSelected(true);
        radio1.setToggleGroup(group1);
        grid.add(radio1, 1, 0);

        RadioButton radio2 = new RadioButton("Option 2");
        radio2.setMnemonicParsing(false);
        radio2.setToggleGroup(group1);
        grid.add(radio2, 1, 1);

        RadioButton radio3 = new RadioButton("Option 3");
        radio3.setMnemonicParsing(false);
        radio3.setToggleGroup(group1);
        grid.add(radio3, 1, 2);

        // ToggleSwitch
        ToggleSwitch toggleSwitch1 = new ToggleSwitch();
        toggleSwitch1.setSelected(true);
        grid.add(toggleSwitch1, 2, 0);

        ToggleSwitch toggleSwitch2 = new ToggleSwitch();
        grid.add(toggleSwitch2, 2, 1);

        return grid;
    }

    private GridPane createSliderGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(20.0);
        grid.setVgap(10.0);
        GridPane.setHalignment(grid, HPos.CENTER);
        GridPane.setValignment(grid, VPos.TOP);

        // 列约束
        for (int i = 0; i < 7; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setHgrow(Priority.NEVER);
            if (i == 6)
                col.setHalignment(HPos.CENTER);
            grid.getColumnConstraints().add(col);
        }

        // 行约束
        for (int i = 0; i < 3; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.SOMETIMES);
            grid.getRowConstraints().add(row);
        }

        // 水平滑块
        VBox hSliderBox1 = new VBox();
        hSliderBox1.setSpacing(10.0);

        Slider hSlider1 = createProgressSlider(150.0, Orientation.HORIZONTAL, 25.0);
        hSlider1.getStyleClass().add("small");

        Slider hSlider1WithTicks = createProgressSlider(150.0, Orientation.HORIZONTAL, 25.0);
        hSlider1WithTicks.getStyleClass().add("small");
        hSlider1WithTicks.setShowTickLabels(true);
        hSlider1WithTicks.setShowTickMarks(true);

        hSliderBox1.getChildren().addAll(hSlider1, hSlider1WithTicks);
        grid.add(hSliderBox1, 0, 0);

        VBox hSliderBox2 = new VBox();
        hSliderBox2.setSpacing(10.0);

        Slider hSlider2 = createProgressSlider(150.0, Orientation.HORIZONTAL, 50.0);

        Slider hSlider2WithTicks = createProgressSlider(150.0, Orientation.HORIZONTAL, 50.0);
        hSlider2WithTicks.setShowTickLabels(true);
        hSlider2WithTicks.setShowTickMarks(true);

        hSliderBox2.getChildren().addAll(hSlider2, hSlider2WithTicks);
        grid.add(hSliderBox2, 0, 1);

        Slider hSlider3 = createProgressSlider(150.0, Orientation.HORIZONTAL, 75.0);
        hSlider3.getStyleClass().add("large");
        grid.add(hSlider3, 0, 2);

        // 垂直滑块
        Slider vSlider1 = createProgressSlider(150.0, Orientation.VERTICAL, 25.0);
        vSlider1.getStyleClass().add("small");
        grid.add(vSlider1, 1, 0, 1, Integer.MAX_VALUE);

        Slider vSlider1WithTicks = createProgressSlider(150.0, Orientation.VERTICAL, 25.0);
        vSlider1WithTicks.getStyleClass().add("small");
        vSlider1WithTicks.setShowTickLabels(true);
        vSlider1WithTicks.setShowTickMarks(true);
        grid.add(vSlider1WithTicks, 2, 0, 1, Integer.MAX_VALUE);

        Slider vSlider2 = createProgressSlider(150.0, Orientation.VERTICAL, 50.0);
        vSlider2.setPrefHeight(150.0);
        grid.add(vSlider2, 3, 0, 1, Integer.MAX_VALUE);

        Slider vSlider2WithTicks = createProgressSlider(150.0, Orientation.VERTICAL, 50.0);
        vSlider2WithTicks.setPrefHeight(150.0);
        vSlider2WithTicks.setShowTickLabels(true);
        vSlider2WithTicks.setShowTickMarks(true);
        grid.add(vSlider2WithTicks, 4, 0, 1, Integer.MAX_VALUE);

        Slider vSlider3 = createProgressSlider(150.0, Orientation.VERTICAL, 75.0);
        vSlider3.getStyleClass().add("large");
        vSlider3.setMinHeight(150.0);
        vSlider3.setPrefHeight(150.0);
        grid.add(vSlider3, 5, 0, 1, Integer.MAX_VALUE);

        return grid;
    }

    private Slider createProgressSlider(double minWidth, Orientation orientation, double value) {
        Slider slider = new Slider();
        slider.setStyle("-fx-skin: \"atlantafx.base.controls.ProgressSliderSkin\";");
        slider.setValue(value);
        slider.setOrientation(orientation);
        if (orientation == Orientation.HORIZONTAL) {
            slider.setMinWidth(minWidth);
        }
        return slider;
    }

    private GridPane createControlsGrid() {
        GridPane grid = new GridPane();
        grid.setHgap(10.0);
        grid.setVgap(10.0);

        // 列约束
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHgrow(Priority.NEVER);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);
        ColumnConstraints col3 = new ColumnConstraints();
        col3.setHalignment(HPos.CENTER);
        col3.setHgrow(Priority.NEVER);
        col3.setMinWidth(50.0);
        ColumnConstraints col4 = new ColumnConstraints();
        col4.setHalignment(HPos.CENTER);
        col4.setHgrow(Priority.NEVER);
        col4.setMinWidth(50.0);
        grid.getColumnConstraints().addAll(col1, col2, col3, col4);

        // 行约束
        for (int i = 0; i < 7; i++) {
            RowConstraints row = new RowConstraints();
            row.setVgrow(Priority.NEVER);
            grid.getRowConstraints().add(row);
        }

        // ComboBox
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setMinWidth(150.0);
        comboBox.setItems(FXCollections.observableArrayList("ComboBox", "Item 2", "Item 3"));
        comboBox.setValue("ComboBox");
        grid.add(comboBox, 0, 0);

        // ChoiceBox
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.setMinWidth(150.0);
        choiceBox.setPrefWidth(150.0);
        choiceBox.setItems(FXCollections.observableArrayList("ChoiceBox", "Item 2", "Item 3"));
        choiceBox.setValue("ChoiceBox");
        grid.add(choiceBox, 1, 0);

        // DatePicker
        DatePicker datePicker = new DatePicker();
        datePicker.setMinWidth(150.0);
        datePicker.setPrefWidth(150.0);
        datePicker.setShowWeekNumbers(true);
        grid.add(datePicker, 0, 1);

        // ColorPicker
        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setMinWidth(150.0);
        colorPicker.setPrefWidth(150.0);
        colorPicker.setValue(Color.color(0.6784313917160034, 0.21176470816135406, 0.19607843458652496));
        grid.add(colorPicker, 1, 1);

        // Spinner
        Spinner<Integer> spinner1 = new Spinner<>();
        spinner1.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 5));
        spinner1.setMinWidth(150.0);
        spinner1.setPrefWidth(150.0);
        grid.add(spinner1, 0, 2);

        Spinner<Integer> spinner2 = new Spinner<>();
        spinner2.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 10, 5));
        spinner2.setMinWidth(150.0);
        spinner2.setPrefWidth(150.0);
        spinner2.getStyleClass().add("split-arrows-horizontal");
        grid.add(spinner2, 1, 2);

        // MenuButton和SplitMenuButton
        MenuButton menuButton1 = createMenuButton("MenuButton", 150.0);
        grid.add(menuButton1, 0, 3);

        SplitMenuButton splitButton1 = createSplitMenuButton("SplitButton", 150.0);
        grid.add(splitButton1, 1, 3);

        MenuButton menuButton2 = createMenuButton("MenuButton", 150.0, "flat");
        grid.add(menuButton2, 0, 4);

        SplitMenuButton splitButton2 = createSplitMenuButton("SplitButton", 150.0, "flat");
        grid.add(splitButton2, 1, 4);

        MenuButton menuButton3 = createMenuButton("MenuButton", 150.0, "outlined");
        grid.add(menuButton3, 0, 5);

        SplitMenuButton splitButton3 = createSplitMenuButton("SplitButton", 150.0, "accent");
        grid.add(splitButton3, 1, 5);

        MenuButton menuButton4 = createMenuButton("MenuButton", 150.0, "success");
        grid.add(menuButton4, 0, 6);

        SplitMenuButton splitButton4 = createSplitMenuButton("SplitButton", 150.0, "danger");
        grid.add(splitButton4, 1, 6);

        // 圆形按钮
        Button circleBtn1 = createCircleButton("mdal-insert_chart_outlined", "button-circle");
        grid.add(circleBtn1, 2, 0);

        Button circleBtn2 = createCircleButton("mdal-insert_chart_outlined", "accent", "button-circle");
        grid.add(circleBtn2, 2, 1);

        Button circleBtn3 = createCircleButton("mdal-insert_chart_outlined", "success", "button-circle");
        grid.add(circleBtn3, 2, 2);

        Button circleBtn4 = createCircleButton("mdal-insert_chart_outlined", "flat", "button-circle");
        grid.add(circleBtn4, 2, 3);

        Button circleBtn5 = createCircleButton("mdal-insert_chart_outlined", "flat", "button-circle", "accent");
        grid.add(circleBtn5, 2, 4);

        Button circleBtn6 = createCircleButton("mdal-insert_chart_outlined", "flat", "button-circle", "success");
        grid.add(circleBtn6, 2, 5);

        Button circleBtn7 = createCircleButton("mdal-insert_chart_outlined", "flat", "button-circle", "danger");
        grid.add(circleBtn7, 2, 6);

        // 标签组
        VBox labelBox = new VBox();
        labelBox.setAlignment(Pos.CENTER_LEFT);
        labelBox.setPrefWidth(100.0);
        labelBox.setSpacing(10.0);
        GridPane.setHalignment(labelBox, HPos.CENTER);
        GridPane.setHgrow(labelBox, Priority.ALWAYS);
        GridPane.setVgrow(labelBox, Priority.ALWAYS);

        labelBox.getChildren().addAll(
                createLabel("Label", "mdal-label"),
                createLabel("Label", "mdal-label", "accent"),
                createLabel("Label", "mdal-label", "success"),
                createLabel("Label", "mdal-label", "warning"),
                createLabel("Label", "mdal-label", "danger"),
                createLabel("Label", "mdal-label", "text-muted"),
                createLabel("Label", "mdal-label", "text-subtle"),
                new Hyperlink("Hyperlink"),
                createVisitedHyperlink(),
                createDisabledHyperlink());

        grid.add(labelBox, 3, 0, 1, Integer.MAX_VALUE);

        return grid;
    }

    private MenuButton createMenuButton(String text, double width, String... styleClasses) {
        MenuButton menuButton = new MenuButton(text);
        menuButton.setMnemonicParsing(false);
        menuButton.setMinWidth(width);
        menuButton.setPrefWidth(width);
        menuButton.getItems().addAll(
                new MenuItem("Action 1"),
                new MenuItem("Action 2"));
        if (styleClasses != null) {
            menuButton.getStyleClass().addAll(styleClasses);
        }
        return menuButton;
    }

    private SplitMenuButton createSplitMenuButton(String text, double width, String... styleClasses) {
        SplitMenuButton splitButton = new SplitMenuButton();
        splitButton.setText(text);
        splitButton.setMnemonicParsing(false);
        splitButton.setMinWidth(width);
        splitButton.setPrefWidth(width);
        splitButton.getItems().addAll(
                new MenuItem("Action 1"),
                new MenuItem("Action 2"));
        if (styleClasses != null) {
            splitButton.getStyleClass().addAll(styleClasses);
        }
        return splitButton;
    }

    private Button createCircleButton(String iconLiteral, String... styleClasses) {
        Button button = new Button();
        button.setMnemonicParsing(false);
        button.setGraphic(new FontIcon(iconLiteral));
        if (styleClasses != null) {
            button.getStyleClass().addAll(styleClasses);
        }
        return button;
    }

    private Label createLabel(String text, String iconLiteral, String... styleClasses) {
        Label label = new Label(text);
        label.setGraphic(new FontIcon(iconLiteral));
        if (styleClasses != null) {
            label.getStyleClass().addAll(styleClasses);
        }
        return label;
    }

    private Hyperlink createVisitedHyperlink() {
        Hyperlink hyperlink = new Hyperlink("Hyperlink");
        hyperlink.setVisited(true);
        return hyperlink;
    }

    private Hyperlink createDisabledHyperlink() {
        Hyperlink hyperlink = new Hyperlink("Hyperlink");
        hyperlink.setDisable(true);
        return hyperlink;
    }

    private VBox createTabPaneAndTextArea() {
        VBox vbox = new VBox();
        vbox.setPrefWidth(400.0);
        vbox.setSpacing(10.0);

        // 第一个TabPane
        TabPane tabPane1 = new TabPane();
        tabPane1.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane1.getTabs().addAll(
                new Tab("Tab 1"),
                new Tab("Tab 2"),
                createDisabledTab("Tab 3"));

        // Floating TabPane
        TabPane tabPane2 = new TabPane();
        tabPane2.getStyleClass().add("floating");
        tabPane2.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane2.getTabs().addAll(
                new Tab("Tab1"),
                new Tab("Tab 2"));

        // Classic TabPane
        TabPane tabPane3 = new TabPane();
        tabPane3.getStyleClass().add("classic");
        tabPane3.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        tabPane3.getTabs().addAll(
                new Tab("Tab 1"),
                new Tab("Tab 2"),
                createDisabledTab("Tab 3"));

        // TextArea
        TextArea textArea = new TextArea();
        textArea.setText(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam molestie lacus ut mauris consectetur, sed aliquet ipsum elementum. Aenean id tincidunt mauris. Sed sit amet leo rutrum, viverra est ac, aliquam erat. Etiam ullamcorper tincidunt felis, eget semper tortor venenatis nec. Maecenas venenatis commodo lacus non sagittis. Proin vitae turpis at enim gravida hendrerit. Interdum et malesuada fames ac ante ipsum primis in faucibus.");
        textArea.setWrapText(true);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        vbox.getChildren().addAll(tabPane1, tabPane2, tabPane3, textArea);
        return vbox;
    }

    private Tab createDisabledTab(String text) {
        Tab tab = new Tab(text);
        tab.setDisable(true);
        return tab;
    }

    private Calendar createCalendar() {
        Calendar calendar = new Calendar();
        calendar.setShowWeekNumbers(true);
        return calendar;
    }

    private VBox createProgressBars() {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER_LEFT);
        vbox.setMinHeight(100.0);
        vbox.setSpacing(10.0);
        vbox.setPadding(new Insets(10.0, 0, 10.0, 0));

        ProgressBar progressBar1 = new ProgressBar(0.25);
        progressBar1.setMaxWidth(Double.MAX_VALUE);
        progressBar1.getStyleClass().add("small");

        ProgressBar progressBar2 = new ProgressBar(0.51);
        progressBar2.setMaxWidth(Double.MAX_VALUE);

        ProgressBar progressBar3 = new ProgressBar(0.75);
        progressBar3.setMaxWidth(Double.MAX_VALUE);
        progressBar3.getStyleClass().add("large");

        vbox.getChildren().addAll(progressBar1, progressBar2, progressBar3);
        return vbox;
    }

    private VBox createTableViewAndPagination() {
        VBox vbox = new VBox();
        vbox.setMinHeight(400.0);
        vbox.setPrefHeight(400.0);

        // TableView
        TableView<Metric> tableView = new TableView<>();
        tableView.getStyleClass().add("striped");
        VBox.setVgrow(tableView, Priority.ALWAYS);

        // Queries列
        TableColumn<Metric, String> queriesCol = new TableColumn<>("Queries");
        queriesCol.setMinWidth(100.0);
        queriesCol.setPrefWidth(100.0);
        queriesCol.setCellValueFactory(new PropertyValueFactory<>("queries"));

        // Latency列
        TableColumn<Metric, String> latencyCol = new TableColumn<>("Latency");
        latencyCol.setMinWidth(30.0);
        latencyCol.setPrefWidth(30.0);
        latencyCol.getStyleClass().add("align-right");
        latencyCol.setCellValueFactory(new PropertyValueFactory<>("latency"));

        // Requests列
        TableColumn<Metric, String> requestsCol = new TableColumn<>("Requests");
        requestsCol.setPrefWidth(75.0);
        requestsCol.getStyleClass().add("align-right");
        requestsCol.setCellValueFactory(new PropertyValueFactory<>("requests"));

        tableView.getColumns().addAll(queriesCol, latencyCol, requestsCol);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // 添加数据
        tableView.setItems(FXCollections.observableArrayList(
                new Metric("activeCart", "116ms", "26.1K", "67.4%"),
                new Metric("getUserShippingAddress", "284ms", "16.3K", "97.6%"),
                new Metric("getPromotionCollection", "456ms", "10.2K", "14.8%"),
                new Metric("getUserPersonalDiscount", "451ms", "8,764", "41.3%"),
                new Metric("getActiveCoupons", "123ms", "5,456", "92.4%")));

        // Pagination
        Pagination pagination = new Pagination();
        pagination.setCurrentPageIndex(2);
        pagination.setPageCount(25);
        pagination.setStyle("-fx-page-information-visible: false;");
        VBox.setVgrow(pagination, Priority.NEVER);

        vbox.getChildren().addAll(tableView, pagination);
        return vbox;
    }

    private HBox createProgressIndicators() {
        HBox hbox = new HBox();
        hbox.setSpacing(20.0);
        hbox.setPadding(new Insets(10.0, 0, 10.0, 0));

        ProgressIndicator pi1 = new ProgressIndicator(0.25);
        pi1.setMinHeight(50.0);
        pi1.setMinWidth(50.0);

        ProgressIndicator pi2 = new ProgressIndicator(0.5);
        pi2.setMinHeight(50.0);
        pi2.setMinWidth(50.0);

        ProgressIndicator pi3 = new ProgressIndicator(0.75);
        pi3.setMinHeight(50.0);
        pi3.setMinWidth(50.0);

        RingProgressIndicator rpi1 = new RingProgressIndicator();
        rpi1.setProgress(0.25);

        RingProgressIndicator rpi2 = new RingProgressIndicator();
        rpi2.setProgress(0.5);

        hbox.getChildren().addAll(pi1, pi2, pi3, rpi1, rpi2);
        return hbox;
    }

    private Accordion createAccordion() {
        Accordion accordion = new Accordion();
        accordion.setMinHeight(300.0);

        // 第一个TitledPane
        TitledPane pane1 = new TitledPane();
        pane1.setText("untitled 1");
        pane1.setAnimated(false);

        AnchorPane content1 = new AnchorPane();
        content1.setMinHeight(0.0);
        content1.setMinWidth(0.0);
        content1.setPrefHeight(180.0);
        content1.setPrefWidth(200.0);

        Tile tile = new Tile();
        tile.setTitle("Title");
        tile.setDescription(
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean sagittis vehicula elit, ac dictum metus bibendum id. Integer elit purus, varius ac eros eu, convallis ultricies tellus.");

        AnchorPane.setBottomAnchor(tile, 0.0);
        AnchorPane.setLeftAnchor(tile, 0.0);
        AnchorPane.setRightAnchor(tile, 0.0);
        AnchorPane.setTopAnchor(tile, 0.0);

        content1.getChildren().add(tile);
        pane1.setContent(content1);

        // 第二个TitledPane
        TitledPane pane2 = new TitledPane();
        pane2.setText("untitled 2");
        pane2.setAnimated(false);

        AnchorPane content2 = new AnchorPane();
        content2.setMinHeight(0.0);
        content2.setMinWidth(0.0);
        content2.setPrefHeight(180.0);
        content2.setPrefWidth(200.0);
        pane2.setContent(content2);

        // 第三个TitledPane
        TitledPane pane3 = new TitledPane();
        pane3.setText("untitled 3");
        pane3.setAnimated(false);

        AnchorPane content3 = new AnchorPane();
        content3.setMinHeight(0.0);
        content3.setMinWidth(0.0);
        content3.setPrefHeight(180.0);
        content3.setPrefWidth(200.0);
        pane3.setContent(content3);

        accordion.getPanes().addAll(pane1, pane2, pane3);
        return accordion;
    }
}
