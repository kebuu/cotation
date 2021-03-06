package com.kebuu.enums;

public enum Direction {
    UP, DOWN, NONE;

    public static Direction fromDelta(double value) {
        return fromDelta(value, 0);
    }

    public static Direction fromDelta(double value, double noneUnderThisAbsoluteValue) {
        return fromConsecutiveValues(0, value, noneUnderThisAbsoluteValue);
    }

    public static Direction fromConsecutiveValues(double value1, double value2) {
        return fromConsecutiveValues(value1, value2, 0);
    }

    public static Direction fromConsecutiveValues(double value1, double value2, double noneUnderThisAbsoluteValue) {
        double difference = value2 - value1;

        if (difference <= -noneUnderThisAbsoluteValue && difference != 0.0) {
            return DOWN;
        } else if (difference >= noneUnderThisAbsoluteValue && difference != 0.0) {
            return UP;
        } else {
            return NONE;
        }
    }
}
