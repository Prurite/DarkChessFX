package io.github.prurite.darkchessfx.game.StrongAI;

import io.github.prurite.darkchessfx.game.PerformGame.Side;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Node {
    private int visitCount;
    private int winCount;
    private ArrayList<Node> children;
    private Node parent;
    private State state;
    private Side mySide;
}
