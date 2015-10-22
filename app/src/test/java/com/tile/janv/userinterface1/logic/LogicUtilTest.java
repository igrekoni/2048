package com.tile.janv.userinterface1.logic;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * Created by janv on 22-Oct-15.
 */
public class LogicUtilTest {

    @Test
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

    @Test
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
        int[] expectedScore = new int[]{
                0, 0, 4, 32,
                0, 8, 16, 8

        };
        List<List<ValueContainer>> valueContainersList = new ArrayList<>();
        List<Integer> scoreList = new ArrayList<>();
        for (int[] inputArray : input) {
            valueContainersList.add(createValueContainerList(inputArray));
        }

        for (List<ValueContainer> list : valueContainersList) {
            scoreList.add(LogicUtil.squashOnList(list));
        }

        for (int i = 0; i < valueContainersList.size(); i++) {
            List<ValueContainer> result = valueContainersList.get(i);
            for (int j = 0; j < result.size(); j++) {
                int expected = expectedResult[i][j];
                int value = result.get(j).getValue();
                assertEquals(expected, value);
            }
        }
        for (int i = 0; i < scoreList.size();i++) {
            assertEquals(expectedScore[i], scoreList.get(i).intValue());
        }
    }

    @Test
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
        LogicUtil.copyValues(valueContainerGrid, input);
        int scoreExpected = 0+4+8+8;

        int scoreResult = LogicUtil.perform(LogicUtil.Action.LEFT, valueContainerGrid);

//        System.out.println("expected");
//        System.out.println(Arrays.deepToString(expectedResult));
//        System.out.println("actual");
//        System.out.println(Arrays.deepToString(LogicUtil.gridToArray(valueContainerGrid)));
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
        assertEquals(scoreExpected, scoreResult);
    }

    @Test
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
        LogicUtil.copyValues(valueContainerGrid, input);
        int scoreExpected = 0+4+8+8;

        int scoreResult = LogicUtil.perform(LogicUtil.Action.RIGHT, valueContainerGrid);

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
        assertEquals(scoreExpected, scoreResult);
    }

    @Test
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
        LogicUtil.copyValues(valueContainerGrid, input);
        int scoreExpected = 0+4+4+4;

        int scoreResult = LogicUtil.perform(LogicUtil.Action.UP, valueContainerGrid);

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
        assertEquals(scoreExpected, scoreResult);
    }

    @Test
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
        LogicUtil.copyValues(valueContainerGrid, input);
        int scoreExpected = 0+4+4+4;

        int scoreResult = LogicUtil.perform(LogicUtil.Action.DOWN, valueContainerGrid);

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
        assertEquals(scoreExpected, scoreResult);
    }

    @Test
    public void test_copyValues_doubleArray() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[][] input = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };

        LogicUtil.copyValues(valueContainerGrid, input);

        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int expected = input[i][j];
                int value = valueContainerGrid[i][j].getValue();
                assertEquals(expected, value);
            }
        }
    }

    @Test
    public void test_copyValues_singlerray() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[] input = new int[]{
                2,0,4,0,
                8,0,2,2,
                2,2,2,2,
                8,2,4,4
        };
        int[][] expected = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };

        LogicUtil.copyValues(valueContainerGrid, input, 4, 4);

        for (int i = 0; i < valueContainerGrid.length; i++) {
            for (int j = 0; j < valueContainerGrid.length; j++) {
                int expectedValue = expected[i][j];
                int value = valueContainerGrid[i][j].getValue();
                assertEquals(expectedValue, value);
            }
        }
    }

    @Test
    public void test_copyValues_gridToArray() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[][] expected = new int[][]{
                {2,0,4,0},
                {8,0,2,2},
                {2,2,2,2},
                {8,2,4,4}
        };
        LogicUtil.copyValues(valueContainerGrid, expected);

        int[][] result = LogicUtil.gridToArray(valueContainerGrid);
        assertEquals(expected.length, result.length); //row count
        assertEquals(expected[0].length, result[0].length); //row size
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                assertEquals(expected[i][j], result[i][j]);
            }
        }
    }

    @Test
    public void test_copyValues_gridToSingleArray() throws Exception {
        ValueContainer[][] valueContainerGrid = createEmptyGrid();
        int[] expected = new int[]{
                2,0,4,0,
                8,0,2,2,
                2,2,2,2,
                8,2,4,4
        };
        LogicUtil.copyValues(valueContainerGrid, expected, 4, 4);

        int[] result = LogicUtil.gridToSingleArray(valueContainerGrid);
        assertEquals(expected.length, result.length); //row count
        for (int i = 0; i < result.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    //--------------------------------------
    // helper methods
    //--------------------------------------

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