package com.kebuu.enums;

public enum ValueDirectionMode {
    RAW {
        public Direction getDirection(double currentValue, double previousValue, double noDirectionUnderThisAbsoluteValue) {
            return  Direction.fromConsecutiveValues(currentValue, previousValue, noDirectionUnderThisAbsoluteValue);
        }

        public Direction getDirection(double currentValue, double previousValue) {
            return getDirection(currentValue, previousValue, 0);
        }
    },

    PERCENT {
        public Direction getDirection(double currentValue, double previousValue, double noDirectionUnderThisAbsoluteValue) {
            return Direction.fromDelta(ValueDeltaMode.PERCENT.getDelta(currentValue, previousValue), noDirectionUnderThisAbsoluteValue);
        }

        public Direction getDirection(double currentValue, double previousValue) {
            return getDirection(currentValue, previousValue, 0);
        }
    };

    public abstract Direction getDirection(double currentValue, double previousValue, double noDirectionUnderThisAbsoluteValue);
    public abstract Direction getDirection(double currentValue, double previousValue);
}
