package io.github.prurite.darkchessfx.game.LoadingExceptions;

public class InvalidChessType extends Exception {
    static int code = 103;
    @Override
    public String toString() {
        return "InvalidChessType: " + code;
    }
}
