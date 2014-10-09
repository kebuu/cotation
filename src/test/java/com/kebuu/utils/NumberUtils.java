package com.kebuu.utils;

import com.google.common.base.Preconditions;

public class NumberUtils {

    public static double round(double value, int nbOfDecimal) {
        Preconditions.checkArgument(nbOfDecimal >= 0, "nbOfDecimal must be positive or null");
        double multiplier = Math.pow(10.0, Integer.valueOf(nbOfDecimal).doubleValue());
        return Math.round(value * multiplier) / multiplier;
    }
}
