package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.model.Player;
import io.github.prurite.darkchessfx.model.PlayerInfoProcessor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StatisticsPageSimpleController implements Initializable, DFXSimpleController {
    @FXML private TableView<Player> statisticsTable;
    private App app;
    private PlayerInfoProcessor playerList;
    public StatisticsPageSimpleController() {
    }

    @Override
    public void setApp(App app) {
        this.app = app;
        playerList = app.getPlayerList();
        File f = new File("playerInfo.txt");
        try {
            f.createNewFile();
            playerList.readFromFile(f);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error reading player info file");
            alert.setContentText(e.toString());
            alert.showAndWait();
            app.changePage("HomePage");
        }
        ObservableList<Player> playerObservableList = FXCollections.observableArrayList(playerList.getPlayers());
        TableColumn nameCol = new TableColumn("Player Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        TableColumn gameCountCol = new TableColumn("Game Count");
        gameCountCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("gameCount"));
        TableColumn scoredGameCountCol = new TableColumn("Scored Game Count");
        scoredGameCountCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("scoredGameCount"));
        TableColumn winCountCol = new TableColumn("Win Count");
        winCountCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("winCount"));
        statisticsTable.getColumns().addAll(nameCol, gameCountCol, scoredGameCountCol, winCountCol);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Open player info file
    }

    public void returnToHome() {
        app.changePage("HomePage");
    }
}
