package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.components.DFXPiece;
import io.github.prurite.darkchessfx.game.PerformGame.Game;
import io.github.prurite.darkchessfx.game.PerformGame.Move;
import io.github.prurite.darkchessfx.game.PerformGame.Piece;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.model.Pos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
    @FXML private FlowPane chessBoardCellPane;
    @FXML private FlowPane redCapturedPane;
    @FXML private FlowPane blackCapturedPane;
    @FXML private StackPane chessBoardBack;
    final private Game game;
    final private App app;
    private final int row = 8, col = 4;
    final private Pane[][] chessBoardCells;
    private boolean isCheatMode;
    GamePageController gamePageController;
    enum BoardStatus {
        LOCKED, // Not current player's turn
        WAITING, // Waiting for move
        SELECTED // Selected piece to move
    }
    BoardStatus status;
    Pos selectedPos;

    public GameBoardController(Game game, App app) {
        this.game = game;
        this.app = app;
        status = BoardStatus.WAITING;
        chessBoardCells = new Pane[row][col];
    }

    public void setGamePageController(GamePageController gamePageController) {
        this.gamePageController = gamePageController;
    }

    public void setCheatMode(boolean cheatMode) {
        isCheatMode = cheatMode;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                int finalI = i, finalJ = j;
                StackPane pane = new StackPane();
                pane.getStyleClass().add("chessBoardCell");
                chessBoardCellPane.getChildren().add(pane);
                DFXPiece piece = new DFXPiece(game.getChessBoard()[i][j]);
                piece.getStyleClass().add("chessBoardPiece");
                piece.setOnMouseClicked(event -> {
                    if (status == BoardStatus.WAITING) {
                        selectedPos = new Pos(finalI, finalJ);
                        status = BoardStatus.SELECTED;
                        chessBoardCells[finalI][finalJ].getStyleClass().add("cellSelected");
                        ArrayList<Pos> moves = game.getValidMoves(selectedPos);
                        for (Pos move : moves)
                            chessBoardCells[move.x][move.y].getStyleClass().add("cellValidMoves");
                    } else if (status == BoardStatus.SELECTED) {
                        // if the clicked pos is in valid moves list, move the piece;
                        // otherwise, cancel the selection.
                        ArrayList<Pos> moves = game.getValidMoves(selectedPos);
                        if (moves.contains(new Pos(finalI, finalJ))) {
                            game.performMove(game.getCurrentPlayer(), new Move(selectedPos, new Pos(finalI, finalJ)));
                            updateBoard();
                        }
                        else {
                            chessBoardCells[selectedPos.x][selectedPos.y].getStyleClass().remove("cellSelected");
                            for (Pos move : moves)
                                chessBoardCells[move.x][move.y].getStyleClass().remove("cellValidMoves");
                        }
                    }
                });
                piece.setOnMousePressed(e -> {
                    if (isCheatMode) {
                        game.revealPiece(new Pair<>(finalI, finalJ));
                        updateBoard();
                    }
                });
                piece.setOnMouseReleased(e -> {
                    if (isCheatMode) {
                        game.hidePiece(new Pair(finalI, finalJ));
                        updateBoard();
                    }
                });
                pane.getChildren().add(piece);
                chessBoardCells[i][j] = pane;
            }
        }
    }

    public void updateBoard() {
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                DFXPiece piece = (DFXPiece) chessBoardCells[i][j].getChildren().get(0);
                piece.updatePiece(game.getChessBoard()[i][j]);
            }
        updateCaptured();
        gamePageController.updatePage();
    }

    public void updateCaptured() {
        redCapturedPane.getChildren().clear();
        blackCapturedPane.getChildren().clear();
        for (Piece p : game.getCaptured(Side.RED)) {
            DFXPiece piece = new DFXPiece(p);
            piece.getStyleClass().add("capturedPiece");
            blackCapturedPane.getChildren().add(piece);
        }
        for (Piece p : game.getCaptured(Side.BLACK)) {
            DFXPiece piece = new DFXPiece(p);
            piece.getStyleClass().add("capturedPiece");
            redCapturedPane.getChildren().add(piece);
        }
    }
}