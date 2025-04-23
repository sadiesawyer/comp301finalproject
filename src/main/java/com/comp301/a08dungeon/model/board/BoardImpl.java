package com.comp301.a08dungeon.model.board;

import com.comp301.a08dungeon.model.pieces.*;
import com.comp301.a08dungeon.model.pieces.CollisionResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Header
// Surprise surprise, BoardImpl should implement the Board interface.
//
// Instance Variables
// You may choose to have as many instance variables as you find useful. The only one that you must
// have, is a properly encapsulated instance of 2-Dimensional array of Pieces called board. Hint: As
// you go through this class, think about what instance variables may be useful that would prevent
// you from searching the entire board for a piece.
//
// Constructors
// You should have two constructors:
//
// One that takes in a width and a height and initializes a board array with those dimensions along
// with any other instance variables you choose to make.
// One that takes in a Piece[][] array and sets your instance variable with the parameter along with
// any other instance variables you choose to make.
// At initialization, the board is empty (full of null pieces).
//
// (Optional) toString
// Consider writing a toString method for board so that you can use print statements to help debug
// your code.

public class BoardImpl implements Board {
  private Piece[][] board;
  private int width;
  private int height;
  private int heroRow;
  private int heroCol;
  private Hero hero;
  private List<Enemy> enemies;

