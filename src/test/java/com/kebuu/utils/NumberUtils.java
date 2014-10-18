package com.kebuu.utils;

import com.google.common.base.Preconditions;
import org.assertj.core.api.Assertions;

public class NumberUtils {

    public static double round(double value, int nbOfDecimal) {
        Preconditions.checkArgument(nbOfDecimal >= 0, "nbOfDecimal must be positive or null");
        double multiplier = Math.pow(10.0, Integer.valueOf(nbOfDecimal).doubleValue());
        return Math.round(value * multiplier) / multiplier;
    }

    public static void assertEquals(double currentValue, double expectedValue, int nbOfDecimal) {
        Assertions.assertThat(round(currentValue, nbOfDecimal)).isEqualTo(round(expectedValue, nbOfDecimal));
    }
}
