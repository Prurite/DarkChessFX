package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.UserData.Player;

public class PlayerInGame {
    private double totalTime;
    private int score;

    private Side side;
    private final Player player;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PlayerInGame) {
            return player.equals(((PlayerInGame)obj).player);
        }
        return false;
    }

    public PlayerInGame(Player player, Side side) {
        this.player = player;
        this.side = side;
        this.totalTime = 0;
        this.score = 0;
    }

    public void addScore(int x) { score += x; }
    public void subTotalTime(double t) { totalTime += t; }

    public Player getPlayer() {
        return player;
    }

    //public void setPlayer(Player player) {
    //    this.player = player;
    //}

    public double getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(double totalTime) {
        this.totalTime = totalTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
