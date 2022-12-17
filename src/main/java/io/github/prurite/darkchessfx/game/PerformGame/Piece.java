package io.github.prurite.darkchessfx.game.PerformGame;

public class Piece {
    Chess type;
    Side side;
    public Piece(Chess type, Side side) {
        this.type = type;
        this.side = side;
    }
    public Chess getType() {
        return type;
    }

    public void setType(Chess type) {
        this.type = type;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
