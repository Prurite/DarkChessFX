package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.model.Player;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Integer.signum;

public class Game implements GameInterface {
    // game information
    private int WINNING_SCORE;
    private boolean scored;
    private double maxTurnTime;
    private double maxTotalTime;
    private GameConfig config;
    public Game(GameConfig config) {
        this.config = config;
        WINNING_SCORE = config.minimumScore;
        maxTurnTime = config.maxTurnTime;
        maxTotalTime = config.maxTotalTime;
        scored = true;

        startGame(config.player1, config.player2);
    }
    public Game() {
        maxTurnTime = 30 * 1000;
        maxTotalTime = 10000000f * 1000;
        scored = true;
    }

    // current state of countdown
    private double lastTurnTime;
    private double lastPauseTime;
    private boolean pausing;
    private double startTime;

    private void initTime() {
        lastTurnTime = System.currentTimeMillis();
        lastPauseTime = lastTurnTime;
        startTime = lastPauseTime;
        pausing = false;
    }

    //information of players
    PlayerInGame[] players;
    private int currentPlayer;

    //information of chessboard
    class Board {
        private Piece[][] chessboard;
        public Board(Piece[][] chessboard) {
            this.chessboard = new Piece[4][8];
            for(int i=0; i<4; ++i) {
                System.arraycopy(chessboard[i], 0, this.chessboard[i], 0, this.chessboard[i].length);
            }
        }

        public Piece[][] getChessboard() {
            return chessboard;
        }
    }



    private ArrayList<Board> lastChessboard;
    private ArrayList<Move> lastMove;
    private ArrayList<EatenPieces> lastEatenPieces;
    private Piece[][] chessboard; // = new Chess[4][8];
    public Piece[][] getChessboard() { return chessboard; }
    private Piece[][] revealedChessboard; // = new Chess[4][8];
    private EatenPieces eatenPieces;
    private EatenPieces revealedPieces;
    public EatenPieces getRevealedPieces() { return revealedPieces; }
    public Piece[][] getChessBoard() {
        return chessboard;
    }
    public ArrayList<Piece> getCaptured(PlayerInGame p) {
        ArrayList<Piece> res = new ArrayList<>();
        for(Chess c : Chess.values()) {
            int t = eatenPieces.eaten(new Piece(c, p.getSide()));
            for(int i=0; i<t; ++i) {
                res.add(new Piece(c, p.getSide()));
            }
        }
        return res;
    }
    public ArrayList<Pair<Integer, Integer>> getPossibleMoves(Pair<Integer, Integer> pos) {
        ArrayList<Pair<Integer, Integer>> res = new ArrayList<>();
        if(chessboard[pos.getKey()][pos.getValue()].getType() == Chess.Unknown) return null;
        for(int i=0; i<4; ++i) {
            for(int j=0; j<8; ++j) {
                if(chessboard[i][j].getType() == Chess.Unknown) {
                    if(chessboard[pos.getKey()][pos.getValue()].getType().canMove(pos, new Pair<>(i, j))) {
                        res.add(new Pair<>(i, j));
                    }
                }
            }
        }

    }

    public EatenPieces getEatenPieces() {
        return eatenPieces;
    }

    private Move curMove;
    // save all the information of the game to a file
    public void saveGame() {
    }
    public void loadGame() {
    }

