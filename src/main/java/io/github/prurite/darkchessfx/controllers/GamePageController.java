package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable, DFXController {
    @FXML
    private HBox rootPane;
    @FXML
    private Label title;
    private App app;

    private boolean gameBoardLoaded = false;

    @Override
    public void setApp(App app) {
        this.app = app;
        if (!gameBoardLoaded) {
            Pane gameBoard = app.loadFXML("GameBoard");
            rootPane.getChildren().add(gameBoard);
            gameBoardLoaded = true;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void returnToHome() {
        app.changePage("HomePage");
    }
}
