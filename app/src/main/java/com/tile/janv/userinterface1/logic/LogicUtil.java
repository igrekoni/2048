package com.tile.janv.userinterface1.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by janv on 22-Oct-15.
 */
public class LogicUtil {

    public enum Action {
        UP, DOWN, LEFT, RIGHT
    }

    private static Random random = new Random();

    private LogicUtil() {
    }

    public static void init(ValueContainer[][] valueContainerGrid) {
        emptyAll(valueContainerGrid);
        fillEmptyPosition(valueContainerGrid);
        fillEmptyPosition(valueContainerGrid);
    }

    public static void perform(Action action, ValueContainer[][] valueContainerGrid) {
        List<List<ValueContainer>> valueContainersList = toValuesContainersList(action, valueContainerGrid);
        for (int i = 0; i < valueContainersList.size(); i++) {
            squashOnList(valueContainersList.get(i));
        }
        fillEmptyPosition(valueContainerGrid);
    }

    public static void squashOnList(List<ValueContainer> valueContainerList) {
        //copy input values out of input obejects
        int[] input = new int[valueContainerList.size()];
        for (int i = 0; i < valueContainerList.size(); i++) {
            input[i] = valueContainerList.get(i).getValue();
        }
        //result = array, so always enough values
        int[] result = new int[valueContainerList.size()];
        Arrays.fill(result, 0);

        //pull values left
        int previousValue = 0;
        int pointer = 0;
        for (int value : input) {
            if (value > 0) {
                if (previousValue > 0) {
                    if (previousValue == value) {
                        result[pointer] = previousValue * 2;
                        pointer++;
                        previousValue = 0;
                    } else {
                        result[pointer] = previousValue;
                        pointer++;
                        previousValue = value;
                    }
                } else {
                    //no value to set
                    previousValue = value;
                }
            }
        }
        if (previousValue > 0) {
            result[pointer] = previousValue;
        }

        //copy back
        for (int i = 0; i < valueContainerList.size(); i++) {
            valueContainerList.get(i).setValue(result[i]);
        }
    }

    //--------------------------------------
    // helper methods
    //--------------------------------------

    /**
     * @param valueContainerGrid
     * @return true if an empty value was filled; false if no empty value found.
     */
    private static boolean fillEmptyPosition(ValueContainer[][] valueContainerGrid) {
        Position randomEmptyPosition = getRandomEmptyPosition(valueContainerGrid);
        if (randomEmptyPosition != null) {
            System.out.println("random position " + randomEmptyPosition + "  " +
                    valueContainerGrid[randomEmptyPosition.getRow()][randomEmptyPosition.getColumn()]);
            valueContainerGrid[randomEmptyPosition.getRow()][randomEmptyPosition.getColumn()].setValue(createRandomNewValue());
            return true;
        }
        return false;
    }

    private static void emptyAll(ValueContainer[][] valueContainerGrid) {
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                valueContainerGrid[i][j].setValue(0);
            }
        }
    }

    private static Position getRandomEmptyPosition(ValueContainer[][] valueContainerGrid) {
        List<Position> emptyPositionList = new ArrayList<>();
        for (int row = 0; row < valueContainerGrid.length; row++) {
            for (int column = 0; column < valueContainerGrid.length; column++) {
                int value = valueContainerGrid[row][column].getValue();
                if (value == 0) {
                    emptyPositionList.add(new Position(row, column));
                }
            }
        }
        System.out.println(emptyPositionList);
        if (emptyPositionList.size() > 0) {
            return emptyPositionList.get(random.nextInt(emptyPositionList.size()));
        }
        return null;
    }

    private static int createRandomNewValue() {
        if (random.nextFloat() <= 0.2) { // 20% 4
            return 4;
        }
        return 2;
    }

    private static List<List<ValueContainer>> toValuesContainersList(Action action, ValueContainer[][] valueContainerGrid) {
        int rows = valueContainerGrid.length;
        int columns = valueContainerGrid[0].length;
        List<List<ValueContainer>> valueContainersList = new ArrayList<>();
        switch (action) {
            case LEFT:
            case RIGHT:
                for (int i = 0; i < rows; i++) {
                    valueContainersList.add(new ArrayList<ValueContainer>());
                }
                for (int row = 0; row < rows; row++) {
                    for (int column = 0; column < columns; column++) {
                        valueContainersList.get(row).add(valueContainerGrid[row][column]);
                    }
                }
                break;
            case UP:
            case DOWN:
                for (int i = 0; i < columns; i++) {
                    valueContainersList.add(new ArrayList<ValueContainer>());
                }
                for (int column = 0; column < columns; column++) {
                    for (int row = 0; row < rows; row++) {
                        valueContainersList.get(column).add(valueContainerGrid[row][column]);
                    }
                }
                break;
        }
        switch (action) {
            case RIGHT:
            case DOWN:
                for (List<ValueContainer> valueContainerList : valueContainersList) {
                    Collections.reverse(valueContainerList);
                }
                break;
        }
        return valueContainersList;
    }

    //--------------------------------------
    // helper classes
    //--------------------------------------

    static public class Position {
        int row, column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        @Override
        public String toString() {
            return "Position{" +
                    "row=" + row +
                    ", column=" + column +
                    '}';
        }
    }
}
