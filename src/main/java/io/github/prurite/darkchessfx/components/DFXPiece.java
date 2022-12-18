package io.github.prurite.darkchessfx.components;

import io.github.prurite.darkchessfx.DarkchessFXResourcesLoader;
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

    public void updatePiece(Piece piece) {
        side = piece.getSide() == Side.RED ? ChessSide.RED : ChessSide.BLACK;
        switch (piece.getType()) {
            case General -> type = ChessType.GENERAL;
            case Advisor -> type = ChessType.ADVISOR;
            case Minister -> type = ChessType.ELEPHANT;
            case Horse -> type = ChessType.HORSE;
            case Chariot -> type = ChessType.CHARIOT;
            case Cannon -> type = ChessType.CANNON;
            case Soldier -> type = ChessType.SOLDIER;
            case Unknown -> type = ChessType.COVERED;
            case Empty -> type = ChessType.EMPTY;
        }
        initializeView();
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
