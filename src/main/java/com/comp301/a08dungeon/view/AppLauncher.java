package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.controller.ControllerImpl;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.ModelImpl;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
      stage.setTitle("Sadie Sawyer's Dungeon Crawler");
      Model model = new ModelImpl(8,8);
      Controller pc = new ControllerImpl(model);
      View view = new View(pc, model);
      model.addObserver(view);
      Scene scene = new Scene(view.render(), 800,800);
      scene.getStylesheets().add("dungeon.css");
      stage.setScene(scene);
      stage.show();
  }
}
