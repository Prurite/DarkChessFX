package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, DFXController {
    private App app;
    @FXML private VBox homeButtons;
    @FXML private VBox selectModeButtons;

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectModeButtons.managedProperty().bind(selectModeButtons.visibleProperty());
        homeButtons.managedProperty().bind(homeButtons.visibleProperty());
        selectModeButtons.setVisible(false);
    }

    public void selectMode() {
        homeButtons.setVisible(false);
        selectModeButtons.setVisible(true);
    }

    public void returnToHome() {
        homeButtons.setVisible(true);
        selectModeButtons.setVisible(false);
    }

    public void toNewGame() throws IOException {
        app.changePage("StartGamePage");
    }
}
