package com.kebuu.enums;

public enum ValueDeltaMode {
    RAW {
        public double getDelta(double currentValue, double previousValue) {
            return currentValue - previousValue;
        }
    },

    PERCENT {
        public double getDelta(double currentValue, double previousValue) {
            return RAW.getDelta(currentValue, previousValue) / previousValue * 100.0;
        }
    };

    public abstract double getDelta(double currentValue, double previousValue);
}