    // create a static array of Piece with 2 of each kind of Piece on each side
    static Piece[] arrayOfPieces = {
        new Piece(Chess.General, Side.RED),
        new Piece(Chess.Advisor, Side.RED),
        new Piece(Chess.Advisor, Side.RED),
        new Piece(Chess.Minister, Side.RED),
        new Piece(Chess.Minister, Side.RED),
        new Piece(Chess.Chariot, Side.RED),
        new Piece(Chess.Chariot, Side.RED),
        new Piece(Chess.Horse, Side.RED),
        new Piece(Chess.Horse, Side.RED),
        new Piece(Chess.Cannon, Side.RED),
        new Piece(Chess.Cannon, Side.RED),
        new Piece(Chess.Soldier, Side.RED),
        new Piece(Chess.Soldier, Side.RED),
        new Piece(Chess.Soldier, Side.RED),
        new Piece(Chess.Soldier, Side.RED),
        new Piece(Chess.Soldier, Side.RED),
        new Piece(Chess.General, Side.BLACK),
        new Piece(Chess.Advisor, Side.BLACK),
        new Piece(Chess.Advisor, Side.BLACK),
        new Piece(Chess.Minister, Side.BLACK),
        new Piece(Chess.Minister, Side.BLACK),
        new Piece(Chess.Chariot, Side.BLACK),
        new Piece(Chess.Chariot, Side.BLACK),
        new Piece(Chess.Horse, Side.BLACK),
        new Piece(Chess.Horse, Side.BLACK),
        new Piece(Chess.Cannon, Side.BLACK),
        new Piece(Chess.Cannon, Side.BLACK),
        new Piece(Chess.Soldier, Side.BLACK),
        new Piece(Chess.Soldier, Side.BLACK),
        new Piece(Chess.Soldier, Side.BLACK),
        new Piece(Chess.Soldier, Side.BLACK),
        new Piece(Chess.Soldier, Side.BLACK),
    };

    public PlayerInGame[] startGame(Player p1, Player p2) {
        revealedChessboard = new Piece[4][8];
        ArrayList<Piece> tmp = new ArrayList<Piece>();
        for(int i=0; i<32; ++i) tmp.add(new Piece(arrayOfPieces[i].getType(), arrayOfPieces[i].getSide()));
        Collections.shuffle(tmp);
        for(int i=0; i<32; ++i) revealedChessboard[i >> 3][i & 7] = tmp.get(i);

        chessboard = new Piece[4][8];
        for(int i=0; i<4; ++i) for(int j=0; j<8; ++j) chessboard[i][j] = new Piece(Chess.Unknown, Side.RED);
        eatenPieces = new EatenPieces();
        revealedPieces = new EatenPieces();
        lastChessboard = new ArrayList<>();
        lastEatenPieces = new ArrayList<>();
        lastMove = new ArrayList<>();

        initTime();

        players = new PlayerInGame[2];
        players[0] = new PlayerInGame(p1, Side.RED);
        players[1] = new PlayerInGame(p2, Side.BLACK);
        currentPlayer = 0;

        curMove = new Move();

        return players;
    }
//    public String firstMove(PlayerInGame u, int x, int y) {
//        if(u.getPlayer().getName() != players[currentPlayer].getPlayer().getName()) return MoveChessMessage.NotCurrentTurnPlayer.getInfo();
//        players[currentPlayer].setSide(revealedChessboard[x][y].getSide());
//        players[1-currentPlayer].setSide(players[currentPlayer].getSide().getOpposite());
//        return clickOnChess(u, x, y);
//    }
    private void eatPiece(Piece x,int sgn) {
        if(sgn == 1) eatenPieces.eatPiece(x);
        players[(x.getSide() == players[1].getSide() ? 0 : 1)].addScore(x.getType().getScore() * sgn);
    }

    public PlayerInGame endGame() {
        double curTime = System.currentTimeMillis();
        for(int i=0; i<2; ++i) {
            players[i].getPlayer().addGameCount();
            players[i].getPlayer().addScoredGameCount();
            players[i].getPlayer().addTime(curTime - startTime);
        }
        if(players[0].getScore() != players[1].getScore()) {
            int winner = players[0].getScore().getValue() > players[1].getScore().getValue() ? 0 : 1;
            players[winner].getPlayer().addWinnedGameCount();
            return players[winner];
        //    GameResultWindow.showResult(players[winner]);
        }
        return null;
        //else GameResultWindow.showResult(null);
    }
    public boolean checkEndGame() {
        return (players[0].getScore().getValue() >= WINNING_SCORE || players[1].getScore().getValue() >= WINNING_SCORE);
    }

