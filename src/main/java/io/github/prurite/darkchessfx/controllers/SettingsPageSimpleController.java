package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsPageSimpleController implements Initializable, DFXSimpleController {
    @FXML private ComboBox<String> colorSchemeComboBox;
    private App app;
    public SettingsPageSimpleController() {
    }

    @Override
    public void setApp(App app) {
        this.app = app;
        colorSchemeComboBox.setValue(app.getColorScheme());
        colorSchemeComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            try {
                app.setColorScheme(colorSchemeComboBox.getItems().get((Integer) newValue));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize color scheme combo box
        colorSchemeComboBox.getItems().addAll("LTCatBlue", "LemonLime", "LuckyPurple");
        colorSchemeComboBox.getSelectionModel().select(0);
    }

    public void returnToHome() {
        app.changePage("HomePage");
    }
}
