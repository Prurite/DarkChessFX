package io.github.prurite.darkchessfx.model;

public enum ChessSide {
    RED("Red", "R"),
    BLACK("Black", "B");

    private final String name, shortName;

    ChessSide(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
    }
}
