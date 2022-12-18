package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.UserData.Player;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.util.Pair;

import java.util.ArrayList;

public interface GameInterface {
    //  Game();
    //  Game(GameConfig config);
    Piece[][] getChessBoard();
    Side getPlayerSide(PlayerInGame p);
    ArrayList<Piece> getCaptured(Player p);
    int getScore(Player p);
    ArrayList<Pair> getPossibleMoves(Pair pos);
    String performMove(PlayerInGame p, Move move);
    // Skipping a turn due to timeout is handled by GUI
    String loadGame(String path);
    String saveGame(String path);
    int getCurrentMovePos(); // -1 first, 0 mid, 1 last
    int goToPrevMove(); // Return current move id
    int goToNextMove();
    GameConfig getGameConfig();
    Player getWinner(); // null if not ended
    Player getPlayer1();
    Player getPlayer2();
    void setPlayer1(Player p);
    void setPlayer2(Player p);
}