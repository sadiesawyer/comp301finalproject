package com.comp301.a08dungeon.model;

import com.comp301.a08dungeon.model.board.*;
import com.comp301.a08dungeon.model.board.Posn;
import com.comp301.a08dungeon.model.pieces.CollisionResult;
import com.comp301.a08dungeon.model.pieces.Piece;
import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {
  private Board board;
  private int curScore;
  private int highScore;
  private Model.STATUS status;
  private int level;
  private List<Observer> observers;

  // Write a constructor that takes in an int width and int height, and creates a new board, and
  // initializes all instance variables to their pregame state. All ints are 0 ,and the STATUS
  // should be END_GAME.

  public ModelImpl(int width, int height) {
    this.board = new BoardImpl(width, height);
    this.curScore = 0;
    this.highScore = 0;
    this.status = Model.STATUS.END_GAME;
    this.observers = new ArrayList<>();
    this.level = 0;
  }

  public ModelImpl(Board board) {
    this.board = board;
    this.curScore = 0;
    this.highScore = 0;
    this.status = Model.STATUS.END_GAME;
    this.observers = new ArrayList<>();
    this.level = 0;
  }

  @Override
  public int getWidth() {
    return this.board.getWidth();
  }

  @Override
  public int getHeight() {
    return this.board.getHeight();
  }

  @Override
  public Piece get(Posn p) {
    return this.board.get(p);
  }

  @Override
  public int getCurScore() {
    return this.curScore;
  }

  @Override
  public int getHighScore() {
    return this.highScore;
  }

  @Override
  public int getLevel() {
    return this.level;
  }

  @Override
  public Model.STATUS getStatus() {
    return this.status;
  }

  // startGame()
  //
  // This is where we initialize our board and start the game logic. To begin, change the status of
  // the game to IN_PROGRESS. Make sure that the current score is 0, and that the level resets to 1.
  // Every time the hero reaches an exit, the board will be re initialized and the level will
  // increment by 1. The number of Enemy pieces will be equal to (level + 1). So the first room will
  // have 2 Enemy pieces, the second room will have 3, etc. You must have at least 2 Treasure pieces
  // and 2 Wall pieces. However, you have freedom to decide what numbers you prefer for your game.
  //
  // This means that even a perfect game will end when there are more pieces than spots on the
  // board. When this happens, be sure to end the game.
  //
  // endGame()
  //
  // Chang the status to END_GAME and check to see if there is a new high score.
  @Override
  public void startGame() {
    this.status = STATUS.IN_PROGRESS;
    if (this.level == 0) {
      this.curScore = 0;
      this.level = 1;
    }
    try {
      this.board.init(this.level + 1, 2, 2);
    } catch (IllegalArgumentException e) {
      endGame();
    } finally {
      notifyObservers();
    }
  }

  @Override
  public void endGame() {
    this.status = STATUS.END_GAME;
    if (this.curScore > this.highScore) {
      this.highScore = this.curScore;
    }
    notifyObservers();
  }

  // Movement
  // Next, our model specifies the actions that the user can take. In this case, moving our Hero up,
  // down, left, or right. Using our board.moveHero() method, move the hero in the correct
  // direction, then check the CollisionResult to see if the game state needs to be updated
  // accordingly. If there are points in the CollisionResult, update the score. If it returns a
  // CollisionResult.Result.NEXT_LEVEL, reinitialize the board at the next level. If it returns
  // CollisionResult.Result.GAME_OVER, you should end the game.
  //
  // Observer Pattern
  // Finally, we have all the pieces to implement an Observer pattern.
  //
  // Implement the addObserver() method so that Observers can subscribe to changes in the model.
  // Implement the private notifyObservers() method that calls the update() method specified by the
  // Observer interface.
  // Go through the model again. This time, whenever the game state changes, notify observers of the
  // change.
  // Congrats! You've finished the backend of your dungeon crawler game.

  @Override
  public void moveUp() {
    CollisionResult result = this.board.moveHero(-1, 0);
    this.curScore += result.getPoints();
    if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      this.level++;
      startGame();
    } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
      endGame();
    }
    notifyObservers();
  }

  @Override
  public void moveDown() {
    CollisionResult result = this.board.moveHero(1, 0);
    this.curScore += result.getPoints();
    if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      this.level++;
      startGame();
    } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
      endGame();
    }
    notifyObservers();
  }

  @Override
  public void moveLeft() {
    CollisionResult result = this.board.moveHero(0, -1);
    this.curScore += result.getPoints();
    if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      this.level++;
      startGame();
    } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
      endGame();
    }
    notifyObservers();
  }

  @Override
  public void moveRight() {
    CollisionResult result = this.board.moveHero(0, 1);
    this.curScore += result.getPoints();
    if (result.getResults() == CollisionResult.Result.NEXT_LEVEL) {
      this.level++;
      startGame();
    } else if (result.getResults() == CollisionResult.Result.GAME_OVER) {
      endGame();
    }
    notifyObservers();
  }

  @Override
  public void addObserver(Observer o) {
    this.observers.add(o);
  }

  private void notifyObservers() {
    for (Observer o : observers) {
      o.update();
    }
  }
}
