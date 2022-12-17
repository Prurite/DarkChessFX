package io.github.prurite.darkchessfx.game.PerformGame;

public enum Side {
    BLACK,
    RED;

    public Side getOpposite() {
        return this == BLACK ? RED : BLACK;
    }
}
