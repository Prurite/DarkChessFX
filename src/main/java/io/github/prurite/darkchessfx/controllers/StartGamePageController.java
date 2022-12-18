package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.components.DFXSlider;
import io.github.prurite.darkchessfx.components.DFXSwitch;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartGamePageController implements Initializable, DFXController {
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
    private App app;
    private Game game;
    private GameConfig gameConfig;

    public StartGamePageController() {
        game = new Game();
        gameConfig = game.getGameConfig();
    }

    public StartGamePageController(Game loadedGame, App app) {
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
        if (gameConfig == null)
            gameConfig = new GameConfig();
        ToggleGroup AIGroup = new ToggleGroup(), LANGroup = new ToggleGroup();
        aiNoButton.setToggleGroup(AIGroup);
        aiYesButton.setToggleGroup(AIGroup);
        lanNoButton.setToggleGroup(LANGroup);
        lanYesButton.setToggleGroup(LANGroup);

        Slider turnTimeSlider = turnTimeDFXSlider.getSlider();
        turnTimeSlider.setMin(0);
        turnTimeSlider.setMax(300);
        turnTimeSlider.setMajorTickUnit(60);
        turnTimeSlider.setMinorTickCount(5);
        turnTimeSlider.setSnapToTicks(true);
        turnTimeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.maxTurnTime = newValue.intValue();
            if (gameConfig.maxTurnTime > 0) {
                turnTimeLabel.setText(gameConfig.maxTurnTime + "s");
                turnTimeLabel.getStyleClass().remove("buttonLabel");
                turnTimeLabel.getStyleClass().add("generalLabel");
            } else {
                turnTimeLabel.setText("Unlimited");
                turnTimeLabel.getStyleClass().remove("generalLabel");
                turnTimeLabel.getStyleClass().add("buttonLabel");
            }
        });
        turnTimeSlider.setValue(gameConfig.maxTurnTime);
        Slider totalTimeSlider = totalTimeDFXSlider.getSlider();
        totalTimeSlider.setMin(0);
        totalTimeSlider.setMax(3600);
        totalTimeSlider.setMajorTickUnit(600);
        totalTimeSlider.setMinorTickCount(9);
        totalTimeSlider.setSnapToTicks(true);
        totalTimeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.maxTotalTime = newValue.intValue();
            if (gameConfig.maxTotalTime > 0) {
                totalTimeLabel.setText(gameConfig.maxTotalTime / 60 + "min");
                totalTimeLabel.getStyleClass().remove("buttonLabel");
                totalTimeLabel.getStyleClass().add("generalLabel");
            } else {
                totalTimeLabel.setText("Unlimited");
                totalTimeLabel.getStyleClass().remove("generalLabel");
                totalTimeLabel.getStyleClass().add("buttonLabel");
            }
        });
        totalTimeSlider.setValue(gameConfig.maxTotalTime);
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
        scoreSlider.setValue(gameConfig.minimumScore);

        cheatToggle.getState().addListener((observable, oldValue, newValue) -> {
            gameConfig.allowCheat = newValue;
            if (gameConfig.allowCheat || gameConfig.allowWithdraw)
                unScoredLabel.setVisible(true);
            else
                unScoredLabel.setVisible(false);
        });
        cheatToggle.getState().set(gameConfig.allowCheat);
        withdrawToggle.getState().addListener((observable, oldValue, newValue) -> {
            gameConfig.allowWithdraw = newValue;
            if (gameConfig.allowCheat || gameConfig.allowWithdraw)
                unScoredLabel.setVisible(true);
            else
                unScoredLabel.setVisible(false);
        });
        withdrawToggle.getState().set(gameConfig.allowWithdraw);
        if (gameConfig.allowCheat || gameConfig.allowWithdraw)
            unScoredLabel.setVisible(true);
        else
            unScoredLabel.setVisible(false);

        AIGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == aiYesButton) {
                difficultyComboBox.setDisable(false);
                gameConfig.aiDifficulty = difficultyComboBox.getSelectionModel().getSelectedIndex() + 1;
                player2TextField.setDisable(true);
                scoreSlider.setValue(60);
                scoreSlider.setDisable(true);
            } else {
                difficultyComboBox.setDisable(true);
                gameConfig.aiDifficulty = 0;
                player2TextField.setDisable(false);
                scoreSlider.setDisable(false);
            }
        });
        if (gameConfig.aiDifficulty > 0)
            AIGroup.selectToggle(aiYesButton);
        else
            AIGroup.selectToggle(aiNoButton);

        difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
        difficultyComboBox.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.aiDifficulty = newValue.getSelectedIndex() + 1;
        });

        LANGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == lanYesButton) {
                portTextField.setDisable(false);
                passwordTextField.setDisable(false);
                player2TextField.setDisable(true);
                portTextField.setText("");
                gameConfig.lanPort = gameConfig.defaultLanPort;
            } else {
                portTextField.setDisable(true);
                passwordTextField.setDisable(true);
                player2TextField.setDisable(false);
                gameConfig.lanPort = 0;
            }
        });
        if (gameConfig.lanPort > 0)
            LANGroup.selectToggle(lanYesButton);
        else
            LANGroup.selectToggle(lanNoButton);

        portTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*"))
                portTextField.setText(oldValue);
        });
        portTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            String text = portTextField.getText();
            if (!text.equals("") && text.matches("\\d*")
                    && Integer.parseInt(text) >= 1024 && Integer.parseInt(text) <= 65535)
                gameConfig.lanPort = Integer.parseInt(text);
            else {
                portTextField.setText("");
                gameConfig.lanPort = gameConfig.defaultLanPort;
            }
        });
        portTextField.setPromptText(gameConfig.defaultLanPort + "");

        passwordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.lanPassword = newValue;
        });

        player1TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.player1 = newValue;
        });
        player1TextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (player1TextField.getText().equals(""))
                gameConfig.player1 = gameConfig.defaultPlayer1;
        });
        player1TextField.setPromptText(gameConfig.defaultPlayer1);
        if (!gameConfig.player1.equals(gameConfig.defaultPlayer1))
            player1TextField.setText(gameConfig.player1);
        player2TextField.textProperty().addListener((observable, oldValue, newValue) -> {
            gameConfig.player2 = newValue;
        });
        player2TextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (player2TextField.getText().equals(""))
                gameConfig.player2 = gameConfig.defaultPlayer2;
        });
        player2TextField.setPromptText(gameConfig.defaultPlayer2);
        if (!gameConfig.player2.equals(gameConfig.defaultPlayer2))
            player2TextField.setText(gameConfig.player2);
    }

    public void returnToHome() throws IOException {
        app.changePage("HomePage");
    }

    public void startGame() throws IOException {
        app.startGame(game);
    }
}
