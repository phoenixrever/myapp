package com.phoenixhell.app.ui.page.general;

import com.phoenixhell.app.ui.layout.ModalDialog;
import com.phoenixhell.app.util.ui.JColorUtils;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.paint.Color;

class ContrastCheckerDialog extends ModalDialog {

    private final ContrastChecker contrastChecker;

    public ContrastCheckerDialog(ReadOnlyObjectProperty<Color> bgBaseColor) {
        super();

        this.contrastChecker = new ContrastChecker(bgBaseColor);

        contrastChecker.bgColorProperty().addListener((obs, old, val) -> updateStyle());
        contrastChecker.fgColorProperty().addListener((obs, old, val) -> updateStyle());

        getStyleClass().add("contrast-checker-dialog");
        header.setTitle("Contrast Checker");
        content.setBody(contrastChecker);
        content.setFooter(null);
    }

    private void updateStyle() {
        setStyle(String.format("-color-contrast-checker-bg:%s;-color-contrast-checker-fg:%s;",
                JColorUtils.toHexWithAlpha(contrastChecker.getFlatBgColor()),
                JColorUtils.toHexWithAlpha(contrastChecker.getSafeFgColor())));
    }

    public ContrastChecker getContent() {
        return contrastChecker;
    }
}
