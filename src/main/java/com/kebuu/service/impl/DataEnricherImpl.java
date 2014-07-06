package com.kebuu.service.impl;

import com.kebuu.service.DataEnricher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataEnricherImpl implements DataEnricher{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addFloatingMean(int floatingMeanDay) {

    }

    @Override
    public void addEndDailyDelta(int floatingMeanDay) {

    }
}
