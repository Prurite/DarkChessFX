package io.github.prurite.darkchessfx.game.StrongAI;

import io.github.prurite.darkchessfx.game.PerformGame.EatenPieces;
import io.github.prurite.darkchessfx.game.PerformGame.Piece;

public class State {
    Piece[][] board;
    EatenPieces eatenPieces;
    public State(Piece[][] board, EatenPieces eatenPieces) {
        this.board = board;
        this.eatenPieces = eatenPieces;
    }
}
