package com.comp301.a08dungeon.controller;

import com.comp301.a08dungeon.model.Model;
import com.comp301.a08dungeon.model.board.Board;

public class ControllerImpl implements Controller {
    private Model model;

    public ControllerImpl(Model model) {
        this.model = model;
    }

    @Override
    public void moveUp() {
        model.moveUp();

    }

    @Override
    public void moveDown() {
        model.moveDown();

    }

    @Override
    public void moveLeft() {
        model.moveLeft();

    }

    @Override
    public void moveRight() {
        model.moveRight();

    }

    @Override
    public void startGame() {
        model.startGame();

    }
}
