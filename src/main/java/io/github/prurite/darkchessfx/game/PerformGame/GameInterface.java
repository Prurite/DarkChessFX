package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.PerformGame.PlayerInGame;
import io.github.prurite.darkchessfx.model.GameConfig;
import javafx.util.Pair;

import java.util.ArrayList;

public interface GameInterface {
    //  Game();
    //  Game(GameConfig config);
    Piece[][] getChessBoard();
    ArrayList<Piece> getCaptured(PlayerInGame p);
    ArrayList<Pair> getPossibleMoves(Pair pos);
    String performMove(PlayerInGame p, Move move);
    // Skipping a turn due to timeout is handled by GUI
    String loadGame(String path);
    String saveGame(String path);
    int getCurrentMovePos(); // -1 first, 0 mid, 1 last
    int goToPrevMove(); // Return current move id
    int goToNextMove();
    GameConfig getGameConfig();
    PlayerInGame getWinner(); // null if not ended
    PlayerInGame getPlayerInGame1();
    PlayerInGame getPlayerInGame2();
    void setPlayerInGame1(PlayerInGame p);
    void setPlayerInGame2(PlayerInGame p);
    void pauseGame();
    void resumeGame();
}