package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.PerformGame.Move;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.game.PerformGame.State;
import io.github.prurite.darkchessfx.game.StrongestAI.Tree;
import io.github.prurite.darkchessfx.game.WeakAI.MakeMove;

import java.util.ArrayList;


public class AIPlayer {
    int difficulty;
    Tree tree;
    public AIPlayer(int difficulty) {
        this.difficulty = difficulty;
        if(difficulty == 2) {
            tree = new Tree();
        }
    }
    public Move makeMove(State state, Side side) {
        if(difficulty == 0) {
            ArrayList<Move> moves = MakeMove.getMoves(state.getBoard(), side).getValue();
            return moves.get((int)(Math.random()*moves.size()));
        }
        else if(difficulty == 1) {
            return MakeMove.makeMove(state.getBoard(), side);
        }
        else {
            return tree.makeMove(state, side);
        }
    }
}
