package com.kebuu.enums;

public enum ValueDeltaMode {
    RAW {
        public double getDelta(double value1, double value2) {
            return value2 - value1;
        }
    },

    PERCENT {
        public double getDelta(double value1, double value2) {
            return RAW.getDelta(value1, value2) / value1 * 100.0;
        }
    };

    public abstract double getDelta(double value1, double value2);
}
