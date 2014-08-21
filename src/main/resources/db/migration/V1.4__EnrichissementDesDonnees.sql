ALTER TABLE enhanced_cotation ADD COLUMN (direction_in_two_day integer);
ALTER TABLE enhanced_cotation ADD COLUMN (direction_in_three_day integer);
ALTER TABLE enhanced_cotation ADD COLUMN (direction_in_five_day integer);

UPDATE enhanced_cotation cotation SET direction_in_two_day = CASE
     WHEN DAILY_END_DELTA_PERCENT > (0.75 / 100) THEN 'UP'
     WHEN DAILY_END_DELTA_PERCENT < (-0.75 / 100) THEN 'DOWN'
     ELSE 'NONE'
 END;
