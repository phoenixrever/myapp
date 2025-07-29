package com.phoenixhell.app.util;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.prefs.Preferences;

import com.phoenixhell.app.Launcher;

public final class Resources {

    public static final String MODULE_DIR = "/";

    public static InputStream getResourceAsStream(String resource) {
        String path = resolve(resource);
        return Objects.requireNonNull(
                Launcher.class.getResourceAsStream(resolve(path)),
                "Resource not found: " + path);
    }

    public static URI getResource(String resource) {
        String path = resolve(resource);
        URL url = Objects.requireNonNull(Launcher.class.getResource(resolve(path)), "Resource not found: " + path);
        return URI.create(url.toExternalForm());
    }

    public static String getResourceStr(String resource) {
        String path = resolve(resource);
        URL url = Objects.requireNonNull(Launcher.class.getResource(resolve(path)), "Resource not found: " + path);
        return url.toExternalForm();
    }

    // 自动加 上"/"前缀
    public static String resolve(String resource) {
        Objects.requireNonNull(resource);
        return resource.startsWith("/") ? resource : MODULE_DIR + resource;
    }

    public static String getPropertyOrEnv(String propertyKey, String envKey) {
        return System.getProperty(propertyKey, System.getenv(envKey));
    }

    public static Preferences getPreferences() {
        return Preferences.userRoot().node("atlantafx");
    }
}
