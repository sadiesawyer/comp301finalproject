package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.Observer;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

import java.awt.*;

public class View implements FXComponent, Observer {
  private Model model;
  private Controller controller;
  private StackPane root;
  private TitleScreenView titleScreenView;
  private GameView gameScreenView;

  public View(Controller controller, Model model) {
    this.model = model;
    this.controller = controller;
    this.root = new StackPane();
    this.titleScreenView = new TitleScreenView(model, controller);
    this.gameScreenView = new GameView(model, controller);
  }

  // Label that displays the overall Highest Score.
  // Label that displays the Last Score.
  // Button that will start the game.
  // (Optional Bonus Interactions)
  // "By [your name]"

  public StackPane render() {
    root.getChildren().clear();

    if (model.getStatus() == Model.STATUS.END_GAME) {
      root.getChildren().add(titleScreenView.render());
    } else {
      System.out.println("Switching to gameview!");
      root.getChildren().add(gameScreenView.render());
    }

    return root;
  }

  @Override
  public void update() {
    render();

  }
}
