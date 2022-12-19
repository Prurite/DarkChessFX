/*  The API description file.
    All requirements to the backend may be described here.
 */


package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.LoadingExceptions.*;
import io.github.prurite.darkchessfx.model.GameConfig;
import io.github.prurite.darkchessfx.model.Player;
import io.github.prurite.darkchessfx.model.Pos;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllegalFormatException;

public interface GameInterface {
    //  Game();
    //  Game(GameConfig config);

    /* Variables needed in Game
    PlayerInGame player1, player2;
    AIPlayer aiPlayer;
        Move aiPlayer.makeMove(Piece[][] board, Side side);
     */

    Piece[][] getChessBoard(); // Returns current chessboard visible to both players.
    ArrayList<Piece> getCaptured(Side s);
    ArrayList<Pos> getValidMoves(Pos pos);
    String performMove(PlayerInGame p, Move move);
    void aiMove();

    // method for load game, throws exception when file is not valid or file format is not valid
    void loadGame(File file) throws IOException, IllegalFormatException , WrongFileFormatException, WrongChessBoardSize, PlayerInformationMissing, InvalidPreviousMove, InvalidChessType;
    void saveGame(File file) throws IOException;
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
    void surrender(Player p1, Player p2, PlayerInGame p);
    void revealPiece(Pos pos); // When this method is called, the piece at pos will be revealed
    void hidePiece(Pos pos);
    void hideAllPieces();
    void startGame();
    // When this is called, the game will start
    // aiPlayer and chessboard etc. may be prepared here
    void endGame(Player p1, Player p2);
}

/* PlayerInGame methods needed:
    int getScore(); // return current score
    String getName();
 */

/* Move: Pos pos1, pos2 */

/* Side: BLACK, RED, UNKNOWN */

/* PlayerInGame should not be bonded to a Player object. Instead, the outer object will be updated
    when endGame is called
 */
