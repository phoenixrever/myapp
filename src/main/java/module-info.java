module myapp {
    requires transitive atlantafx.base;
    requires java.desktop;
    requires transitive java.prefs;
    requires javafx.swing;
    requires javafx.media;
    requires javafx.web;
    requires javafx.fxml;
    requires jdk.zipfs;

    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.material2;
    requires org.jetbrains.annotations;

    // 工具类依赖
    requires org.apache.commons.lang3; // Apache Commons Lang 工具类库
    requires org.apache.commons.io; // Apache Commons IO 工具类库
    requires org.apache.commons.collections4; // Apache Commons Collections 工具类库
    // requires org.apache.logging.log4j; // Apache Log4j 日志库
    requires org.reflections; // Reflections 反射扫描库

    // JSON 相关
    requires com.fasterxml.jackson.databind; // Jackson Databind

    requires datafaker;
    requires javafx.graphics;

    opens com.phoenixhell.app.fake.domain;

    requires org.tinylog.api; // Tinylog 日志库
    requires org.tinylog.impl;
    requires net.harawata.appdirs;

    exports com.phoenixhell.app;
    exports com.phoenixhell.app.service;
    exports com.phoenixhell.app.controller;
    exports com.phoenixhell.app.ui;
    exports com.phoenixhell.app.util;
    exports com.phoenixhell.app.exception;
    exports com.phoenixhell.app.config;
    exports com.phoenixhell.app.model;
    exports com.phoenixhell.app.contract;
    exports com.phoenixhell.app.ui.layout;
    exports com.phoenixhell.app.ui.page;
    exports com.phoenixhell.app.ui.theme;
    exports com.phoenixhell.app.ui.page.components;
    exports com.phoenixhell.app.ui.page.general;
    exports com.phoenixhell.app.ui.page.showcase;

    // exports com.phoenixhell.app.i18n;

    // resources
    // opens myapp;
    // opens myapp.assets.highlightjs;
    // opens myapp.assets.styles;
    // opens myapp.images;
    // opens myapp.images.modena;
    // opens myapp.media;
    // opens myapp.page.general;
    // opens myapp.page.showcase;

}
