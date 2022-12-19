package io.github.prurite.darkchessfx.components;

import io.github.prurite.darkchessfx.DarkchessFXResourcesLoader;
import io.github.prurite.darkchessfx.game.PerformGame.Chess;
import io.github.prurite.darkchessfx.game.PerformGame.Piece;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.model.ChessSide;
import io.github.prurite.darkchessfx.model.ChessType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class DFXPiece extends StackPane {
    ChessType type;
    ChessSide side;

    public DFXPiece(Piece piece) {
        updatePiece(piece);
    }

    public DFXPiece(ChessSide side) {
        this(side, ChessType.COVERED);
    }

    public DFXPiece(ChessSide side, ChessType type) {
        this.type = type;
        this.side = side;
        initializeView();
    }

    public ChessType toDFXChessType(Chess type) {
        ChessType res;
        switch (type) {
            case General -> res = ChessType.GENERAL;
            case Advisor -> res = ChessType.ADVISOR;
            case Minister -> res = ChessType.ELEPHANT;
            case Horse -> res = ChessType.HORSE;
            case Chariot -> res = ChessType.CHARIOT;
            case Cannon -> res = ChessType.CANNON;
            case Soldier -> res = ChessType.SOLDIER;
            case Unknown -> res = ChessType.COVERED;
            // case Empty -> res = ChessType.EMPTY;
            default -> res = ChessType.EMPTY;
        }
        return res;
    }

    public boolean updatePiece(Piece piece) {
        boolean changed = false;
        ChessType newType = toDFXChessType(piece.getType());
        if (newType != type) {
            type = newType;
            changed = true;
        }
        ChessSide newSide = piece.getSide() == Side.RED ? ChessSide.RED : ChessSide.BLACK;
        if (newSide != side) {
            side = newSide;
            changed = true;
        }
        initializeView();
        return changed;
    }

    private void initializeView() {
        getStylesheets().clear();
        getChildren().clear();
        if (type == ChessType.EMPTY)
            return;
        getStylesheets().add(DarkchessFXResourcesLoader.loadURL("css/DFXPiece.css").toExternalForm());
        Circle back = new Circle(), ring = new Circle();
        back.radiusProperty().bind(maxWidthProperty().divide(2.2));
        back.getStyleClass().add("DFXPieceBack");
        ring.radiusProperty().bind(maxWidthProperty().divide(2.2).multiply(0.8));
        ring.getStyleClass().add("DFXPieceRing");
        if (type == ChessType.COVERED)
            ring.getStyleClass().add("DFXPieceRingCovered");
        else if (side == ChessSide.RED)
            ring.getStyleClass().add("DFXPieceRingRed");
        else if (side == ChessSide.BLACK)
            ring.getStyleClass().add("DFXPieceRingBlack");
        getChildren().addAll(back, ring);
        if (type != ChessType.COVERED) {
            Image image = new Image(DarkchessFXResourcesLoader.
                    loadStream("image/" + side.getShortName() + type.getShortName() + ".png"));
            ImageView iv = new ImageView(image);
            iv.setPreserveRatio(true);
            // iv.setFitWidth(10);
            // iv.setFitHeight(10);
            iv.fitWidthProperty().bind(maxWidthProperty().multiply(0.9));
            iv.fitHeightProperty().bind(maxHeightProperty().multiply(0.9));
            iv.setSmooth(true);
            getChildren().add(iv);
        }
    }

    public ChessType getType() {
        return type;
    }

    public ChessSide getSide() {
        return side;
    }
}