    public Player getWinner() {
        if(players[0].getScore().getValue() >= WINNING_SCORE) return players[0].getPlayer();
        if(players[1].getScore().getValue() >= WINNING_SCORE) return players[1].getPlayer();
        return null;
    }

    public void setUnscored() {}
    // when cononection lost for more than 10s

    private void changePlayer() {
        double curTime = System.currentTimeMillis();
        players[currentPlayer].subTotalTime(curTime - lastTurnTime);
        lastTurnTime = curTime;
        currentPlayer = (currentPlayer + 1) % 2;
    }
    public Move withdraw() {
        assert(lastChessboard.size() > 0);
        int p = lastChessboard.size() - 1;
        chessboard = lastChessboard.get(p).getChessboard();
        eatenPieces = lastEatenPieces.get(p);
        Move move = lastMove.get(p);
        int newx = move.getNewx(), newy = move.getNewy();
        assert(move.getCurx() !=-1 && move.getCury() != -1);
        if(newx!=-1 && newy !=-1 && chessboard[newx][newy].getType() != Chess.Empty) {
            if(chessboard[newx][newy].getType() == Chess.Unknown)
                eatPiece(revealedChessboard[newx][newy], -1);
            else eatPiece(chessboard[newx][newy], -1);
        }
        lastChessboard.remove(p);
        lastEatenPieces.remove(p);
        lastMove.remove(p);
        changePlayer();
        return move;
    }

    private boolean checkIntermediate(Move move) {
        int dx = signum(move.getNewx() - move.getCurx());
        int dy = signum(move.getNewy() - move.getCury());
        int curx = move.getCurx(), cury =  move.getCury();
        int count = 0;
        curx += dx; cury += dy;
        while(curx != move.getNewx() || cury != move.getNewy()) {
            if(chessboard[curx][cury].getType() != Chess.Empty)
                count ++;
            curx += dx; cury += dy;
        }
        return count == 1;
    }
    private String checkMove(PlayerInGame player, Move move) {
        if(move.sameX() && move.sameY()) return MoveChessMessage.ChessToSamePosition.getInfo();
        if(player != players[currentPlayer]) return MoveChessMessage.NotCurrentTurnPlayer.getInfo();
        Piece u = chessboard[move.getCurx()][move.getCury()];
        Piece v = chessboard[move.getNewx()][move.getNewy()];
        if(u.getSide() != player.getSide()) return MoveChessMessage.FirstClickError.getInfo();
        if(u.getType() == Chess.Cannon) {
            if(v.getType() == Chess.Empty) return MoveChessMessage.CannonToEmpty.getInfo();
            if(!move.sameX() && !move.sameY()) return MoveChessMessage.CannonDiagonal.getInfo();
            if(!checkIntermediate(move)) return MoveChessMessage.CannonNotOneIntermediate.getInfo();
            return null;
        }

        if(move.getDis() != 1) return MoveChessMessage.MoveNotAdjacent.getInfo();
        if(v.getType() == Chess.Empty) return null;
        if(v.getType() == Chess.Unknown) return MoveChessMessage.CaptureUnknown.getInfo();
        if(u.getSide() == v.getSide()) return MoveChessMessage.CaptureSameSide.getInfo();
        if(v.getType().CompareTo(u.getType()) > 0) return MoveChessMessage.CaptureHighRank.getInfo();
        return null;
    }

    public void updGUI(int x,int y) {
        // need to update both chessboard and eaten Pieces
    }
    public void updGUI(Move move) {
        updGUI(move.getCurx(), move.getCury());
        updGUI(move.getNewx(), move.getNewy());
    }
    public void updEndGUI() {
        // need to update both chessboard and eaten Pieces
    }

    public String qryPiece(int x,int y) {

        return chessboard[x][y].getType().toString() + "-" + chessboard[x][y].getSide().toString() + ".png";
    }
    public PlayerInGame qryCur() { return players[currentPlayer]; }

    private void storeLastMove() {
        lastMove.add(new Move(curMove.getCurx(), curMove.getCury(), curMove.getNewx(), curMove.getNewy()));
        lastChessboard.add(new Board(chessboard));
        lastEatenPieces.add(new EatenPieces(eatenPieces));
    }

