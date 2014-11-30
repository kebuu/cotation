package com.kebuu.enums;


import com.google.common.collect.Range;
import org.apache.commons.lang3.math.NumberUtils;

public enum CrossingValuesStatus {
    FIRST_CROSSING_UP,
    FIRST_CROSSING_UP_YESTERDAY,
    FIRST_CROSSING_UP_BEFORE_YESTERDAY,
    FIRST_MAY_CROSS_UP_TOMORROW,
    FIRST_MAY_CROSS_UP_AFTER_TOMORROW,
    FIRST_CROSSING_DOWN,
    FIRST_CROSSING_DOWN_YESTERDAY,
    FIRST_CROSSING_DOWN_BEFORE_YESTERDAY,
    FIRST_MAY_CROSS_DOWN_TOMORROW,
    FIRST_MAY_CROSS_DOWN_AFTER_TOMORROW,
    NOT_CROSSING;

    public static CrossingValuesStatus fromValues(double value1Step1, double value1Step2, double value2Step1, double value2Step2) {
        CrossingValuesStatus result = NOT_CROSSING;

        double currentValuesDelta = value1Step2 - value2Step2;
        double previousValuesDelta = value1Step1 - value2Step1;
        double denominatorForIntersectionCalculus = value1Step2 + value2Step1 - value1Step1 - value2Step2;

        if (areValuesCrossing(currentValuesDelta, previousValuesDelta)) {
            if (currentValuesDelta < previousValuesDelta) {
                result = FIRST_CROSSING_DOWN;
            } else {
                result = FIRST_CROSSING_UP;
            }
        } else if (denominatorForIntersectionCalculus != NumberUtils.DOUBLE_ZERO) {
            double intersectionPoint = (value2Step1 - value1Step1) / denominatorForIntersectionCalculus;
            boolean isValue1HigherThanValue2 = value1Step2 > value2Step2;

            if (Range.openClosed(1.0, 2.0).contains(intersectionPoint)) {
                if (isValue1HigherThanValue2) {
                    result = FIRST_MAY_CROSS_DOWN_TOMORROW;
                } else {
                    result = FIRST_MAY_CROSS_UP_TOMORROW;
                }
            } else if (Range.openClosed(2.0, 3.0).contains(intersectionPoint)){
                if (isValue1HigherThanValue2) {
                    result = FIRST_MAY_CROSS_DOWN_AFTER_TOMORROW;
                } else {
                    result = FIRST_MAY_CROSS_UP_AFTER_TOMORROW;
                }
            }
        }

        return result;
    }

    private static boolean areValuesCrossing(Double currentValuesDelta, Double previousValuesDelta) {
        return Math.signum(currentValuesDelta) != Math.signum(previousValuesDelta);
    }
}
