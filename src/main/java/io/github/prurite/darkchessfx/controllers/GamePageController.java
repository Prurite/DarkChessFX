package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GamePageController implements Initializable, DFXController {
    @FXML
    private Label title;
    private App app;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void returnToHome() throws IOException {
        app.changePage("HomePage");
    }
}
