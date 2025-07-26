package com.phoenixhell.app.controller;

import com.phoenixhell.app.annotation.PostConstruct;
import com.phoenixhell.app.annotation.View;
import com.phoenixhell.app.ui.view.Overview;

import javafx.scene.Parent;

public class OverviewController extends BaseController {
    @View
    private Overview overview;

    @PostConstruct
    public void init() {
        // 注册当前控制器为 Translatable 接口的实现 这样可以在语言切换时更新文本
        initView();
        initEvent();
    }

    private void initEvent() {
    }

    private void initView() {
    }

    @Override
    public Parent getView() {
        return overview;
    }

    @Override
    public void updateTexts() {
    }

}
