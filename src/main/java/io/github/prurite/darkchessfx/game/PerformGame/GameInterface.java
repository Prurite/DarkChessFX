package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.UserData.Player;

public interface GameInterface {

    //public Game(String path); // constructor
    public PlayerInGame[] startGame(Player p1, Player p2);
    public String clickOnChess(PlayerInGame p, int x, int y);
    public boolean checkEndGame();
    public PlayerInGame getWinner();
    // endGame, calculate gameCount, scoredGameCount, winCount, and playing time, calll the showResultWindow
    public void endGame();
    public void setUnscored(); // haven't implemented
    public PlayerInGame qryCur(); // return the current turn player
    public Piece[][] getChessboard();
    public EatenPieces getEatenPieces();
    public void saveGame();// haven't implemented
    public void loadGame();// haven't implemented
    public Move withdraw(); // return last move
    public void updGUI(int x,int y);
    // {
        // need to update both chessboard and eaten Pieces
    //}
    public void updGUI(Move move);
    // {
//        updGUI(move.getCurx(), move.getCury());
//        updGUI(move.getNewx(), move.getNewy());
//    }
    public void updEndGUI();
//     {
//        // need to update both chessboard and eaten Pieces
//    }}
    public String qryPiece(int x, int y); // format as "General-BLACK"
    /*
    public boolean FirstMoveFlag;
    is deefined, with this you can know if this is the first turn (and you need to update side of each of the players)
     */
     public void pause(); // haven't checked if this is not faulty
     public void resume(); // haven't checked if this is not faulty

     public String debugChessboard(); // return status of the all board

}