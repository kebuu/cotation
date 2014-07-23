package com.kebuu.service.impl;

import com.kebuu.service.DataEnricherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class DataEnricherServiceImpl implements DataEnricherService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addFloatingMean(int slippingMeanDay) {
        jdbcTemplate.execute("CREATE OR REPLACE VIEW V_SLIPPING_MEAN_" + slippingMeanDay + " AS SELECT cotation.date, AVG(pastCotation.end) " +
            "FROM COTATION cotation " +
            "JOIN COTATION pastCotation " +
            "ON pastCotation.date in (SELECT subCotation.date FROM COTATION subCotation WHERE subCotation.date < cotation.date ORDER BY subCotation.date DESC LIMIT " + slippingMeanDay + ") " +
            "WHERE cotation.date not in (SELECT excludeCotation.date FROM COTATION excludeCotation ORDER BY excludeCotation.date ASC LIMIT " + slippingMeanDay +") " +
            "GROUP BY cotation.date"
        );

        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (slipping_mean_" + slippingMeanDay + " double");
        jdbcTemplate.execute("UPDATE COTATION SET SLIPPING_MEAN_" + slippingMeanDay + " = (SELECT V_SLIPPING_MEAN_" + slippingMeanDay + ".mean_" + slippingMeanDay + " FROM V_SLIPPING_MEAN_" + slippingMeanDay + " WHERE V_SLIPPING_MEAN_" + slippingMeanDay + ".date = COTATION.date)");

        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (slipping_mean_" + slippingMeanDay + "_delta double");
        jdbcTemplate.execute("UPDATE COTATION SET SLIPPING_MEAN_" + slippingMeanDay + "_DELTA = slipping_mean_" + slippingMeanDay + " - END");
    }

    @Override
    public void addDirectionInfo() {
        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (direction double)");
        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (same_direction_consecutive_count integer)");

        jdbcTemplate.execute("UPDATE COTATION SET DIRECTION = CASE WHEN END - START > 0 THEN 'UP' ELSE 'DOWN' END");
        jdbcTemplate.execute("UPDATE COTATION cotation SET same_direction_consecutive_count = (" +
                "SELECT count(*) FROM COTATION cotation2 WHERE cotation2.date < cotation.date AND cotation2.date > (" +
                    "SELECT cotation3.date FROM COTATION cotation3 WHERE cotation3.date < cotation.date AND cotation.direction != cotation3.direction ORDER BY cotation3.date DESC LIMIT 1" +
                ")" +
            ")");
    }

    @Override
    public void addEndDailyDelta() {
        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (daily_end_delta double)");
        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (daily_end_delta_percent double)");

        jdbcTemplate.execute("UPDATE COTATION actual SET daily_end_delta = end - (SELECT previous.end from COTATION previous where previous.date < actual.date order by previous.date desc limit 1)");
        jdbcTemplate.execute("UPDATE COTATION SET daily_end_delta_percent = daily_end_delta / (end  - daily_end_delta)");
    }

    @Override
    public void addIntraDayDeltas() {
        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (intra_day_delta double)");
        jdbcTemplate.execute("ALTER TABLE COTATION ADD COLUMN (intra_day_max_minus_min double)");

        jdbcTemplate.execute("UPDATE COTATION SET intra_day_delta = end - start");
        jdbcTemplate.execute("UPDATE COTATION SET intra_day_max_minus_min = max - min");
    }

    @Override
    public void createTimeSeriesView(int timeSeriesLength) {
        jdbcTemplate.execute("CREATE OR REPLACE VIEW V_TIME_SERIES_" + timeSeriesLength + " AS SELECT cotation.date AS baseDate, pastCotation.* " +
            "FROM COTATION cotation " +
            "JOIN COTATION pastCotation " +
            "ON pastCotation.date in (SELECT subCotation.date FROM COTATION subCotation WHERE subCotation.date < cotation.date ORDER BY subCotation.date DESC LIMIT " + timeSeriesLength + ") " +
            "WHERE cotation.date not in (SELECT excludeCotation.date FROM COTATION excludeCotation ORDER BY excludeCotation.date ASC LIMIT " + timeSeriesLength +") "
        );
    }
}
