package io.github.prurite.darkchessfx.model;

public class Pos {
    private int x, y;
    public Pos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() { return x; }
    public int getY() { return y; }
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y; }

    public void swapXY() {
        int t = x;
        x = y;
        y = t;
    }
}
