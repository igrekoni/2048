package com.tile.janv.userinterface1.logic;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by janv on 22-Oct-15.
 */
public class LogicUtilTest extends TestCase {

    public void test_init__turnsAllValuesZeroButTwo() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();

        LogicUtil.init(valueContainerGrid);

        List<Integer>  nonZeroList = new ArrayList<>();
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int value = valueContainerGrid[i][j].getValue();
                if (value != 0) {
                    nonZeroList.add(value);
                }
            }
        }
        assertEquals(2, nonZeroList.size());
        for (Integer intValue : nonZeroList) {
            assertTrue(intValue == 2 || intValue == 4);
        }
     }

    public void test_squashOnList() throws Exception {
        int[][] input = new int[][]{
                {2,0,4,0},
                {0,4,0,8},
                {8,0,2,2},
                {16,0,0,16},
                {2,4,16,2},
                {2,2,2,2},
                {8,8,8,4},
                {8,2,4,4}
        };
        int[][] expectedResult = new int[][]{
                {2,4,0,0},
                {4,8,0,0},
                {8,4,0,0},
                {32,0,0,0},
                {2,4,16,2},
                {4,4,0,0},
                {16,8,4,0},
                {8,2,8,0}
        };
        List<List<ValueContainer>> valueContainersList = new ArrayList<>();
        for (int[] inputArray : input) {
            valueContainersList.add(createValueContainerList(inputArray));
        }

        for (List<ValueContainer> list : valueContainersList) {
            LogicUtil.squashOnList(list);
        }

        for (int i = 0; i < valueContainersList.size(); i++) {
            List<ValueContainer> result = valueContainersList.get(i);
            for (int j = 0; j < result.size(); j++) {
                int expected = expectedResult[i][j];
                int value = result.get(j).getValue();
                assertEquals(expected, value);
            }
        }
    }

    public void test_perform_left() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[][] input = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };
        int[][] expectedResult = new int[][]{
                {2,4,0,0},
                {8,4,0,0},
                {4,4,0,0},
                {8,2,8,0}
        };
        setValues(valueContainerGrid, input);

        LogicUtil.perform(LogicUtil.Action.LEFT, valueContainerGrid);

        System.out.println("expected");
        System.out.println(Arrays.deepToString(expectedResult));
        System.out.println("actual");
        System.out.println(Arrays.deepToString(gridToArray(valueContainerGrid)));
        List<Integer> nonExpectedNonZeroList = new ArrayList<>();
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int expected = expectedResult[i][j];
                int value = valueContainerGrid[i][j].getValue();
                if (expected > 0) {
                    assertEquals(expected, value);
                } else if (value > 0) {
                    nonExpectedNonZeroList.add(value);
                }
            }
        }
        assertEquals(1, nonExpectedNonZeroList.size());
        for (Integer intValue : nonExpectedNonZeroList) {
            assertTrue(intValue == 2 || intValue == 4);
        }
    }

    private int[][] gridToArray(ValueContainer[][] valueContainerGrid) {
        int[][] arrays = new int[valueContainerGrid.length][];
        for (int i = 0; i < valueContainerGrid.length; i++) {
            ValueContainer[] valueContainersRow = valueContainerGrid[i];
            arrays[i] = new int[valueContainersRow.length];
            for (int j = 0; j < valueContainersRow.length; j++) {
                arrays[i][j] = valueContainersRow[j].getValue();
            }
        }
        return arrays;
    }

    public void test_perform_right() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[][] input = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };
        int[][] expectedResult = new int[][]{
                {0,0,2,4},
                {0,0,8,4},
                {0,0,4,4},
                {0,8,2,8}
        };
        setValues(valueContainerGrid, input);

        LogicUtil.perform(LogicUtil.Action.RIGHT, valueContainerGrid);

        List<Integer> nonExpectedNonZeroList = new ArrayList<>();
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int expected = expectedResult[i][j];
                int value = valueContainerGrid[i][j].getValue();
                if (expected > 0) {
                    assertEquals(expected, value);
                } else if (value > 0) {
                    nonExpectedNonZeroList.add(value);
                }
            }
        }
        assertEquals(1, nonExpectedNonZeroList.size());
        for (Integer intValue : nonExpectedNonZeroList) {
            assertTrue(intValue == 2 || intValue == 4);
        }
    }

    public void test_perform_up() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[][] input = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };
        int[][] expectedResult = new int[][]{
                {2,4,4,4},
                {8,0,4,4},
                {2,0,4,0},
                {8,0,0,0}
        };
        setValues(valueContainerGrid, input);

        LogicUtil.perform(LogicUtil.Action.UP, valueContainerGrid);

        List<Integer> nonExpectedNonZeroList = new ArrayList<>();
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int expected = expectedResult[i][j];
                int value = valueContainerGrid[i][j].getValue();
                if (expected > 0) {
                    assertEquals(expected, value);
                } else if (value > 0) {
                    nonExpectedNonZeroList.add(value);
                }
            }
        }
        assertEquals(1, nonExpectedNonZeroList.size());
        for (Integer intValue : nonExpectedNonZeroList) {
            assertTrue(intValue == 2 || intValue == 4);
        }
    }

    public void test_perform_down() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[][] input = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };
        int[][] expectedResult = new int[][]{
                {2,0,0,0},
                {8,0,4,0},
                {2,0,4,4},
                {8,4,4,4}
        };
        setValues(valueContainerGrid, input);

        LogicUtil.perform(LogicUtil.Action.DOWN, valueContainerGrid);

        List<Integer> nonExpectedNonZeroList = new ArrayList<>();
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int expected = expectedResult[i][j];
                int value = valueContainerGrid[i][j].getValue();
                if (expected > 0) {
                    assertEquals(expected, value);
                } else if (value > 0) {
                    nonExpectedNonZeroList.add(value);
                }
            }
        }
        assertEquals(1, nonExpectedNonZeroList.size());
        for (Integer intValue : nonExpectedNonZeroList) {
            assertTrue(intValue == 2 || intValue == 4);
        }
    }

    private ValueContainer[][] setValues(ValueContainer[][] valueContainerGrid, int[][] values) {
        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                valueContainerGrid[i][j].setValue(values[i][j]);
            }
        }
        return valueContainerGrid;
    }

    private List<ValueContainer> createValueContainerList(int[] values) {
        List<ValueContainer> valueContainerList = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            ValueContainer valueContainer = new ValueContainerImpl();
            valueContainer.setValue(values[i]);
            valueContainerList.add(valueContainer);
        }
        return valueContainerList;
    }

    private ValueContainer[][] createEmptyGrid() {
        ValueContainer[][] valueContainerGrid = new ValueContainer[4][];
        for (int i = 0; i < valueContainerGrid.length; i++) {
            valueContainerGrid[i] = new ValueContainerImpl[4];
            for (int j = 0; j < valueContainerGrid.length; j++) {
                valueContainerGrid[i][j] = new ValueContainerImpl();
            }
        }
        return valueContainerGrid;
    }
}