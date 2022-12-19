package io.github.prurite.darkchessfx.game.LoadingExceptions;

public class WrongFileFormatException extends Exception {
    static int code = 101;
    @Override
    public String toString() {
        return "WrongFileFormatException: " + code;
    }
}
