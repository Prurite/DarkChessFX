package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.PerformGame.Move;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.game.PerformGame.State;
import io.github.prurite.darkchessfx.game.StrongestAI.Tree;
import io.github.prurite.darkchessfx.game.WeakAI.MakeMove;
import io.github.prurite.darkchessfx.game.StrongAI.Tree0;

import java.util.ArrayList;


public class AIPlayer {
    int difficulty;
    Tree tree;
    Tree0 tree0;
    public AIPlayer(int difficulty) {
        this.difficulty = difficulty;
        if(difficulty == 1) {
            tree0 = new Tree0();
        }
        else if(difficulty == 2) {
            tree = new Tree();
        }
    }
    public Move makeMove(State state, Side side) {
        if(difficulty == 0) {
            return MakeMove.makeMove(state.getBoard(), side);
        }
        else if(difficulty == 1) {
            return tree0.makeMove(state, side);
        }
        else if(difficulty == 2) {
            return tree.makeMove(state, side);
        }
        else {
            return tree.makeMove(state, side);
        }
    }
}
