package com.phoenixhell.app.service;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class LocalDirService {

    private static String appLocalDirName;

    public static String getUserDataDirPath() throws IOException {
        System.out.println("资源路径: " + new File("build/classes/java/main/config.properties").exists());

        if (appLocalDirName == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            appLocalDirName = resourceBundle.getString("lowercase_name");
        }

        AppDirs appDirs = AppDirsFactory.getInstance();
        String path = appDirs.getUserDataDir(appLocalDirName, null, null);
        Files.createDirectories(Paths.get(path));
        return path;
    }

}