    private void makeMove() {
        storeLastMove();
        int x1 = curMove.getCurx(), y1 = curMove.getCury();
        int x2 = curMove.getNewx(), y2 = curMove.getNewy();
        if(chessboard[x2][y2].getType() != Chess.Empty) {
            if(chessboard[x2][y2].getType() == Chess.Unknown) {
                eatPiece(revealedChessboard[x2][y2], 1);
                revealedPieces.eatPiece(revealedChessboard[x2][y2]);
            }
            else {
                eatPiece(chessboard[x2][y2], 1);
            }
        }
        chessboard[x2][y2] = new Piece(chessboard[x1][y1]);
        chessboard[x1][y1] = new Piece(Chess.Empty, Side.RED);
        updGUI(curMove);
        changePlayer();
        // calculate scores and time spent
        // update chessboard and current player
        // check if the game is over
    }
    private void turnUpChess(int x, int y) {
        storeLastMove();
        //int x = curMove.getCurx(), y = curMove.getCury();
        chessboard[x][y] = new Piece(revealedChessboard[x][y]);
        revealedPieces.eatPiece(revealedChessboard[x][y]);
        updGUI(x, y);
        changePlayer();
    }

    private void setCurMoveFrom(int x, int y) {
        curMove.setCurx(x);
        curMove.setCury(y);
    }
    private void setCurMoveTo(int x,int y) {
        curMove.setNewx(x);
        curMove.setNewy(y);
    }
    public boolean firstMoveFlag;
    public String clickOnChess(PlayerInGame player, int x, int y) {
        assert(x>=0 && y>=0);
        // when receive a click on the chessboard, call this method
        if(pausing == true) return MoveChessMessage.MovePausing.getInfo();
        if(firstMoveFlag == false) {
            firstMoveFlag = true;
            int c = player.equals(players[1]) ? 1 : 0;
            players[c].setSide(revealedChessboard[x][y].getSide());
            players[c^1].setSide(players[c].getSide().getOpposite());
        }
        if(curMove.getCurx() == x && curMove.getCury() == y) {
            setCurMoveFrom(-1, -1);
            return MoveChessMessage.FirstClickError.getInfo();
        }

        if(players[currentPlayer].getSide() != player.getSide()) return MoveChessMessage.NotCurrentTurnPlayer.getInfo();
        if(chessboard[x][y].getType() == Chess.Empty && curMove.getCurx() == -1) return MoveChessMessage.FirstClickError.getInfo();

        if(curMove.getCurx() != -1) {
            setCurMoveTo(x, y);
            String s = checkMove(player, curMove);
            if(s == null) {
                makeMove();
                setCurMoveFrom(-1, -1);
            }
            setCurMoveTo(-1, -1);
            return s;
        }

        if(chessboard[x][y].getType() == Chess.Unknown) {
            setCurMoveFrom(x, y);
            turnUpChess(x, y);
            setCurMoveFrom(-1, -1);
            return null;
        }


        if(chessboard[x][y].getSide() != player.getSide()) {
            return MoveChessMessage.FirstClickError.getInfo();
        }

        setCurMoveFrom(x, y);
        return MoveChessMessage.NeedSecondClick.getInfo();
    }

    public void pause() {
        lastPauseTime = System.currentTimeMillis();
        pausing = true;
        // set limit for pause time
    }
    public void resume() {
        lastTurnTime += System.currentTimeMillis() - lastPauseTime;
        pausing = false;
    }
    public State getState() {
        return new State(chessboard, eatenPieces, revealedPieces);
    }

    public String debugChessboard() {
        StringBuilder s = new StringBuilder();
        s.append("Chessboard:\n");
        for(int i=0; i<4; ++i) {
            for(int j=0;j<8; ++j)
                s.append(chessboard[i][j].getType().toString() + " ");
            s.append("\n");
        }
        String u = new String(s);
        return u;
    }

}
