package io.github.prurite.darkchessfx.model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Player {
    private SimpleStringProperty name;
    private SimpleIntegerProperty gameCount;
    private SimpleIntegerProperty scoredGameCount;
    private SimpleIntegerProperty winnedGameCount;
    private SimpleDoubleProperty totalGameTime;
    private SimpleDoubleProperty winningRate;

    public Player(String s) {
        this.name = new SimpleStringProperty(s);
        this.gameCount = new SimpleIntegerProperty(0);
        this.scoredGameCount = new SimpleIntegerProperty(0);
        this.winnedGameCount = new SimpleIntegerProperty(0);
        this.totalGameTime = new SimpleDoubleProperty(0);
        this.winnedGameCount = new SimpleIntegerProperty(0);
        this.winningRate = new SimpleDoubleProperty(0);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player) {
            return getName().equals(((Player)obj).getName());
        }
        return false;
    }
    private void UPDwinningRate() { winningRate.set((double)winnedGameCount.get() / (double)scoredGameCount.get()); }
    public void addTime(double x) { totalGameTime.set(totalGameTime.get() + x); }
    public void addGameCount() { gameCount.set(gameCount.get() + 1); }
    public void addScoredGameCount() { scoredGameCount.set(scoredGameCount.get() + 1); UPDwinningRate();}
    public void addWinnedGameCount() { winnedGameCount.set(winnedGameCount.get() + 1); }

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

    public int getWinnedGameCount() {
        return winnedGameCount.get();
    }

    public void setWinnedGameCount(int winnedGameCount) {
        this.winnedGameCount.set(winnedGameCount);
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
        return winnedGameCount;
    }
    public SimpleDoubleProperty getTotalGameTimeProperty() {
        return totalGameTime;
    }
    public SimpleDoubleProperty getWinningRateProperty() {
        return winningRate;
    }
}

