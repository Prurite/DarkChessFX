package io.github.prurite.darkchessfx.game.StrongAI;

import io.github.prurite.darkchessfx.game.PerformGame.*;
import io.github.prurite.darkchessfx.game.WeakAI.MakeMove;

import static java.lang.Math.min;

public class Tree0 implements TreeInterface{
    private Node root;
    private boolean myfirst;
    private int lastx, lasty;
    public Tree0() { myfirst = false; }
    private void init() {
        //root.setVisitCount(1);
        root.simulation();
    }
    public void Debug() {
        String s = "";
        root.Debug(0, s);
        MakeMove.debug(s);
    }
    public Move makeMove(State state, Side side) {
        if (root == null) {
            if(myfirst == true) {
                root = new Node(state, state.getBoard()[lastx][lasty].getSide(), null, 1, true, null);
                init();
            }
            else {
                Piece chess = null;
                for(int i=0; i<4; ++i) for(int j=0; j<8; ++j) if(state.getBoard()[i][j].getType() != Chess.Unknown) {
                    chess = state.getBoard()[i][j];
                }
                if(chess == null) {
                    myfirst = true;
                    lastx = min((int)(Math.random()*4), 3);
                    lasty = min((int)(Math.random()*8), 7);
                    return new Move(lastx, lasty, -1, -1);
                }
                else {
                    root = new Node(state, chess.getSide().getOpposite(), null, 1, true, null);
                    init();
                }
            }
        } else {
            Side tmp = root.getMySide();
            //MakeMove.debug("going to find child  -- " + tmp.toString() + state.debugBoard());
            root = root.findChild(state, 0);
            root.setParent(null);
            init();
        }


        int N = 1;
        for(int ii = 0; ii< MCTS_TIMES; ++ii) {
            Node node = root.chooseChild(N);
            if(node == null) continue;
            node.workOn();
            ++N;
        }

        double mx = -114514;
        Node res = null;
        for(Node node: root.getChildren()) if(node.getVisitCount() > 0) {
            double rate = -node.getWinCount() / node.getVisitCount();
            if(rate > mx || res == null) {
                mx = rate;
                res = node;
            }
        }
        MakeMove.debug(mx + " " + res.getMove().toString());
        //!!

        if(res == null) MakeMove.debug("res == null");
        root = res;
        assert res != null;
        return res.getMove();

//         return new Move (0, 0, -1, -1);

    }
}
