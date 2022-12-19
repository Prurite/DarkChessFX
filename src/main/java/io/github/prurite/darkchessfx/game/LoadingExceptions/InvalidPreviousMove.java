package io.github.prurite.darkchessfx.game.LoadingExceptions;

public class InvalidPreviousMove extends Exception {
    static int code = 105;
    @Override
    public String toString() {
        return "InvalidPreviousMove: " + code;
    }

}
