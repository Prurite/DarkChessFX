package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.model.Pos;

public class Move {
    int curx, cury;
    int newx, newy;

    public Move() { curx = cury = newx = newy = -1; }
    public Move(Pos pos1, Pos pos2) {
        curx = pos1.getX();
        cury = pos1.getY();
        newx = pos2.getX();
        newy = pos2.getY();
    }

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
    // swap X and Y in move
    public Move swapXY() {
        return new Move(cury, curx, newy, newx);
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
