package io.github.prurite.darkchessfx.game.PerformGame;

import io.github.prurite.darkchessfx.game.UserData.Player;

public interface GameInterface {
    public Game();
    public Game(GameConfig config);
    public Piece[][] getChessBoard();
    public Side getPlayerSide(PlayerInGame p);
    public Arraylist<Piece> getCaptured(Player p);
    public int getScore(Player p);
    public Arraylist<Pair> getPossibleMoves(Pair pos);
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