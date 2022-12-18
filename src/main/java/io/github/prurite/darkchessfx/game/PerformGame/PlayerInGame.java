package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.model.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlayerInGame {
    private double totalTime;
    private SimpleIntegerProperty scoreProperty;

    private Side side;
    private final Player player;
    public SimpleStringProperty nameProperty;

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
        this.scoreProperty.set(0);
        nameProperty = new SimpleStringProperty(player.getName());
    }

    public SimpleStringProperty getNameProperty() {
        return nameProperty;
    }

    public void addScore(int x) { scoreProperty.add(x); }
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

    public SimpleIntegerProperty getScoreProperty() {
        return scoreProperty;
    }

    public void setScore(int score) {
        this.scoreProperty.set(score);
    }

    public Side getSide() {
        return side;
    }

    public void setSide(Side side) {
        this.side = side;
    }
}
