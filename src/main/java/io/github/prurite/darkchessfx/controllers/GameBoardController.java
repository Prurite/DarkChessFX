package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.components.DFXPiece;
import io.github.prurite.darkchessfx.model.ChessSide;
import io.github.prurite.darkchessfx.model.ChessType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardController implements DFXController, Initializable {
    @FXML
    private FlowPane chessBoardCellPane;
    @FXML
    private VBox redCapturedPane;
    @FXML
    private StackPane chessBoardBack;
    @FXML
    private VBox blackCapturedPane;
    private App app;
    private final int row = 8, col = 4;
    private Pane[][] chessBoardCells = new Pane[row][col];

    @Override
    public void setApp(App app) {
        this.app = app;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                StackPane pane = new StackPane();
                pane.getStyleClass().add("chessBoardCell");
                chessBoardCellPane.getChildren().add(pane);
                long side = Math.round(Math.random() * 1);
                long type = Math.round(Math.random() * 7);
                DFXPiece piece = new DFXPiece(ChessSide.values()[(int) side], ChessType.values()[(int) type]);
                piece.getStyleClass().add("chessBoardPiece");
                pane.getChildren().add(piece);
                chessBoardCells[i][j] = pane;
            } // TODO
        }
    }
}
