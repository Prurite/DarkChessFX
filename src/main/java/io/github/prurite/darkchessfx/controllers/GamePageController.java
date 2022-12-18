package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class GamePageController implements Initializable {
    @FXML private Label player1Name;
    @FXML private Label player1Score;
    @FXML private Label player2Name;
    @FXML private Label player2Score;
    @FXML private Label currentPlayerName;
    @FXML private Label actionLabel;
    @FXML private Button toggleCheatButton;
    @FXML private VBox messagesBox;
    @FXML private TextField messageInput;
    @FXML private StackPane gameArea;
    @FXML private HBox rootPane;
    @FXML private Label title;
    private App app;
    private Game game;
    private GameConfig gameConfig;
    boolean isCheatMode;
    GameBoardController gameBoardController;
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
        this.isCheatMode = false;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Pane gameBoard = app.loadGameBoard(game, gameBoardController);
        gameBoardController.setGamePageController(this);
        gameArea.getChildren().add(gameBoard);
        if (gameConfig.lanPort != 0)
            gameType = GameType.LAN;
        else if (gameConfig.aiDifficulty != 0)
            gameType = GameType.AI;
        else
            gameType = GameType.LOCAL;
        title.setText(gameType.title);
        player1Name.textProperty().bind(game.getPlayerInGame1().getNameProperty());
        player1Score.textProperty().bind(game.getPlayerInGame1().getScoreProperty().asString());
        player2Name.textProperty().bind(game.getPlayerInGame2().getNameProperty());
        player2Score.textProperty().bind(game.getPlayerInGame2().getScoreProperty().asString());
        currentPlayerName.setText(game.getCurrentPlayer().getNameProperty().getName());
        updatePanel();
    }

    public void returnToHome() {
        app.changePage("HomePage");
    }

    public void saveGame() {
        try {
            FileChooser fileChooser = new FileChooser();
            // Select a file
            fileChooser.setTitle("Save Game");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dark Chess Game", "*.dcg"));
            fileChooser.setInitialFileName("DarkChessGame.dcg");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showSaveDialog(app.getPrimaryStage());
            if (file != null)
                game.saveGame(file);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            String s = "Error saving game:";
            alert.setContentText(s + '\n' + e.toString());
            alert.showAndWait();
        }
    }

    public void askForDraw() {
        game.askForDraw();
    }

    public void surrender() {
        game.surrender(game.getCurrentPlayer());
    }

    public void withdraw() {
        game.goToPrevMove();
        gameBoardController.updateBoard();
    }

    public void toggleCheat() {
        if (isCheatMode) {
            isCheatMode = false;
            toggleCheatButton.setText("Enable Cheat");
            game.hideAllPieces();
            gameBoardController.updateBoard();
        } else {
            isCheatMode = true;
            toggleCheatButton.setText("Disable Cheat");
        }
        gameBoardController.setCheatMode(isCheatMode);
    }

    public void sendMessage() {
        // Pack the text in messageInput in a label, and append to messagesBox
        Label message = new Label(messageInput.getText());
        message.getStyleClass().add("message");
        messagesBox.getChildren().add(message);
    }

    public void updatePanel() {
        player1Name.getStyleClass().removeAll("textRed", "textBlack");
        player2Name.getStyleClass().removeAll("textRed", "textBlack");
        if (game.getPlayerInGame1().getSide() == Side.RED) {
            player1Name.getStyleClass().add("textRed");
            player2Name.getStyleClass().add("textBlack");
        } else {
            player1Name.getStyleClass().add("textBlack");
            player2Name.getStyleClass().add("textRed");
        }
        if (game.getCurrentPlayer() == game.getPlayerInGame1()) {
            currentPlayerName.setText(game.getPlayerInGame1().getNameProperty().getName());
            currentPlayerName.getStyleClass().removeAll("textRed", "textBlack");
            if (game.getPlayerInGame1().getSide() == Side.RED)
                currentPlayerName.getStyleClass().add("textRed");
            else
                currentPlayerName.getStyleClass().add("textBlack");
        } else {
            currentPlayerName.setText(game.getPlayerInGame2().getNameProperty().getName());
            currentPlayerName.getStyleClass().removeAll("textRed", "textBlack");
            if (game.getPlayerInGame2().getSide() == Side.RED)
                currentPlayerName.getStyleClass().add("textRed");
            else
                currentPlayerName.getStyleClass().add("textBlack");
        }
        if (game.getWinner() != null) {
            // Pop up a dialog showing the winner's name, with one button to return and another to save replay
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Game Over");
            alert.setHeaderText("Game Over");
            alert.setContentText(game.getWinner().getNameProperty().getName() + " wins!");
            ButtonType saveReplay = new ButtonType("Save Replay");
            ButtonType returnToHome = new ButtonType("Return to Home");
            alert.getButtonTypes().setAll(saveReplay, returnToHome);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == saveReplay)
                saveGame();
            else if (result.get() == returnToHome)
                returnToHome();
            }
        }
    }
}
