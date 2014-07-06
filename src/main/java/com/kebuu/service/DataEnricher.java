package com.kebuu.service;

public interface DataEnricher {

    void addFloatingMean(int floatingMeanDay);

    void addEndDailyDelta(int floatingMeanDay);
}
