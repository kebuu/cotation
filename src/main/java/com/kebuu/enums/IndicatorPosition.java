package com.kebuu.enums;

public enum IndicatorPosition {
    COTATION_UPPER, COTATION_LOWER, SAME_AS_COTATION;

    public static IndicatorPosition basedOn(double cotationValue, double indicatorValue) {
        if (cotationValue > indicatorValue) {
            return COTATION_UPPER;
        } else if(cotationValue < indicatorValue) {
            return COTATION_LOWER;
        } else {
            return SAME_AS_COTATION;
        }
    }
}
