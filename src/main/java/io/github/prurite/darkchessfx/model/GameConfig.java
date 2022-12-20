package io.github.prurite.darkchessfx.model;

public class GameConfig {
    public int maxTurnTime; // 0 for disable
    public int maxTotalTime; // 0 for disable
    public int minimumScore;
    public boolean allowCheat;
    public boolean allowWithdraw;
    public int aiDifficulty; // 0 for disable
    public int lanPort; // 0 for disable
    public int defaultLanPort = 10900;
    public boolean isReplay;
    public String lanHostIp;
    public String lanPassword;
    public String player1;
    public String defaultPlayer1 = "Player1";
    public String player2;
    public String defaultPlayer2 = "Player2";

    public GameConfig() {
        maxTurnTime = 30;
        maxTotalTime = 1200;
        minimumScore = 60;
        allowCheat = false;
        allowWithdraw = false;
        aiDifficulty = 0;
        lanPort = 0;
        lanPassword = "";
        player1 = defaultPlayer1;
        player2 = defaultPlayer2;
    }

    // put all information into a string, spaced by space
    @Override
    public String toString() {
        return maxTurnTime + " " + maxTotalTime + " " + minimumScore + " " + allowCheat + " " + allowWithdraw + " " + aiDifficulty + " " + lanPort + " " + lanPassword + " " + player1 + " " + player2;
    }

    // set all information with String given by toString
    public void init(String s) {
        String[] ss = s.split(" ");
        maxTurnTime = Integer.parseInt(ss[0]);
        maxTotalTime = Integer.parseInt(ss[1]);
        minimumScore = Integer.parseInt(ss[2]);
        allowCheat = Boolean.parseBoolean(ss[3]);
        allowWithdraw = Boolean.parseBoolean(ss[4]);
        aiDifficulty = Integer.parseInt(ss[5]);
        lanPort = Integer.parseInt(ss[6]);
        lanPassword = ss[7];
        player1 = ss[8];
        player2 = ss[9];
    }
}
