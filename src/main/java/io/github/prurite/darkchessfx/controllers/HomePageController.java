package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
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

    public void toNewGame() {
        app.changePage("StartGamePage");
    }

    public void loadGame() {
        try {
            Game game = new Game();
            FileChooser fileChooser = new FileChooser();
            // Select a file
            fileChooser.setTitle("Load Game");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dark Chess Game", "*.dcg"));
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            File file = fileChooser.showOpenDialog(app.getPrimaryStage());
            if (file != null) {
                game.loadGame(file);
                app.loadGameFinish(game);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            String s = "Error loading game:";
            alert.setContentText(s + '\n' + e);
            alert.showAndWait();
        }
    }
}
