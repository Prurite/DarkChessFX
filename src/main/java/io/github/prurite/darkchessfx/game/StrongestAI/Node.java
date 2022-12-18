package io.github.prurite.darkchessfx.game.StrongestAI;

import io.github.prurite.darkchessfx.game.PerformGame.*;
import io.github.prurite.darkchessfx.game.WeakAI.MakeMove;

import java.util.ArrayList;

public class Node {
    // parameters
    static double EPS = 1e-6;
    static double BIAS = 0.75;
    static double COE_D = 0.01;
    static double COE_S = 0.0001;
    static double PGL = 150;
    static double MAX_UTC = 40;
    static int MAX_SIMULATION_LENGTH = 300;
    static int MAX_INEFFECTIVE_STEP = 40;


    private boolean isDeterminant;
    private double probability;
    private double visitCount;
    private double winCount;
    private ArrayList<Node> children;
    private Node parent;
    private State state;
    private Side mySide; // side of next move
    private Move move;

    // calculate wi = P(its parent wins)
    public double getVal(int N) {
        if(visitCount == 0) return Math.random() * MAX_UTC;
        if(isDeterminant == false) {
            double val = 0;
            for(Node child : children) {
                val += child.getVal(N) * child.getProbability();
            }
            return val;
        }
        else {
            return - winCount / visitCount + Math.sqrt(Math.log(N) / visitCount) * BIAS;
        }
    }
    public void Debug(int d, String s) {
        for(int i=0; i<d; ++i) s += " ";
        s += winCount + "\n";
        for(Node child: children) child.Debug(d+1, s);
    }
    public Node(State state, Side mySide, Node parent, double probability, boolean isDeterminant, Move move) {
        this.state = new State(state);
        this.mySide = mySide;
        this.parent = parent;
        this.visitCount = 0;
        this.winCount = 0;
        this.children = new ArrayList<>();
        this.probability = probability;
        this.isDeterminant = isDeterminant;
        this.move = new Move(move);
    }
    public Node findChild(State state, int depth) {
        if(depth >= 5) return null;
        //!!
        if(state.equals(this.state)) {
            //MakeMove.debug("depth = " + depth);
            return this;
        }

        if(!state.getEatenPieces().includes(this.state.getEatenPieces())) return null;
        if(!state.getRevealedPieces().includes(this.state.getRevealedPieces())) return null;
//        if(state.getRevealedPieces().equals(this.state.getRevealedPieces())) return this;
//        if(state.getEatenPieces().equals(this.state.getEatenPieces())) return this;
        if(children.size() == 0) generateChildren();
        for(int i=0; i<children.size(); ++i) {
            Node child = children.get(i);
            Node tmp = child.findChild(state, depth + 1);
            //MakeMove.debug(child.getState().getRevealedPieces().toString());
            if(tmp != null) return tmp;
            else {
                children.set(i, null);
            }
        }
        return null;
    }

    public void generateChildren() {
        if(children.size() != 0 || state.endGame()) return;
        ArrayList<Move> moves = MakeMove.getMoves(state.getBoard(), mySide).getValue();
//        if(moves.size() == 0) {
//            MakeMove.debug("no moves");
//            return;
//        }
        for(Move move : moves) {
            if(move.getNewx() != -1 && state.getBoard()[move.getNewx()][move.getNewy()].getType() != Chess.Unknown) {
                State tmp = new State(state);
                tmp.simulationUPD(move, null);
                Node son = new Node(tmp, mySide.getOpposite(), this, 1.0, true, move);
                children.add(son);
                //MakeMove.debug("FIND MOVE: \n" + state.debugBoard() + "\n" + move.toString() + "\n" + tmp.debugBoard());
                //!!!!
            }
            else {
                // for each kind of possible faced down piece, add a child
                Node u = new Node(new State(state), mySide, this, 1.0, false, move);
                children.add(u);

                int tot = 32 - state.getRevealedPieces().getTot();
                int checkTot = 0;
                for(Side s : Side.values())
                    for(Chess c : Chess.values()) if(c != Chess.Unknown && c != Chess.Empty) {
                        int count = c.getCount() - state.getRevealedPieces().eaten(new Piece(c, s));
                        if(count == 0) continue; checkTot += count;
                        double prob = 1.0 * count / tot;

                        State tmp = new State(state);
                        tmp.simulationUPD(move, new Piece(c, s));
                        Node son = new Node(tmp, mySide.getOpposite(), this, prob, true, null);
                        u.getChildren().add(son);
                    }
                if(checkTot != tot) MakeMove.debug("tot = " + tot + " checkTot = " + checkTot);
            }
        }
        //MakeMove.debug("children size = " + children.size());
    }

