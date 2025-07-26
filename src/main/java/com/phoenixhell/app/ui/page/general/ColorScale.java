package com.phoenixhell.app.ui.page.general;

import java.util.Arrays;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

final class ColorScale extends FlowPane {

    private final List<com.phoenixhell.app.ui.page.general.ColorScaleBlock> blocks;

    public ColorScale(ReadOnlyObjectProperty<Color> bgBaseColor) {
        super();

        blocks = Arrays.asList(
            com.phoenixhell.app.ui.page.general.ColorScaleBlock.forColorPrefix(bgBaseColor, "-color-base-", 10),
            com.phoenixhell.app.ui.page.general.ColorScaleBlock.forColorPrefix(bgBaseColor, "-color-accent-", 10),
            com.phoenixhell.app.ui.page.general.ColorScaleBlock.forColorPrefix(bgBaseColor, "-color-success-", 10),
            com.phoenixhell.app.ui.page.general.ColorScaleBlock.forColorPrefix(bgBaseColor, "-color-warning-", 10),
            com.phoenixhell.app.ui.page.general.ColorScaleBlock.forColorPrefix(bgBaseColor, "-color-danger-", 10),
            com.phoenixhell.app.ui.page.general.ColorScaleBlock.forColorName(bgBaseColor, "-color-dark", "-color-light")
        );

        setId("color-scale");
        getChildren().setAll(blocks);
    }

    public void updateColorInfo(Duration delay) {
        var t = new Timeline(new KeyFrame(delay));
        t.setOnFinished(e -> blocks.forEach(com.phoenixhell.app.ui.page.general.ColorScaleBlock::update));
        t.play();
    }
}
