package io.github.prurite.darkchessfx.model;

public enum ChessType {
    GENERAL("General", "G"),
    ADVISOR("Advisor", "A"),
    ELEPHANT("Elephant", "E"),
    HORSE("Horse", "H"),
    CHARIOT("Chariot", "C"),
    CANNON("Cannon", "N"),
    SOLDIER("Soldier", "S"),
    COVERED("Covered", "X"),
    EMPTY("Empty", "O");

    private final String name, shortName;

    ChessType(String name, String shortName) {
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