  public BoardImpl(Piece[][] board) {
    this.board = board;
    this.height = board.length;
    this.width = board[0].length;
    this.enemies = new ArrayList<>();
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        if (board[r][c] instanceof Hero) {
          this.hero = (Hero) board[r][c];
          this.heroRow = r;
          this.heroCol = c;
        }
        if (board[r][c] instanceof Enemy) {
          this.enemies.add((Enemy) board[r][c]);
        }
      }
    }
  }

  public BoardImpl(int width, int height) {
    this.height = height;
    this.width = width;
    this.board = new Piece[height][width];
    this.enemies = new ArrayList<>();
    this.hero = null;
  }

  @Override
  public void init(int enemies, int treasures, int walls) {
    if (enemies + treasures + walls
        > this.height * this.width) { // if there are more pieces than spots
      throw new IllegalArgumentException();
    } else {
      this.hero = null;
      this.enemies.clear();
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          board[r][c] = null;
        }
      } // clear board
      for (int i = 0; i < enemies; i++) placeRandomly(new Enemy());
      for (int i = 0; i < walls; i++) placeRandomly(new Wall());
      for (int i = 0; i < treasures; i++) placeRandomly(new Treasure());
      placeRandomly(new Exit());
      if (this.hero == null) {
        placeRandomly(new Hero());
      }
      this.enemies.clear();
      for (int r = 0; r < height; r++) {
        for (int c = 0; c < width; c++) {
          if (board[r][c] instanceof Enemy) {
            this.enemies.add((Enemy) board[r][c]);
          }
        }
      }
    }
    System.out.println("=== INIT COMPLETE ===");
    System.out.println("Board dimensions: " + width + "x" + height);
    System.out.println("Enemies on board:");
    for (Enemy e : this.enemies) {
      Posn pos = e.getPosn();
      System.out.println("- Enemy at (" + pos.getRow() + ", " + pos.getCol() + ")");
    }
    System.out.println("Total enemies: " + this.enemies.size());
    System.out.println("Hero at: (" + heroRow + ", " + heroCol + ")");
    System.out.println("=====================");

    printBoard();
  }

  private void placeRandomly(Piece piece) {
    Random rand = new Random();
    while (true) {
      int row = rand.nextInt(height); // random integer
      int col = rand.nextInt(width);
      if (board[row][col] == null) { // keep going until you find an empty space
        piece.setPosn(new Posn(row, col));
        board[row][col] = piece;
        if (piece instanceof Hero) { // if it's a hero be sure to store those coordinates
          this.heroRow = row;
          this.heroCol = col;
          this.hero = (Hero) piece;
          this.hero.setPosn(new Posn(heroRow, heroCol));
        }
        if (piece instanceof Enemy) {
          this.enemies.add((Enemy) piece);
          System.out.println("enemy added!");
        }
        return;
      }
    }
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  public Piece[][] getBoard() {
    return this.board;
  }

  @Override
  public Piece get(Posn posn) {
    return this.board[posn.getRow()][posn.getCol()];
  }

  @Override
  public void set(Piece p, Posn newPos) {
    if (p == null) return;
    Posn oldPos = p.getPosn(); // safe to call once and check
    if (oldPos != null) {
      this.board[oldPos.getRow()][oldPos.getCol()] = null;
    }
    p.setPosn(newPos);
    this.board[newPos.getRow()][newPos.getCol()] = p;
    System.out.println(
        p.getName() + " moved to " + p.getPosn().getRow() + ", " + p.getPosn().getCol());
  }

  // Hero
  //
  // moveHero(int drow, int dcol): The MovablePieces will be able to move UP,DOWN, LEFT, RIGHT from
  // its current position. The inputs drow and dcol should be the delta between the current position
  // and the new position. For example, if the hero is at (2,3) going UP would be moveHero(-1,0).
  // The hero has restrictions on where it can move:
  // The hero cannot move off the bounds of the board.
  // The hero cannot move where a wall is currently placed.
  // If the move is illegal, the hero should remain in its current position. Otherwise, it can move
  // into the new spot and collide() with any piece that might be there.
  //
  // Once the hero has moved, all enemies get a chance to move. (Enemies do not move if the Hero
  // action was illegal)
  //
  // Once all pieces have moved, the method should return a CollisionResult that represents the
  // state of the game. BEWARE there is some order of precedence here.

  // Direction	drow	dcol	Meaning
  // UP	-1	0	Move up one row
  // DOWN	+1	0	Move down one row
  // LEFT	0	-1	Move left one column
  // RIGHT	0	+1	Move right one column
  @Override
  public CollisionResult moveHero(int drow, int dcol) {
    System.out.println("[Hero Move Attempt] drow: " + drow + ", dcol: " + dcol);
    System.out.println("Current position: (" + heroRow + ", " + heroCol + ")");
    System.out.println("Target position: (" + (heroRow + drow) + ", " + (heroCol + dcol) + ")");
    int newRow = this.heroRow + drow;
    int newCol = this.heroCol + dcol;
    int points = 0;
    if (newRow < 0
        || newRow >= this.height
        || newCol < 0
        || newCol >= this.width
        || this.board[newRow][newCol] instanceof Wall) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else {
      Piece target = this.board[newRow][newCol];

      if (target instanceof Exit) {
        CollisionResult result = this.hero.collide(target);
        set(this.hero, new Posn(newRow, newCol));
        this.heroRow = newRow;
        this.heroCol = newCol;
        return result;
      } else if (target instanceof Treasure) {
        points = ((Treasure) target).getValue();
        board[newRow][newCol] = null;
        set(this.hero, new Posn(newRow, newCol));
        this.heroRow = newRow;
        this.heroCol = newCol;
      } else if (target instanceof Enemy) {
        set(this.hero, new Posn(newRow, newCol));
        return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
      } else {
        set(this.hero, new Posn(newRow, newCol));
        this.heroRow = newRow;
        this.heroCol = newCol;
      }

      for (Enemy e : enemies) {
        CollisionResult.Result r = moveEnemy(e);
        if (r == CollisionResult.Result.GAME_OVER) {
          return new CollisionResult(points, CollisionResult.Result.GAME_OVER);
        }
      }

      return new CollisionResult(points, CollisionResult.Result.CONTINUE);
    }
  }

  // Enemy Movement
  //
  // For now, our enemies are going to be dumb. Every enemy should pick a random direction to go.
  // However, they have more restrictions on its movement than a Hero.
  //
  // Enemy cannot move off the bounds of the board.
  // Enemy cannot move where a Wall is currently placed.
  // Enemy cannot move where an Exit is currently placed.
  // Enemy cannot collide with another Enemy.
  // If an enemy is trapped, it should just skip its turn.

  public CollisionResult.Result moveEnemy(Enemy enemy) {
    int row = enemy.getPosn().getRow();
    int col = enemy.getPosn().getCol();
    int option = 0;

    while (option < 4) {
      int newRow = row;
      int newCol = col;

      switch (option) {
        case 0:
          newRow -= 1; // up
          break;
        case 1:
          newRow += 1; // down
          break;
        case 2:
          newCol -= 1; // left
          break;
        case 3:
          newCol += 1; // right
          break;
      }

      if (newRow >= 0
          && newRow < this.height
          && newCol >= 0
          && newCol < this.width
          && !(board[newRow][newCol] instanceof Wall)
          && !(board[newRow][newCol] instanceof Enemy)
          && !(board[newRow][newCol] instanceof Exit)) {

        Piece target = board[newRow][newCol];
        Posn oldPos = enemy.getPosn();
        Posn newPos = new Posn(newRow, newCol);

        board[oldPos.getRow()][oldPos.getCol()] = null;
        enemy.setPosn(newPos);
        board[newRow][newCol] = enemy;

        if (target instanceof Hero) {
          return CollisionResult.Result.GAME_OVER;
        } else {
          return CollisionResult.Result.CONTINUE;
        }
      }

      option++;
    }
    return CollisionResult.Result.CONTINUE;
  }

  public void printBoard() {
    System.out.println("--- Current Board ---");
    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        Piece p = board[r][c];
        System.out.print((p == null ? "." : p.getName().charAt(0)) + " ");
      }
      System.out.println();
    }
    System.out.println("----------------------");
  }
}
