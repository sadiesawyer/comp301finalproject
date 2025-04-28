package com.comp301.a08dungeon.view;

import com.comp301.a08dungeon.controller.Controller;
import com.comp301.a08dungeon.model.Model;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TitleScreenView implements FXComponent {
    private Model model;
    private Controller controller;

    public TitleScreenView(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
    }


    public VBox render() {
        VBox TitleScreen = new VBox();
        TitleScreen.getStylesheets().add("dungeon.css");
        TitleScreen.getStyleClass().add("layout");

        Label title = new Label("Ladybug Dungeon Crawler 🐞");
        title.getStyleClass().add("label-title");

        Label highScore = new Label("High Score: " + model.getHighScore());
        highScore.getStyleClass().add("label-score");

        Label lastScore = new Label("Last Score: " + model.getCurScore());
        lastScore.getStyleClass().add("label-score");

        Button startButton = new Button("Start Game");
        startButton.getStyleClass().add("button-start");
        startButton.setOnAction((
        ActionEvent e) -> {controller.startGame();});

        Label author = new Label("By: Sadie Sawyer");
        author.getStyleClass().add("label-author");

        ToggleButton mode = new ToggleButton("Dark Mode");
        mode.getStylesheets().add("toggle-button");
        mode.setOnAction(
                (ActionEvent e) -> {
                    if (mode.isSelected()) {
                        mode.setText("Light Mode");
                        TitleScreen.getStylesheets().remove("dungeon.css");
                        TitleScreen.getStylesheets().add("dungeon-darkmode.css");
                    } else {
                        mode.setText("Dark Mode");
                        TitleScreen.getStylesheets().remove("dungeon-darkmode.css");
                        TitleScreen.getStylesheets().add("dungeon.css");
                    }
                });

        TitleScreen.getChildren().addAll(title, highScore, lastScore, startButton, author, mode);
        return TitleScreen;
}
}