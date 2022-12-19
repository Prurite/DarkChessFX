package io.github.prurite.darkchessfx.game.LoadingExceptions;

public class WrongChessBoardSize extends Exception {
    static int code = 102;
    @Override
    public String toString() {
        return "WrongChessBoardSize: " + code;
    }
}
