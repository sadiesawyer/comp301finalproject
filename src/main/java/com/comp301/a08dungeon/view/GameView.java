package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.Piece;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GameView implements FXComponent {
    private Model model;
    private Controller controller;

    public GameView(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }

    public Parent render() {
        Pane gameView = new Pane();
        GridPane GameBoard = new GridPane();
        GameBoard.getStyleClass().add("layout");
        gameView.getStyleClass().add("layout");
        for (int i = 0; i < model.getWidth(); i++) {
            for (int j = 0; j < model.getHeight(); j++) {
                GameBoard.add(addImage(j, i), j, i);
            }
        }

        Image image = new Image("arrow.png");
        ImageView leftArrow = new ImageView(image);
        leftArrow.setFitWidth(30);
        leftArrow.setFitHeight(30);
        leftArrow.setRotate(270);

        ImageView rightArrow = new ImageView(image);
        rightArrow.setFitWidth(30);
        rightArrow.setFitHeight(30);
        rightArrow.setRotate(90);

        ImageView upArrow = new ImageView(image);
        upArrow.setFitWidth(30);
        upArrow.setFitHeight(30);
        upArrow.setRotate(0);

        ImageView downArrow = new ImageView(image);
        downArrow.setFitWidth(30);
        downArrow.setFitHeight(30);
        downArrow.setRotate(180);

        Button leftButton = new Button();
        leftButton.setOnAction((ActionEvent e) -> {controller.moveLeft();});
        leftButton.setGraphic(leftArrow);

        Button rightButton = new Button();
        rightButton.setOnAction((ActionEvent e) -> {controller.moveRight();});
        rightButton.setGraphic(rightArrow);

        Button downButton = new Button();
        downButton.setOnAction((ActionEvent e) -> {controller.moveDown();});
        downButton.setGraphic(downArrow);

        Button upButton = new Button();
        upButton.setOnAction((ActionEvent e) -> {controller.moveUp();});
        upButton.setGraphic(upArrow);

        gameView.getChildren().add(GameBoard);
        gameView.getChildren().add(leftButton);
        gameView.getChildren().add(rightButton);
        gameView.getChildren().add(upButton);
        gameView.getChildren().add(downButton);

        leftButton.setLayoutX(725);
        leftButton.setLayoutY(750);
        rightButton.setLayoutX(845);
        rightButton.setLayoutY(750);
        upButton.setLayoutX(785);
        upButton.setLayoutY(700);
        downButton.setLayoutX(785);
        downButton.setLayoutY(800);

        Label highScore = new Label("High Score: " + model.getHighScore());
        highScore.getStyleClass().add("label-score");

        Label lastScore = new Label("Current Score: " + model.getCurScore());
        lastScore.getStyleClass().add("label-score");

        Label currentLevel = new Label("Current Level: " + model.getLevel());
        currentLevel.getStyleClass().add("label-score");

        highScore.setLayoutX(760);
        highScore.setLayoutY(200);

        currentLevel.setLayoutX(760);
        currentLevel.setLayoutY(400);

        lastScore.setLayoutX(760);
        lastScore.setLayoutY(300);

        Label instructions = new Label("Press the arrows to move the ladybug hero!\nBe sure to avoid the spiders, and try to make it to the wind to fly to the next level.");
        instructions.setLayoutX(50);
        instructions.setLayoutY(750);
        instructions.getStyleClass().add("label-author");

        ToggleButton mode = new ToggleButton("Dark Mode");
        mode.getStylesheets().add("toggle-button");
        mode.setOnAction(
                (ActionEvent e) -> {
                    if (mode.isSelected()) {
                        mode.setText("Light Mode");
                        gameView.getStylesheets().remove("dungeon.css");
                        gameView.getStylesheets().add("dungeon-darkmode.css");
                    } else {
                        mode.setText("Dark Mode");
                        gameView.getStylesheets().remove("dungeon-darkmode.css");
                        gameView.getStylesheets().add("dungeon.css");
                    }
                });
        if (mode.isSelected()) {
            mode.setText("Light Mode");
            gameView.getStylesheets().remove("dungeon.css");
            gameView.getStylesheets().add("dungeon-darkmode.css");
        } else {
            mode.setText("Dark Mode");
            gameView.getStylesheets().remove("dungeon-darkmode.css");
            gameView.getStylesheets().add("dungeon.css");
        }

        mode.setLayoutX(775);
        mode.setLayoutY(100);

        gameView.getChildren().addAll(highScore, lastScore, currentLevel, instructions, mode);

        return gameView;
    }

    private Node addImage(int j, int i) {
        Piece piece = model.get(new Posn(i, j));
        StackPane tile = new StackPane();
        tile.getStyleClass().add("tile");
        if (piece != null) {
            String path = piece.getResourcePath();
            if (path != null) {
                try {
                    Image image = new Image(path);
                    ImageView imgview = new ImageView(image);
                    imgview.setFitWidth(60);
                    imgview.setFitHeight(60);
                    tile.getChildren().add(imgview);
                } catch (Exception e) {
                    System.out.println("Failed to load " + path);
                    return new Label("Error");
                }
            }
        }
        tile.setPrefWidth(80);
        tile.setPrefHeight(80);
        return tile;
    }


}
