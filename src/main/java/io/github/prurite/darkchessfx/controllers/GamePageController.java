package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {
    @FXML private Label player1Name;
    @FXML private Label player1Time;
    @FXML private Label player1Score;
    @FXML private Label player2Name;
    @FXML private Label player2Time;
    @FXML private Label player2Score;
    @FXML private Label currentPlayerName;
    @FXML private Label currentPlayerTime;
    @FXML private Label actionLabel;
    @FXML private VBox messagesBox;
    @FXML private TextField messageInput;
    @FXML private StackPane gameArea;
    @FXML private HBox rootPane;
    @FXML private Label title;
    private App app;
    private Game game;
    private GameConfig gameConfig;
    enum GameType {
        LOCAL("Local Game"),
        AI("Local Game - with AI"),
        LAN("LAN Game");

        final String title;
        GameType(String s)
        {
            title = s;
        }
    }

    GameType gameType;

    public GamePageController(Game game, App app) {
        this.game = game;
        this.gameConfig = game.getGameConfig();
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane gameBoard = app.loadGameBoard(game);
        gameArea.getChildren().add(gameBoard);
        if (gameConfig.lanPort != 0)
            gameType = GameType.LAN;
        else if (gameConfig.aiDifficulty != 0)
            gameType = GameType.AI;
        else
            gameType = GameType.LOCAL;
        title.setText(gameType.title);
        player1Name.setText(game.getPlayerInGame1().getPlayer().getName());
        player1Score.setText(Integer.toString(game.getPlayerInGame2().getScore()));

    }

    public void returnToHome() {
        app.changePage("HomePage");
    }

    public void saveGame() {
        game.saveGame();
    }

    public void askForDraw() {
    }

    public void surrender() {
    }

    public void withdraw() {
    }

    public void cheat() {
    }

    public void sendMessage() {
    }
}
