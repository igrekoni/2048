package com.tile.janv.userinterface1.util;

/**
 * Created by janv on 02-Jan-16.
 */
public final class GameUtil {

    private static final String SCORE_VALUES_SEPARATOR = "::";
    private static final String VALUES_SEPARATOR = ",";

    private GameUtil() {
    }

    /**
     * Creates a string with values of current game.
     * Form of string is: score:cellValue[,cellValue}.
     * @param gameInfo
     * @return
     */
    public static String gameInfoToString(GameInfo gameInfo) {
        if (gameInfo == null ||
                gameInfo.getValues() == null || gameInfo.getValues().length == 0) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append(gameInfo.getScore());
        stringBuilder.append(SCORE_VALUES_SEPARATOR);
        for (int value : gameInfo.getValues()) {
            stringBuilder.append(value);
            stringBuilder.append(VALUES_SEPARATOR);
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        return stringBuilder.toString();
    }

    public static GameInfo extractGameInfo(String gameInfoAsString) {
        String[] scoreSplit = gameInfoAsString.split(SCORE_VALUES_SEPARATOR);
        int score = Integer.parseInt(scoreSplit[0]);
        String[] valuesString = scoreSplit[1].split(VALUES_SEPARATOR);
        int[] values = new int[valuesString.length];
        for (int i = 0; i < valuesString.length; i++) {
            values[i] = Integer.parseInt(valuesString[i]);
        }
        return new GameInfo(score, values);
    }
}
