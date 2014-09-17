package com.kebuu.enums;

public enum ValueComparisonPosition {
    FIRST_VALUE_UPPER, FIRST_VALUE_LOWER, EQUAL_VALUES;

    public static ValueComparisonPosition from(double value1, double value2) {
        return from(value1, value2, 0);
    }

    public static ValueComparisonPosition from(double value1, double value2, double equalUnderThisAbsoluteValue) {
        double difference = value2 - value1;

        if (difference <= -equalUnderThisAbsoluteValue && difference != 0) {
            return FIRST_VALUE_LOWER;
        } else if (difference >= equalUnderThisAbsoluteValue && difference != 0) {
            return FIRST_VALUE_UPPER;
        } else {
            return EQUAL_VALUES;
        }
    }
}
