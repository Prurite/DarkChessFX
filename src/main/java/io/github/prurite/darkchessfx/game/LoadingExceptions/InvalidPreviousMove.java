package io.github.prurite.darkchessfx.game.LoadingExceptions;

public class InvalidPreviousMove extends Exception {
    static int code = 104;
    @Override
    public String toString() {
        return "InvalidPreviousMove: " + code;
    }

}
