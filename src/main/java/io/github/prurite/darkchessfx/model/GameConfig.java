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
}
