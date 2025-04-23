package com.comp301.a08dungeon.model.pieces;

public class Enemy extends APiece implements MovablePiece {
  public Enemy() {
    super("Enemy", "src/main/resources/enemy");
  }

  // If the enemy collides with nothing (null), game should continue unaffected.
  // The enemy will "eat" any treasure it finds (removing it from the board) and the game will
  // continue.
  // The enemy can catch up with the hero and "eat" him, causing the game to end.
  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else if (other instanceof Hero) {
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    } else if (other instanceof Treasure) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else {
      throw new IllegalArgumentException();
    }
  }
}