    public void updateCurrent() {
//        visitCount += v.getVisitCount() * sgn;
//        winCount += v.getWinCount() * sgn;
        visitCount = winCount = 0;
        for(Node v : children) {
            visitCount += v.getVisitCount() * v.getProbability();
            winCount += v.getWinCount() * v.getProbability() * (isDeterminant ? -1 : 1);
        }
    }
    public void update() {
        updateCurrent();
        if(parent != null) parent.update();
    }

    public Node chooseChild(int N) {
        if(children.size() == 0 || Math.abs(children.get(0).getVisitCount()) < EPS) return this;
        if(isDeterminant == false) {
            double x = Math.random();
            double c = 0;
            for(Node v : children) {
                c += v.getProbability();
                if(x <= c) return v.chooseChild(N);
            }
            return children.get(children.size() - 1).chooseChild(N);
        }
        else {
            Node res = null;
            double max = 0;
            for(Node v : children) {
                double cur = v.getVal(N);
                if(res == null || cur > max || (cur >= max-EPS && Math.random() < 0.1)) {
                    res = v;
                    max = cur;
                }
            }
            return res.chooseChild(N);
        }
    }
    public void workOn() {
        generateChildren();
        for(Node v : children) {
            v.simulation();
        }
        update();
    }
    public void simulation() {
        if(isDeterminant == false) {
            for(Node v : children) v.simulation();
            this.updateCurrent();
            return;
        }

        State state = new State(this.state);
        Side side = mySide;

        double res = 0;
        int lastmove = 0;

        if(state.endGame()) {
            winCount = (state.getWinner() == mySide.ordinal() ? 1 : -1) * 3;
            visitCount = 1;
            return;
        }

        for(int curmove = 1; curmove - lastmove <= MAX_INEFFECTIVE_STEP && curmove <= MAX_SIMULATION_LENGTH; ++curmove) {
            if(state.endGame()) {
                res = (1 + COE_D * (PGL - curmove) ) * (mySide.ordinal() == state.getWinner() ? 1 : -1);
                break;
            }

            Move move = MakeMove.makeMove(state.getBoard(), side);
            if(move == null) break;

            if(move.getNewx() == -1 || state.getBoard()[move.getNewx()][move.getNewy()].getType() != Chess.Empty) {
                lastmove = curmove;
            }

            if(move.getNewx() != -1 && state.getBoard()[move.getNewx()][move.getNewy()].getType() != Chess.Unknown) {
                state.simulationUPD(move, null);
            } else {
                double p = Math.random(), sum = 0;
                int tot = 32 - state.getRevealedPieces().getTot();
                boolean flg = false;
                for(Side s : Side.values()) {
                    for(Chess c : Chess.values()) if(c != Chess.Unknown && c != Chess.Empty) {
                        int count = c.getCount() - state.getRevealedPieces().eaten(new Piece(c, s));
                        if(count == 0) continue;
                        double prob = 1.0 * count / tot;
                        sum += prob;
                        if(sum >= p || (s.ordinal() == Side.values().length - 1 && c.ordinal() == Chess.values().length - 1)) {
                            state.simulationUPD(move, new Piece(c, s));
                            flg = true;
                            break;
                        }
                    }
                    if(flg) break;
                }

            }
            side = side.getOpposite();
        }
        winCount = res /*+ COE_S * state.getScore(mySide.ordinal())*/;
        visitCount = 1;
    }

    public Move getMove() {
        return move;
    }

    public boolean isDeterminant() {
        return isDeterminant;
    }

    public void setDeterminant(boolean determinant) {
        isDeterminant = determinant;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public double getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(double visitCount) {
        this.visitCount = visitCount;
    }

    public double getWinCount() {
        return winCount;
    }

    public void setWinCount(double winCount) {
        this.winCount = winCount;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Node> children) {
        this.children = children;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Side getMySide() {
        return mySide;
    }

    public void setMySide(Side mySide) {
        this.mySide = mySide;
    }
}