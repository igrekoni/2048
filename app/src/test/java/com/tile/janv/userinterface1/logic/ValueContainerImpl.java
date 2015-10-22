package com.tile.janv.userinterface1.logic;

/**
 * Created by janv on 22-Oct-15.
 */
public class ValueContainerImpl implements ValueContainer {

    private int value = 0;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
