package io.github.prurite.darkchessfx.game.PerformGame;

public class Move {
    int curx, cury;
    int newx, newy;

    public Move() { curx = cury = newx = newy = -1; }
    public Move(int x, int y,int a, int b) {
        curx = x;
        cury = y;
        newx = a;
        newy = b;
    }
    public Move(Move move) {
        if(move != null) {
            curx = move.getCurx();
            cury = move.getCury();
            newx = move.getNewx();
            newy = move.getNewy();
        }
    }
    public boolean sameX() { return curx == newx; }
    public boolean sameY() { return cury == newy; }
    public int getDis() {
        return Math.abs(curx - newx) + Math.abs(cury - newy);
    }
    public int getCurx() {
        return curx;
    }

    public void setCurx(int curx) {
        this.curx = curx;
    }

    public int getCury() {
        return cury;
    }

    public void setCury(int cury) {
        this.cury = cury;
    }

    public int getNewx() {
        return newx;
    }

    public void setNewx(int newx) {
        this.newx = newx;
    }

    public int getNewy() {
        return newy;
    }

    public void setNewy(int newy) {
        this.newy = newy;
    }
    public String toString() {
        return "(" + curx + ", " + cury + ") -> (" + newx + ", " + newy + ")";
    }
}
