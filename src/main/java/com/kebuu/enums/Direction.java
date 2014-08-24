package com.kebuu.enums;

public enum Direction {
    UP, DOWN, NONE;

    public static Direction fromConsecutiveValues(double value1, double value2) {
        return fromConsecutiveValues(value1, value2, 0);
    }

    public static Direction fromConsecutiveValues(double value1, double value2, double noneUnderThisAbsoluteValue) {
        double difference = value2 - value1;

        if (difference <= -noneUnderThisAbsoluteValue) {
            return DOWN;
        } else if (difference >= noneUnderThisAbsoluteValue) {
            return UP;
        } else {
            return NONE;
        }
    }
}
