package com.kebuu.enums;

public enum OverBuyOrSellStatus {
    OVER_BUY, OVER_SELL, NOTHING;

    public static OverBuyOrSellStatus fromThresholds(double value, double overBuyThreshold, double overSellThreshold) {
        OverBuyOrSellStatus overBuyOrSellStatusValue = OverBuyOrSellStatus.NOTHING;

        if(value >= overBuyThreshold) {
            overBuyOrSellStatusValue = OverBuyOrSellStatus.OVER_BUY;
        } else if(value <= overSellThreshold) {
            overBuyOrSellStatusValue = OverBuyOrSellStatus.OVER_SELL;
        }

        return overBuyOrSellStatusValue;
    }
}
