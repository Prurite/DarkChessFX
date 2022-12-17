package io.github.prurite.darkchessfx.game.PerformGame;
public class EatenPieces {
    static int BASE_LENGTH = 20;
    long s;
    public EatenPieces() { s = 0; }
    public EatenPieces(EatenPieces e) {
        s = e.getS();
    }
    public long getS() { return s; }
    public void eatPiece(Piece x) {
        int id = 0;
        id += (x.getSide() == Side.RED ? 1 : 0) * BASE_LENGTH;
        id += (6 - x.getType().getId()) * 2;
        while(((s>>id)&1) != 0) id++;
        s |= 1l<<id;
    }
    public int eaten(Piece x) {
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
}