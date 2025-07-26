package com.phoenixhell.app.controller;

import com.phoenixhell.app.annotation.Control;
import com.phoenixhell.app.annotation.View;
import com.phoenixhell.app.service.UserService;
import com.phoenixhell.app.ui.MainView;

public class UserViewController {

  @View
  private MainView view;

  @Control
  private UserService userService;

  public void setView(MainView view) {
    this.view = view;
    initEvent();
  }

  public MainView getView() {
    return view;
  }

  private void initEvent() {
    // view.getButton().setOnAction(e -> {
    // String name = userService.getUserName();
    // view.getLabel().setText("用户：" + name);
    // });
  }
}
