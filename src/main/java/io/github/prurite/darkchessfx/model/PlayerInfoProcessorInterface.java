package io.github.prurite.darkchessfx.model;

import java.io.File;
import java.io.IOException;

public interface PlayerInfoProcessorInterface {
    public Player getPlayer(String name);
    public void readFromFile(File file) throws IOException;
    public void saveToFile(File file) throws IOException;
}
