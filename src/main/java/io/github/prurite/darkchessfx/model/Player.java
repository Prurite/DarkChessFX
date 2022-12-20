package io.github.prurite.darkchessfx.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Player {
    private SimpleStringProperty name;
    private SimpleIntegerProperty gameCount;
    private SimpleIntegerProperty scoredGameCount;
    private SimpleIntegerProperty wonGameCount;
    private SimpleDoubleProperty totalGameTime;
    private SimpleDoubleProperty winningRate;

    public Player(String s) {
        this.name = new SimpleStringProperty(s);
        this.gameCount = new SimpleIntegerProperty(0);
        this.scoredGameCount = new SimpleIntegerProperty(0);
        this.wonGameCount = new SimpleIntegerProperty(0);
        this.totalGameTime = new SimpleDoubleProperty(0);
        this.wonGameCount = new SimpleIntegerProperty(0);
        this.winningRate = new SimpleDoubleProperty(0);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player) {
            return getName().equals(((Player)obj).getName());
        }
        return false;
    }
    private void UPDwinningRate() { winningRate.set((double) wonGameCount.get() / (double)scoredGameCount.get()); }
    public void addTime(double x) { totalGameTime.set(totalGameTime.get() + x); }
    public void addGameCount() { gameCount.set(gameCount.get() + 1); }
    public void addScoredGameCount() { scoredGameCount.set(scoredGameCount.get() + 1); UPDwinningRate();}
    public void addWinnedGameCount() { wonGameCount.set(wonGameCount.get() + 1); UPDwinningRate(); }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getGameCount() {
        return gameCount.get();
    }
    public void setGameCount(int gameCount) {
        this.gameCount.set(gameCount);
    }
    public SimpleStringProperty getNameProperty() { return name; }

    public int getScoredGameCount() {
        return scoredGameCount.get();
    }

    public void setScoredGameCount(int scoredGameCount) {
        this.scoredGameCount.set(scoredGameCount);
    }

    public int getWonGameCount() {
        return wonGameCount.get();
    }

    public void setWonGameCount(int wonGameCount) {
        this.wonGameCount.set(wonGameCount);
    }

    public double getTotalGameTime() {
        return totalGameTime.get();
    }

    public void setTotalGameTime(double totalGameTime) {
        this.totalGameTime.set(totalGameTime);
    }
    public double getWinningRate() {
        return winningRate.get();
    }
    public void setWinningRate(double winningRate) {
        this.winningRate.set(winningRate);
    }
    public SimpleIntegerProperty getGameCountProperty() {
        return gameCount;
    }
    public SimpleIntegerProperty getScoredGameCountProperty() {
        return scoredGameCount;
    }
    public SimpleIntegerProperty getWinnedGameCountProperty() {
        return wonGameCount;
    }
    public SimpleDoubleProperty getTotalGameTimeProperty() {
        return totalGameTime;
    }
    public SimpleDoubleProperty getWinningRateProperty() {
        return winningRate;
    }
}

