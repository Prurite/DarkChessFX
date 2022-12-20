package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.components.DFXSlider;
import io.github.prurite.darkchessfx.components.DFXSwitch;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class StartGamePageSimpleController implements Initializable, DFXSimpleController {
    @FXML private RadioButton aiNoButton;
    @FXML private RadioButton aiYesButton;
    @FXML private RadioButton lanNoButton;
    @FXML private RadioButton lanYesButton;
    @FXML private Label title;
    @FXML private DFXSlider turnTimeDFXSlider;
    @FXML private DFXSlider totalTimeDFXSlider;
    @FXML private DFXSlider scoreDFXSlider;
    @FXML private Label turnTimeLabel;
    @FXML private Label totalTimeLabel;
    @FXML private Label scoreLabel;
    @FXML private DFXSwitch cheatToggle;
    @FXML private DFXSwitch withdrawToggle;
    @FXML private Label unScoredLabel;
    @FXML private ComboBox<String> difficultyComboBox;
    @FXML private TextField portTextField;
    @FXML private TextField passwordTextField;
    @FXML private TextField player1TextField;
    @FXML private TextField player2TextField;
    private ToggleGroup aiToggleGroup;
    private App app;
    private Game game;
    private GameConfig gameConfig;

    public StartGamePageSimpleController() {
        game = new Game();
        gameConfig = game.getGameConfig();
    }

    public StartGamePageSimpleController(Game loadedGame, App app) {
        game = loadedGame;
        gameConfig = game.getGameConfig();
        this.app = app;
    }

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    public void setGameConfig(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (gameConfig == null) {
            title.setText("Start Game");
            gameConfig = new GameConfig();
        } else
            title.setText("Load Game");
        aiToggleGroup = new ToggleGroup();
        aiNoButton.setToggleGroup(aiToggleGroup);
        aiYesButton.setToggleGroup(aiToggleGroup);

        Slider scoreSlider = scoreDFXSlider.getSlider();
        scoreSlider.setMin(10);
        scoreSlider.setMax(100);
        scoreSlider.setMajorTickUnit(20);
        scoreSlider.setMinorTickCount(3);
        scoreSlider.setSnapToTicks(true);
        scoreSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.minimumScore = newValue.intValue();
            scoreLabel.setText(gameConfig.minimumScore + "");
        });

        cheatToggle.getState().addListener((observable, oldValue, newValue) -> {
            gameConfig.allowCheat = newValue;
            if (gameConfig.allowCheat || gameConfig.allowWithdraw)
                unScoredLabel.setVisible(true);
            else
                unScoredLabel.setVisible(false);
        });

        withdrawToggle.getState().addListener((observable, oldValue, newValue) -> {
            gameConfig.allowWithdraw = newValue;
            if (gameConfig.allowCheat || gameConfig.allowWithdraw)
                unScoredLabel.setVisible(true);
            else
                unScoredLabel.setVisible(false);
        });

        aiToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == aiYesButton) {
                difficultyComboBox.setDisable(false);
                difficultyComboBox.getSelectionModel().select(0);
                gameConfig.aiDifficulty = difficultyComboBox.getSelectionModel().getSelectedIndex();
                player2TextField.setText("AI");
                player2TextField.setDisable(true);
                scoreSlider.setValue(60);
                scoreSlider.setDisable(true);
                withdrawToggle.setDisable(true);
            } else {
                difficultyComboBox.setDisable(true);
                gameConfig.aiDifficulty = -1;
                player2TextField.setText("");
                player2TextField.setDisable(false);
                scoreSlider.setDisable(false);
                withdrawToggle.setDisable(false);
            }
        });

        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.aiDifficulty = newValue.intValue();
        });

        player1TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.player1 = newValue;
        });
        player1TextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (player1TextField.getText().equals(""))
                gameConfig.player1 = gameConfig.defaultPlayer1;
        });
        player1TextField.setPromptText(gameConfig.defaultPlayer1);
        player2TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.player2 = newValue;
        });
        player2TextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (player2TextField.getText().equals(""))
                gameConfig.player2 = gameConfig.defaultPlayer2;
        });
        player2TextField.setPromptText(gameConfig.defaultPlayer2);

        updateFromGameConfig();
    }
    private void updateFromGameConfig()
    {
        scoreDFXSlider.getSlider().setValue(gameConfig.minimumScore);
        cheatToggle.getState().set(gameConfig.allowCheat);
        withdrawToggle.getState().set(gameConfig.allowWithdraw);
        if (gameConfig.allowCheat || gameConfig.allowWithdraw)
            unScoredLabel.setVisible(true);
        else
            unScoredLabel.setVisible(false);
        if (!gameConfig.player1.equals(gameConfig.defaultPlayer1))
            player1TextField.setText(gameConfig.player1);
        if (!gameConfig.player2.equals(gameConfig.defaultPlayer2))
            player2TextField.setText(gameConfig.player2);
        if (gameConfig.aiDifficulty >= 0) {
            aiToggleGroup.selectToggle(aiYesButton);
            difficultyComboBox.getSelectionModel().select(gameConfig.aiDifficulty);
            difficultyComboBox.setDisable(false);
        }
        else {
            aiToggleGroup.selectToggle(aiNoButton);
            difficultyComboBox.setDisable(true);
        }
    }

    public void returnToHome() {
        app.changePage("HomePage");
    }

    public void startGame() {
        if (gameConfig.player1.equals(""))
            gameConfig.player1 = gameConfig.defaultPlayer1;
        if (gameConfig.player2.equals(""))
            gameConfig.player2 = gameConfig.defaultPlayer2;
        if (gameConfig.aiDifficulty != -1)
            gameConfig.allowWithdraw = false;
        app.startGameFinish(game);
    }
}
