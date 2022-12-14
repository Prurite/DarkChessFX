package io.github.prurite.darkchessfx;

import java.io.InputStream;
import java.net.URL;

public class DarkchessFXResourcesLoader {
    private  DarkchessFXResourcesLoader() {};

    public static URL loadURL(String path) {
        return DarkchessFXResourcesLoader.class.getResource(path);
    }

    public static String load(String path) {
        return loadURL(path).toString();
    }

    public static InputStream loadStream(String name) {
        return DarkchessFXResourcesLoader.class.getResourceAsStream(name);
    }
}
