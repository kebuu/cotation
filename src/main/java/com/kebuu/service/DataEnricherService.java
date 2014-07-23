package com.kebuu.service;

public interface DataEnricherService {

    void addFloatingMean(int slippingMeanDay);

    void addDirectionInfo();

    void addEndDailyDelta();

    void addIntraDayDeltas();

    void createTimeSeriesView(int timeSeriesLength);
}
