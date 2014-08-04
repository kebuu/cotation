-- INTER DAY DELTA
ALTER TABLE working_cotation ADD COLUMN (daily_end_delta double);
UPDATE working_cotation actual SET daily_end_delta = end - (SELECT previous.end from working_cotation previous where previous.date < actual.date order by previous.date desc limit 1);

ALTER TABLE working_cotation ADD COLUMN (daily_end_delta_percent double);
UPDATE working_cotation SET daily_end_delta_percent = daily_end_delta / (end  - daily_end_delta);

-- INTRA DAY DELTA
ALTER TABLE working_cotation ADD COLUMN (intra_day_delta double);
UPDATE working_cotation SET intra_day_delta = end - start;

ALTER TABLE working_cotation ADD COLUMN (intra_day_max_minus_min double);
UPDATE working_cotation SET intra_day_max_minus_min = max - min;

-- DIRECTION
ALTER TABLE working_cotation ADD COLUMN (direction varchar);
UPDATE working_cotation SET direction = CASE WHEN END - START > 0 THEN 'UP' ELSE 'DOWN' END;

ALTER TABLE working_cotation ADD COLUMN (same_direction_consecutive_count integer);
UPDATE working_cotation cotation SET same_direction_consecutive_count = (
    SELECT count(*) FROM working_cotation cotation2 WHERE cotation2.date < cotation.date AND cotation2.date > (
        SELECT cotation3.date FROM working_cotation cotation3 WHERE cotation3.date < cotation.date AND cotation.direction != cotation3.direction ORDER BY cotation3.date DESC LIMIT 1
    )
);


-- MOYENNE MOBILE
CREATE OR REPLACE VIEW V_SLIPPING_MEAN_20 AS
    SELECT cotation.date, AVG(pastCotation.end) as mean
    FROM COTATION cotation
    JOIN COTATION pastCotation
        ON pastCotation.date in (SELECT subCotation.date FROM COTATION subCotation WHERE subCotation.date < cotation.date ORDER BY subCotation.date DESC LIMIT 20)
    WHERE cotation.date not in (SELECT excludeCotation.date FROM COTATION excludeCotation ORDER BY excludeCotation.date ASC LIMIT 20)
    GROUP BY cotation.date
;

ALTER TABLE working_cotation ADD COLUMN (slipping_mean_20 double);
UPDATE working_cotation SET SLIPPING_MEAN_20 = (SELECT V_SLIPPING_MEAN_20.mean FROM V_SLIPPING_MEAN_20 WHERE V_SLIPPING_MEAN_20.date = working_cotation.date);

ALTER TABLE working_cotation ADD COLUMN (slipping_mean_20_delta double);
UPDATE working_cotation SET SLIPPING_MEAN_20_DELTA = slipping_mean_20 - END;

-- TIME SERIES
CREATE OR REPLACE VIEW V_TIME_SERIES_20 AS SELECT cotation.date AS baseDate, pastCotation.*
    FROM COTATION cotation
    JOIN COTATION pastCotation
        ON pastCotation.date in (SELECT subCotation.date FROM COTATION subCotation WHERE subCotation.date <= cotation.date ORDER BY subCotation.date DESC LIMIT 20)
    WHERE cotation.date not in (SELECT excludeCotation.date FROM COTATION excludeCotation ORDER BY excludeCotation.date ASC LIMIT 20);