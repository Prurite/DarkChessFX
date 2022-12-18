/*  The API description file.
    All requirements to the backend may be described here.
 */


package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.model.GameConfig;
import io.github.prurite.darkchessfx.model.Pos;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public interface GameInterface {
    //  Game();
    //  Game(GameConfig config);

    /* Variables needed in Game
    PlayerInGame player1, player2;
    AIPlayer aiPlayer;
        Move aiPlayer.performMove(Piece[][] board, Side side);
     */

    Piece[][] getChessBoard(); // Returns current chessboard visible to both players.
    ArrayList<Piece> getCaptured(Side s);
    ArrayList<Pos> getValidMoves(Pos pos);
    String performMove(PlayerInGame p, Move move);
    String loadGame(File file) throws IOException;
    String saveGame(File file) throws IOException;
    int getCurrentMovePos(); // -1 first, 0 mid, 1 last
    void goToPrevMove(); // Return current move id
    void goToNextMove();
    GameConfig getGameConfig();
    PlayerInGame getWinner(); // null if not ended
    PlayerInGame getPlayerInGame1();
    PlayerInGame getPlayerInGame2();
    PlayerInGame getCurrentPlayer();
    void setPlayerInGame1(PlayerInGame p);
    void setPlayerInGame2(PlayerInGame p);
    void askForDraw();
    void surrender(PlayerInGame p);
    void revealPiece(Pair pos); // When this method is called, the piece at pos will be revealed
    void hidePiece(Pair pos);
    void hideAllPieces();
}

/* PlayerInGame methods needed:
    int getScore(); // return current score
    String getName();
 */

/* Move: Pos pos1, pos2 */

/* Side: BLACK, RED, UNKNOWN */
