ALTER TABLE enhanced_cotation ADD COLUMN (cotation_position integer);
UPDATE enhanced_cotation cotation SET cotation_position = SELECT position FROM (
        SELECT date, rownum() as position from enhanced_cotation order by date
    ) positions WHERE positions.date = cotation.date;

ALTER TABLE enhanced_cotation ADD COLUMN (slipping_mean_20_position varchar);
UPDATE enhanced_cotation SET slipping_mean_20_position = CASE
    WHEN slipping_mean_20 >= END THEN 'COTATION_LOWER'
    ELSE 'COTATION_UPPER'
END;

ALTER TABLE enhanced_cotation ADD COLUMN (slipping_mean_20_yesterday_delta varchar);
UPDATE enhanced_cotation actual SET slipping_mean_20_yesterday_delta = actual.slipping_mean_20 - (SELECT previous.slipping_mean_20 from enhanced_cotation previous where previous.date < actual.date order by previous.date desc limit 1);

ALTER TABLE enhanced_cotation ADD COLUMN (slipping_mean_20_yesterday_delta_percent varchar);
UPDATE enhanced_cotation SET slipping_mean_20_yesterday_delta_percent = slipping_mean_20_yesterday_delta / slipping_mean_20;

ALTER TABLE enhanced_cotation ADD COLUMN (slipping_mean_20_direction varchar);
UPDATE enhanced_cotation SET slipping_mean_20_direction = CASE
    WHEN slipping_mean_20_yesterday_delta_percent > 0.05 THEN 'RAISING'
    WHEN slipping_mean_20_yesterday_delta_percent < -0.05 THEN 'DESCENDING'
    ELSE 'NONE'
END;