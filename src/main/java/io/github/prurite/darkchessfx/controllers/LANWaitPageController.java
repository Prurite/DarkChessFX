package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class LANWaitPageController implements Initializable {
    @FXML
    private VBox infoCard;
    @FXML private Label infoCardTitle;
    @FXML private Label infoCardText;
    @FXML private VBox readyCard;
    @FXML private Label readyCardTitle;
    @FXML private Label player1NameLabel;
    @FXML private Label player1ReadyLabel;
    @FXML private Label player2NameLabel;
    @FXML private Label player2ReadyLabel;
    private Game game;
    private App app;

    public LANWaitPageController (Game game, App app) {
        this.app = app;
        this.game = game;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
