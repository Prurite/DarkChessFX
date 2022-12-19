package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.model.Player;
import io.github.prurite.darkchessfx.model.GameConfig;
import io.github.prurite.darkchessfx.model.Pos;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IllegalFormatCodePointException;

import static java.lang.Integer.signum;

public class Game implements GameInterface {
    // game information
    private int WINNING_SCORE;
    private boolean scored;
    private double maxTurnTime;
    private double maxTotalTime;
    private GameConfig config;
    AIPlayer aiPlayer;
    @Override
    public GameConfig getGameConfig() { return config; }
    public void setGameConfig(GameConfig config) {
        this.config = config;
        WINNING_SCORE = config.minimumScore;
        maxTurnTime = config.maxTurnTime;
        maxTotalTime = config.maxTotalTime;
        aiPlayer = new AIPlayer(config.aiDifficulty);
        //startGame(new Player(config.player1), new Player(config.player2));
    }

    public Game(GameConfig config) {
        setGameConfig(config);

        scored = true;
        currentMovePos = -1;
        revealedPos = new ArrayList<>();

    }
    public Game() {
        this(new GameConfig());
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


    ArrayList<Pos> revealedPos;
    private ArrayList<Board> lastChessboard;
    private ArrayList<Move> lastMove;
    private ArrayList<EatenPieces> lastEatenPieces;
    private Piece[][] chessboard; // = new Chess[4][8];
    public Piece[][] getChessboard() {
        Piece[][] a = new Piece[4][8];
        for(int i=0; i<4; ++i)
            System.arraycopy(chessboard[i], 0, a[i], 0, a[i].length);
        for(Pos p : revealedPos) {
            a[p.getX()][p.getY()] = revealedChessboard[p.getX()][p.getY()];
        }
        return a;
    }
    private Piece[][] revealedChessboard; // = new Chess[4][8];
    private EatenPieces eatenPieces;
    private EatenPieces revealedPieces;
    public EatenPieces getRevealedPieces() { return revealedPieces; }
    public Piece[][] getChessBoard() {
        return chessboard;
    }

    public Piece getPieceOnBoard(Pos p){
        return getChessBoard()[p.getY()][p.getX()];
    }

    public ArrayList<Piece> getCaptured(Side s) {
        ArrayList<Piece> res = new ArrayList<>();
        for(Chess c : Chess.values()) {
            int t = eatenPieces.eaten(new Piece(c, s));
            for(int i=0; i<t; ++i) {
                res.add(new Piece(c, s));
            }
        }
        return res;
    }
    public ArrayList<Pos> getValidMoves(Pos pos) {
        pos = pos.swapXY();
        ArrayList<Pos> res = new ArrayList<>();
        int x = pos.getX(), y = pos.getY();
        Piece piece = chessboard[x][y];
        if(piece.getType() == Chess.Unknown || piece.getType() == Chess.Empty) return null;
        PlayerInGame p = players[players[1].getSide() == piece.getSide() ? 1 : 0];
        for(int i=0; i<4; ++i) {
            for(int j=0; j<8; ++j) {
                if(checkMove(p, new Move(x, y, i, j)) == null) {
                        res.add(new Pos(j,i));
                }
            }
        }
        return res;
    }

    public EatenPieces getEatenPieces() {
        return eatenPieces;
    }

    private Move curMove;
    // save all the information of the game to a file
    public void saveGame(File file) throws IOException {
        //BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    }
    public void loadGame(File file) throws IOException, IllegalFormatCodePointException {

    }

    public void aiMove() {
        Move move = aiPlayer.makeMove(new State(chessboard, eatenPieces, revealedPieces), players[currentPlayer].getSide());
        if(move != null) {
            move = move.swapXY();
            performMove(players[currentPlayer], move);
        }
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
    private void setPlayers(GameConfig config) {
        players = new PlayerInGame[2];
        players[0] = new PlayerInGame(new Player(config.player1), Side.RED);
        players[1] = new PlayerInGame(new Player(config.player2), Side.BLACK);
        currentPlayer = 0;
    }
    public void startGame() {
        setGameConfig(config);
        setPlayers(config);

        revealedChessboard = new Piece[4][8];
        ArrayList<Piece> tmp = new ArrayList<Piece>();
        for(int i=0; i<32; ++i) tmp.add(new Piece(arrayOfPieces[i].getType(), arrayOfPieces[i].getSide()));
        Collections.shuffle(tmp);
        for(int i=0; i<32; ++i) revealedChessboard[i >> 3][i & 7] = tmp.get(i);

        chessboard = new Piece[4][8];
        System.out.println("Starting game"); // DEBUG
        for(int i=0; i<4; ++i) for(int j=0; j<8; ++j) chessboard[i][j] = new Piece(Chess.Unknown, Side.RED);
        eatenPieces = new EatenPieces();
        revealedPieces = new EatenPieces();
        lastChessboard = new ArrayList<>();
        lastEatenPieces = new ArrayList<>();
        lastMove = new ArrayList<>();

        initTime();

        curMove = new Move();
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

    public void endGame(Player p1, Player p2, PlayerInGame winner) {
        double curTime = System.currentTimeMillis();
        if(p2.getName().equals(players[0].getNameProperty().getValue())) {
            Player tmp = p1;
            p1 = p2;
            p2 = tmp;
        }
        p1.addGameCount(); p2.addGameCount();
        p1.addTime(curTime - startTime);
        p2.addTime(curTime - startTime);
        if(scored) { p1.addScoredGameCount(); p2.addScoredGameCount(); }
        if(winner != null) {
            winner.getPlayer().addWinnedGameCount();
        //    GameResultWindow.showResult(players[winner]);
        }
        //else GameResultWindow.showResult(null);
    }
    public void endGame(Player p1, Player p2) {
        PlayerInGame winner = (players[0].getScore() != players[1].getScore() && checkEndGame()) ? (players[0].getScore() > players[1].getScore() ? players[0] : players[1]) : null;
        endGame(p1, p2, winner);
    }
    public void surrender(Player p1, Player p2, PlayerInGame p) {
        endGame(p1, p2, players[p.getName().equals(players[0].getName()) ? 1 : 0]);
    }
    public boolean checkEndGame() {
        return (players[0].getScore() >= WINNING_SCORE || players[1].getScore() >= WINNING_SCORE);
    }

    public void revealPiece(Pos pos) {
        pos = pos.swapXY();
        revealedPos.add(pos);
    }
    public void hidePiece(Pos pos) {
        pos = pos.swapXY();
        revealedPos.remove(pos);
    }
    public void hideAllPieces() {
        revealedPos.clear();
    }

    public PlayerInGame getWinner() {
        if(players[0].getScore() >= WINNING_SCORE) return players[0]; //.getPlayer();
        if(players[1].getScore() >= WINNING_SCORE) return players[1]; //.getPlayer();
        return null;
    }

    public void setUnscored() {
        scored = true;
    }
    // when cononection lost for more than 10s

    private void changePlayer() {
        double curTime = System.currentTimeMillis();
        players[currentPlayer].subTotalTime(curTime - lastTurnTime);
        lastTurnTime = curTime;
        currentPlayer = (currentPlayer + 1) % 2;
    }
    public void doLastMove(int p, int sgn) {
        chessboard = lastChessboard.get(p).getChessboard();
        eatenPieces = lastEatenPieces.get(p);
        Move move = lastMove.get(p);
        int newx = move.getNewx(), newy = move.getNewy();
        if(newx!=-1 && newy !=-1 && chessboard[newx][newy].getType() != Chess.Empty) {
            if(chessboard[newx][newy].getType() == Chess.Unknown)
                eatPiece(revealedChessboard[newx][newy], sgn);
            else eatPiece(chessboard[newx][newy], sgn);
        }
    }
    public void goToPrevMove() {
        int p = -- currentMovePos;
        doLastMove(p, -1);
        changePlayer();
    }
    public void goToNextMove() {
        int p = ++ currentMovePos;
        doLastMove(p, 1);
        changePlayer();
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
    private int currentMovePos;
    private void storeLastMove() {
//        lastMove.add(new Move(curMove.getCurx(), curMove.getCury(), curMove.getNewx(), curMove.getNewy()));
//        lastChessboard.add(new Board(chessboard));
//        lastEatenPieces.add(new EatenPieces(eatenPieces));
        int pos = ++currentMovePos;
        if(lastMove.size() <= currentMovePos) {
            lastMove.add(new Move(curMove.getCurx(), curMove.getCury(), curMove.getNewx(), curMove.getNewy()));
            lastChessboard.add(new Board(chessboard));
            lastEatenPieces.add(new EatenPieces(eatenPieces));
        } else {
            lastMove.set(pos, new Move(curMove.getCurx(), curMove.getCury(), curMove.getNewx(), curMove.getNewy()));
            lastChessboard.set(pos, new Board(chessboard));
            lastEatenPieces.set(pos, new EatenPieces(eatenPieces));
        }
    }
    public int getCurrentMovePos() { return currentMovePos; }
    public int getMoveCount() { return lastMove.size(); }

    private void makeMove() {
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
        storeLastMove();
        // calculate scores and time spent
        // update chessboard and current player
        // check if the game is over
    }

//    private PlayerInGame getPlayerInGame(Player player) {
//        if(players[0].getPlayer().getName().equals(player)) return players[0];
//        if(players[1].getPlayer().getName().equals(player)) return players[1];
//        return null;
//    }
    public PlayerInGame getPlayerInGame1() { return players[0]; }
    public PlayerInGame getPlayerInGame2() { return players[1]; }
    public void setPlayerInGame1(PlayerInGame p) { players[0] = p; }
    public void setPlayerInGame2(PlayerInGame p) { players[1] = p; }


    @Override
    public PlayerInGame getCurrentPlayer() { return players[currentPlayer]; }

    public String performMove(PlayerInGame playerInGame, Move move) {
        move = move.swapXY();

        if(move.getNewx() == -1) {
            if(chessboard[move.getCurx()][move.getCury()].getType() == Chess.Unknown) {
                turnUpChess(move.getCurx(), move.getCury());
                return null;
            }
            else return MoveChessMessage.FirstClickError.getInfo();
        }

        String msg = checkMove(playerInGame, move);
        curMove = move;
        if(msg == null) makeMove();
        return msg;
    }

    private void turnUpChess(int x, int y) {
        //int x = curMove.getCurx(), y = curMove.getCury();
        chessboard[x][y] = new Piece(revealedChessboard[x][y]);
        revealedPieces.eatPiece(revealedChessboard[x][y]);
        updGUI(x, y);
        changePlayer();

        storeLastMove();
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
            storeLastMove();
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
