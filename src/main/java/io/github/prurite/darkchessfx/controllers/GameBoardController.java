package io.github.prurite.darkchessfx.controllers;

import io.github.prurite.darkchessfx.App;
import io.github.prurite.darkchessfx.components.DFXPiece;
import io.github.prurite.darkchessfx.game.PerformGame.*;
import io.github.prurite.darkchessfx.model.ChessSide;
import io.github.prurite.darkchessfx.model.ChessType;
import io.github.prurite.darkchessfx.model.Pos;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
    @FXML private BorderPane rootPane;
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
                DFXPiece piece = new DFXPiece(game.getPieceOnBoard(new Pos(finalI, finalJ)));
                piece.getStyleClass().add("chessBoardPiece");
                piece.setOnMouseClicked(event -> {
                    Pos p = new Pos(finalI, finalJ);
                    pieceOnClick(p);
                });
                pane.getChildren().add(piece);
                chessBoardCells[i][j] = pane;
            }
        }
    }

    private void pieceOnClick(Pos p) {
        if (isCheatMode) {
            game.revealPiece(p);
            gamePageController.updatePage();
            return;
        }
        if (status == BoardStatus.WAITING) {
            if (game.getPieceOnBoard(p).getType() != Chess.Unknown
                    && game.getPieceOnBoard(p).getSide() != game.getCurrentPlayer().getSide() )
                return;
            if (game.getPieceOnBoard(p).getType() == Chess.Empty)
                return;
            status = BoardStatus.SELECTED;
            selectedPos = p;
            chessBoardCells[p.getX()][p.getY()].getStyleClass().add("cellSelected");
            if (game.getPieceOnBoard(selectedPos).getType() != Chess.Unknown) {
                ArrayList<Pos> moves = game.getValidMoves(selectedPos);
                if (moves != null)
                    for (Pos move : moves) {
                        chessBoardCells[move.getX()][move.getY()].getStyleClass().add("cellValidMoves");
                    }
            }
        } else if (status == BoardStatus.SELECTED) {
            status = BoardStatus.WAITING;
            chessBoardCells[selectedPos.getX()][selectedPos.getY()].getStyleClass().remove("cellSelected");
            ArrayList<Pos> moves = game.getValidMoves(selectedPos);
            if (game.getPieceOnBoard(selectedPos).getType() == Chess.Unknown
                    && selectedPos.equals(p)) {
                game.performMove(game.getCurrentPlayer(), new Move(selectedPos, new Pos(-1, -1)));
                gamePageController.performMoveFinish();
            } else if (moves != null && moves.contains(p)) {
                game.performMove(game.getCurrentPlayer(), new Move(selectedPos, p));
                gamePageController.performMoveFinish();
            }
            if (moves != null)
                for (Pos move : moves)
                    chessBoardCells[move.getX()][move.getY()].getStyleClass().remove("cellValidMoves");
        }
    }

    public void updateBoard() {
        boolean changed = false;
        for (int i = 0; i < row; i++)
            for (int j = 0; j < col; j++) {
                DFXPiece piece = (DFXPiece) chessBoardCells[i][j].getChildren().get(0);
                changed |= piece.updatePiece(game.getPieceOnBoard(new Pos(i, j)));
            }
        updateCaptured();
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

    public void setStatus(BoardStatus status) {
        this.status = status;
    }
}