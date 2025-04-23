package com.comp301.a08dungeon.model.pieces;

public class Hero extends APiece implements MovablePiece {
  // When certain pieces collide on the board, specific outcomes occur. For the hero:
  //
  // If the hero collides with nothing (null), game should continue unaffected.
  // The game will continue, but the score may change if the hero collides with a piece of treasure.
  // The game will end if the hero collides with an enemy.
  // The hero will move to the next level if they collide with the exit.
  public Hero() {
    super("Hero", "src/main/resources/enemy");
  }

  // A Hero can make it to the exit, in which case, the Enemy should not be able to perform another
  // action. The CollisionResult should be Result.NEXT_LEVEL.
  // A Hero can get a treasure, thus increasing the score, and then immediately die by an Enemy. In
  // which case, the CollisionResult would need both the score from the Hero's action, and the
  // Result.GAME_OVER from the Enemy's action.
  // If gameplay should continue after everyone has moved, the CollisionResult should be
  // Result.CONTINUE
  public CollisionResult collide(Piece other) {
    if (other == null) {
      return new CollisionResult(0, CollisionResult.Result.CONTINUE);
    } else if (other instanceof Exit) {
      return new CollisionResult(0, CollisionResult.Result.NEXT_LEVEL);
    } else if (other instanceof Treasure) {
      return new CollisionResult(((Treasure) other).getValue(), CollisionResult.Result.CONTINUE);
    } else if (other instanceof Enemy) {
      return new CollisionResult(0, CollisionResult.Result.GAME_OVER);
    } else {
      throw new IllegalArgumentException();
    }
  }
}
