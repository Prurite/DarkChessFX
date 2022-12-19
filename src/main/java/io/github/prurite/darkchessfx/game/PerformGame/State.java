package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.LoadingExceptions.InvalidChessType;
import io.github.prurite.darkchessfx.game.LoadingExceptions.WrongChessBoardSize;
import javafx.beans.property.SimpleIntegerProperty;

public class State {
    Piece[][] board;
    EatenPieces eatenPieces;
    EatenPieces revealedPieces;
    int[] score;
    public State(Piece[][] board, EatenPieces eatenPieces, EatenPieces revealedPieces) {
        this.board = new Piece[4][8]; for(int i=0; i<4; ++i) for(int j=0; j<8; ++j) this.board[i][j] = new Piece(board[i][j]);
        this.eatenPieces = new EatenPieces(eatenPieces);
        this.revealedPieces = new EatenPieces(revealedPieces);
        score = new int[2];
        this.score[0] = this.score[1] = 0;
        for(Side s : Side.values()) {
            for(Chess c : Chess.values()) {
                if(c == Chess.Empty || c == Chess.Unknown) continue;
                score[1 - s.ordinal()] += c.getScore() * eatenPieces.eaten(new Piece(c, s));
            }
        }
    }
    public State() {
        board = new Piece[4][8];
        eatenPieces = new EatenPieces();
        revealedPieces = new EatenPieces();
        score = new int[2];
    }
    public State(State state) {
        board = new Piece[4][8];
        for(int i=0; i<4; ++i) for(int j=0; j<8; ++j) board[i][j] = new Piece(state.getBoard()[i][j]);
        eatenPieces = new EatenPieces(state.getEatenPieces());
        revealedPieces = new EatenPieces(state.getRevealedPieces());
        score = new int[2];
        score[0] = state.getScore()[0];
        score[1] = state.getScore()[1];
    }
    public boolean endGame() {
        return score[0] >= 60 || score[1] >= 60;
    }
    public int getWinner() {
        return score[1] >= 60 ? 1 : 0;
    }

    public int[] getScore() {
        return score;
    }
    public int getScore(int u) { return score[u] - score[1 - u]; }
    public int getScoreDiff() {
        return Math.abs(score[0] - score[1]);
    }

    public void simulationUPD(Move move, Piece chess) {
        if(move.getNewx() != -1 && board[move.getNewx()][move.getNewy()].getType() != Chess.Unknown) {
            Piece piece = board[move.getNewx()][move.getNewy()];
            if(piece.getType() != Chess.Empty) {
                score[1 - piece.getSide().ordinal()] += piece.getType().getScore();
                eatenPieces.eatPiece(piece);
            }
            board[move.getNewx()][move.getNewy()] = new Piece(board[move.getCurx()][move.getCury()]);
            board[move.getCurx()][move.getCury()] = new Piece(Chess.Empty, Side.RED);
        }
        else {
            assert chess != null;
            revealedPieces.eatPiece(chess);
            if(move.getNewx() == -1) {
                board[move.getCurx()][move.getCury()] = new Piece(chess);
            }
            else {
                score[1 - chess.getSide().ordinal()] += chess.getType().getScore();
                eatenPieces.eatPiece(chess);
                board[move.getNewx()][move.getNewy()] = new Piece(board[move.getCurx()][move.getCury()]);
                board[move.getCurx()][move.getCury()] = new Piece(Chess.Empty, Side.RED);
            }
        }
    }
    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(obj == this) return true;
        if(obj instanceof State) {
            State s = (State) obj;
            for(int i=0; i<4; ++i) for(int j=0; j<8; ++j) if(!board[i][j].equals(s.getBoard()[i][j])) return false;
            if(!eatenPieces.equals(s.getEatenPieces())) return false;
            if(!revealedPieces.equals(s.getRevealedPieces())) return false;
            //if(score[0] != s.getScore()[0] || score[1] != s.getScore()[1]) return false;
            return true;
        }
        else return false;
    }

    public EatenPieces getRevealedPieces() {
        return revealedPieces;
    }

    public void setRevealedPieces(EatenPieces revealedPieces) {
        this.revealedPieces = revealedPieces;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void setBoard(Piece[][] board) {
        this.board = board;
    }

    public EatenPieces getEatenPieces() {
        return eatenPieces;
    }

    public void setEatenPieces(EatenPieces eatenPieces) {
        this.eatenPieces = eatenPieces;
    }
    public String debugBoard() {
        String s = "";
        for(int i=0; i<4; ++i) {
            for(int j=0; j<8; ++j) {
                s += board[i][j].getType().toString() + " ";
            }
            s += "\n";
        }
        return s;
    }
    public String toString() {
        String s = "";
        for(int i=0; i<4; ++i) {
            for(int j=0; j<8; ++j) {
                s += board[i][j].toString() + " ";
            }
        }
        s += eatenPieces.getS() + " " + revealedPieces.getS() + " " + score[0] + " " + score[1];
        return s;
    }
    public void init(String s) throws WrongChessBoardSize, InvalidChessType {
        String[] ss = s.split(" ");
        if(ss.length != 36) {
            throw new WrongChessBoardSize();
        }
        int k = 0;
        for(int i=0; i<4; ++i) {
            for(int j=0; j<8; ++j) {
                board[i][j] = new Piece(Chess.Unknown, Side.RED);
                String tmp = ss[k++];
                tmp += " " +  ss[k++];
                board[i][j].init(tmp);
            }
        }
        eatenPieces.s = Long.parseLong(ss[k++]);
        revealedPieces.s = Long.parseLong(ss[k++]);
        score[0] = Integer.parseInt(ss[k++]);
        score[1] = Integer.parseInt(ss[k++]);
    }
}
