package com.phoenixhell.app.util.ui;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public final class PlatformUtils {

    public static void copyToClipboard(String s) {
        var content = new ClipboardContent();
        content.putString(s);
        Clipboard.getSystemClipboard().setContent(content);
    }
}
