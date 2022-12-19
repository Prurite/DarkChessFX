package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.LoadingExceptions.InvalidChessType;

public class Piece {
    Chess type;
    Side side;
    @Override
    public boolean equals(Object obj) { // equals if type and side are the same
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj instanceof Piece) {
            Piece p = (Piece) obj;
            if(p.getType() != type) return false;
            if(type == Chess.Unknown || type == Chess.Empty) return true;
            return p.getSide() == side;
        }
        else return false;
    }
    public Piece(Chess type, Side side) {
        this.type = type;
        this.side = side;
    }
    public Piece(Piece p) {
        this.type = p.getType();
        this.side = p.getSide();
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

    public String toString() {
        return type.toString() + " " + side.toString();
    }
    public void init(String s) throws InvalidChessType {
        String[] ss = s.split(" ");
        boolean flag = false;
        for(Chess c : Chess.values()) if(c == Chess.valueOf(ss[0])) {
            type = c;
            flag = true;
            break;
        }
        if(flag == false) {
            throw new InvalidChessType();
        }
        side = Side.valueOf(ss[1]);
    }
}
