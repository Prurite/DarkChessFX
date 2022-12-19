package io.github.prurite.darkchessfx.game.LoadingExceptions;

public class PlayerInformationMissing extends Exception {
    static int code = 104;
    @Override
    public String toString() {
        return "PlayerInformationMissing: " + code;
    }
}
