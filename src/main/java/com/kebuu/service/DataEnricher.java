package com.kebuu.service;

public interface DataEnricher {

    void addFloatingMean(int slippingMeanDay);

    void addDirectionInfo();

    void addEndDailyDelta();

    void addIntraDayDeltas();
}
