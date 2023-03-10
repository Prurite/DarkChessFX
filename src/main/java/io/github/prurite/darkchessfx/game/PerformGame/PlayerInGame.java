package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.model.Player;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class PlayerInGame {
    private double totalTime;
    private SimpleIntegerProperty scoreProperty;

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
        this.scoreProperty = new SimpleIntegerProperty(0);
    }

    public String getName() { return player.getName(); }

    public SimpleStringProperty getNameProperty() {
        return player.getNameProperty();
    }

    public void addScore(int x) {
        scoreProperty.set(scoreProperty.get() + x);
        //System.out.printf("DEBUG: ADD SCORE %d -> %d  \n", x, scoreProperty.get());
    }
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
    public int getScore() {
        //System.out.printf("DUBUG : GET SCORE %d\n", scoreProperty.get());
        return scoreProperty.get();
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

    public String toString() {
        return player.getName() + " " + side.toString() + " " + totalTime + " " + scoreProperty.get();
    }
    public void init(String s) {
        String[] ss = s.split(" ");
        player.setName(ss[0]);
        side = Side.valueOf(ss[1]);
        totalTime = Double.parseDouble(ss[2]);
        scoreProperty.set(Integer.parseInt(ss[3]));
    }
}
