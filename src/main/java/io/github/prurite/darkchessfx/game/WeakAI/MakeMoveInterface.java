package io.github.prurite.darkchessfx.game.WeakAI;

import io.github.prurite.darkchessfx.game.PerformGame.Chess;
import io.github.prurite.darkchessfx.game.PerformGame.Move;
import io.github.prurite.darkchessfx.game.PerformGame.Piece;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import javafx.util.Pair;

import java.util.ArrayList;

public interface MakeMoveInterface {
    Move makeMove(Piece[][] chessboard);
    Pair<ArrayList<Move>, ArrayList<Move>> getMoves(Piece[][] chessboard);

    // static methods in Interface must be implemented, so I only put them in comments:
    // static Move makeMove(Piece[][] chessboard, Side mySide);
    //static Pair<ArrayList<Move>, ArrayList<Move>> getMoves(Piece[][] chessboard, Side mySide) ;
}
