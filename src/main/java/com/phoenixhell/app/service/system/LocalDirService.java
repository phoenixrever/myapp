package com.phoenixhell.app.service.system;

import net.harawata.appdirs.AppDirs;
import net.harawata.appdirs.AppDirsFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

/**
 * 本地目录服务，用于获取应用的本地数据目录。
 * 通过 AppDirs 库获取跨平台的用户数据目录。
 *
 * 需要在 config.properties 中配置应用名称
 * 例如：lowercase_name=myapp
 *
 * TODO : 最好放在程序文件夹下 待后面删除net.harawata.appdirs
 */
public class LocalDirService {

    private static String appLocalDirName;

    public static String getUserDataDirPath() throws IOException {

        if (appLocalDirName == null) {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("config");
            appLocalDirName = resourceBundle.getString("lowercase_name");
        }

        AppDirs appDirs = AppDirsFactory.getInstance();
        String path = appDirs.getUserDataDir(appLocalDirName, null, null);
        Files.createDirectories(Paths.get(path)); // "C:\Users\phoen\AppData\Local\myapp"

        return path;
    }

}
