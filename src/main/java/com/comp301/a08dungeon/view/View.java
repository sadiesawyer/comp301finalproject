package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.Observer;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class View implements FXComponent, Observer {
  private Model model;
  private Controller controller;

  public View(Controller controller, Model model) {
    this.model = model;
    this.controller = controller;
  }


  public Parent render() {
    Pane s = new StackPane();
    s.getChildren().add(new Label("Hello, World"));
    return s;
  }

  @Override
  public void update() {

  }
}
