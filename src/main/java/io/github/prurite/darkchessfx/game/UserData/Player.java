package io.github.prurite.darkchessfx.game.UserData;

import javafx.beans.property.StringProperty;

public class Player {
    private StringProperty name;
    private int gameCount;
    private int scoredGameCount;
    private int winnedGameCount;
    private double totalGameTime;

    public Player(String s) {
        this.name = StringProperty(s);
        this.gameCount = 0;
        this.scoredGameCount = 0;
        this.winnedGameCount = 0;
        this.totalGameTime = 0;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player) {
            return name.equals(((Player)obj).name);
        }
        return false;
    }
    public void addTime(double x) { totalGameTime += x; }
    public void addGameCount() { gameCount++; }
    public void addScoredGameCount() { scoredGameCount++; }
    public void addWinnedGameCount() { winnedGameCount++; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }

    public int getScoredGameCount() {
        return scoredGameCount;
    }

    public void setScoredGameCount(int scoredGameCount) {
        this.scoredGameCount = scoredGameCount;
    }

    public int getWinnedGameCount() {
        return winnedGameCount;
    }

    public void setWinnedGameCount(int winnedGameCount) {
        this.winnedGameCount = winnedGameCount;
    }

    public double getTotalGameTime() {
        return totalGameTime;
    }

    public void setTotalGameTime(double totalGameTime) {
        this.totalGameTime = totalGameTime;
    }
}

