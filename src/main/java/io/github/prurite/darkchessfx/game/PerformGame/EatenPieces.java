package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.WeakAI.MakeMove;

public class EatenPieces {
    static int BASE_LENGTH = 20;
    long s;
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj instanceof EatenPieces) {
            EatenPieces e = (EatenPieces) obj;
            return s == e.getS();
        }
        else return false;
    }
    public EatenPieces() { s = 0; }
    public EatenPieces(EatenPieces e) {
        s = e.getS();
    }
    public long getS() { return s; }
    public int getTot() {
        int res = 0;
        long s = this.s;
        while(s != 0) {
            res ++;
            s -= s&-s;
        }
        return res;
    }
    public void eatPiece(Piece x) {
        if(x.getType() == Chess.Unknown || x.getType() == Chess.Empty) {
            MakeMove.debug("EatenPieces.eatPiece: eat unknown or empty piece");
        }
        int id = 0;
        id += (x.getSide() == Side.RED ? 1 : 0) * BASE_LENGTH;
        id += (6 - x.getType().getId()) * 2;
        while(((s>>id)&1) != 0) id++;
        s |= 1l<<id;
    }
    public int eaten(Piece x) {
        if(x.getType() == Chess.Unknown || x.getType() == Chess.Empty) {
            return 0;
        }
        int id = 0;
        id += (x.getSide() == Side.RED ? 1 : 0) * BASE_LENGTH;
        id += (6 - x.getType().getId()) * 2;
        int count = 0;
        if(x.getType() == Chess.Soldier) {
            for(int j = 0; j < 5; ++j) count += (s >> (id+j)) & 1;
        }
        else count = (int)(((s>>id)&1) + ((s>>(id+1))&1));
        return count;
    }
    // check if every piece is eaten and the count
    public String toString() {
        String res = "";
        for(int i = 0; i < 2; ++i) {
            res += Side.values()[i].toString() + ": ";
            for(int j = 0; j < 7; ++j) {
                int count = eaten(new Piece(Chess.values()[j], Side.values()[i]));
                if(count > 0) res += count + " " + Chess.values()[j].toString() + " ";
            }
            res += "\n";
        }
        return res;

    }
    public boolean includes(EatenPieces e) {
        return (s & e.getS()) == e.getS();
    }
}