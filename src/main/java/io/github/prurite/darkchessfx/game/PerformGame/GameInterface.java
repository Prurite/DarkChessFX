package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.UserData.Player;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.util.Pair;

import java.util.ArrayList;

public interface GameInterface {
    //public Game();
    //public Game(GameConfig config);
    // I've made this 2 comments because they can't be valid interface statement
    public Piece[][] getChessBoard();
    //public Side getPlayerSide(PlayerInGame p);
    //it's not needed, because we can use p.getSide() instead
    public ArrayList<Piece> getCaptured(PlayerInGame p);
    public ArrayList<Pair<Integer, Integer>> getPossibleMoves(Pair<Integer, Integer> pos);
    public String performMove(PlayerInGame p, Move move);
    // Skipping a turn due to timeout is handled by GUI
    public String loadGame(String path);
    public String saveGame(String path);
    public int getCurrentMovePos(); // -1 first, 0 mid, 1 last
    public int goToPrevMove(); // Return current move id
    public int goToNextMove();
    public GameConfig getGameConfig();
    public Player getWinner(); // null if not ended
}