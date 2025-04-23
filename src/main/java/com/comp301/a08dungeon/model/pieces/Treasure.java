package com.comp301.a08dungeon.model.pieces;

public class Treasure extends APiece {

  public Treasure() {
    super("Treasure", "src/main/resources/treasure");
  }

  public int getValue() {
    return 100;
  }
}
