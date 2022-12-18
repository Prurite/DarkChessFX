package io.github.prurite.darkchessfx.game.StrongAI;

import io.github.prurite.darkchessfx.game.PerformGame.Move;
import io.github.prurite.darkchessfx.game.PerformGame.Side;
import io.github.prurite.darkchessfx.game.PerformGame.State;

public interface TreeInterface {
    static int MCTS_TIMES = 200;
    public Move makeMove(State state, Side side);
}
