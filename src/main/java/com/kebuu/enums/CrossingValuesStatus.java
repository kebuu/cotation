package com.kebuu.enums;

public enum CrossingValuesStatus {
    FIRST_CROSSING_UP, FIRST_CROSSING_DOWN, NOT_CROSSING;

    public static CrossingValuesStatus fromValues(double value1Step1, double value1Step2, double value2Step1, double value2Step2) {
        CrossingValuesStatus result = NOT_CROSSING;

        double currentValuesDelta = value1Step1 - value1Step2;
        double previousValuesDelta = value2Step1 - value2Step2;

        if (areValuesCrossing(currentValuesDelta, previousValuesDelta)) {
            if (currentValuesDelta < previousValuesDelta) {
                result = FIRST_CROSSING_DOWN;
            } else {
                result = FIRST_CROSSING_UP;
            }
        }

        return result;
    }

    private static boolean areValuesCrossing(Double currentValuesDelta, Double previousValuesDelta) {
        return Math.signum(currentValuesDelta) != Math.signum(previousValuesDelta);
    }
}
