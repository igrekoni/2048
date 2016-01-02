package com.tile.janv.userinterface1.util;

/**
 * Created by janv on 02-Jan-16.
 */
public class GameInfo {
    int score;
    int[] values;

    public GameInfo(int score, int[] values) {
        this.score = score;
        this.values = values;
    }

    public int getScore() {
        return score;
    }

    public int[] getValues() {
        return values;
    }
}
