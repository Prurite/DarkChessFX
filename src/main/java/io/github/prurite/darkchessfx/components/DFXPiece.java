package io.github.prurite.darkchessfx.components;

import io.github.prurite.darkchessfx.DarkchessFXResourcesLoader;
import io.github.prurite.darkchessfx.model.ChessSide;
import io.github.prurite.darkchessfx.model.ChessType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class DFXPiece extends StackPane {
    ChessType type;
    ChessSide side;

    public DFXPiece(ChessSide side, ChessType type) {
        this.type = type;
        this.side = side;
        getStylesheets().add(DarkchessFXResourcesLoader.loadURL("css/DFXPiece.css").toExternalForm());
        Circle back = new Circle(), ring = new Circle();
        back.radiusProperty().bind(widthProperty().divide(2.2));
        back.getStyleClass().add("DFXPieceBack");
        ring.radiusProperty().bind(widthProperty().divide(2.2).multiply(0.8));
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

    public DFXPiece(ChessSide side) {
        this(side, ChessType.COVERED);
    }

}
