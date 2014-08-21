package com.kebuu.utils;

import com.kebuu.constant.Constant;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public final class DecimalFormatUtils {

    private static final DecimalFormat DEFAULT_DECIMAL_FORMAT;

    static {
        DEFAULT_DECIMAL_FORMAT = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        DEFAULT_DECIMAL_FORMAT.applyPattern(Constant.DEFAULT_DECIMAL_FORMAT_PATTERN);
        DEFAULT_DECIMAL_FORMAT.setRoundingMode(RoundingMode.HALF_DOWN);
    }

    private DecimalFormatUtils() { }

    public static DecimalFormat getDefault() {
        return (DecimalFormat) DEFAULT_DECIMAL_FORMAT.clone();
    }
}
