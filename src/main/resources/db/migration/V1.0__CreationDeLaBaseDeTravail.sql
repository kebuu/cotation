CREATE TABLE enhanced_cotation (
	id BIGINT NOT NULL PRIMARY KEY,
	date TIMESTAMP,
	end DOUBLE NOT NULL,
	max DOUBLE NOT NULL,
	min DOUBLE NOT NULL,
	start DOUBLE NOT NULL,
	volume BIGINT
);

INSERT INTO enhanced_cotation (id, date, end, max, min, start, volume)
SELECT id, date, end, max, min, start, volume FROM cotation;

CREATE UNIQUE INDEX ON enhanced_cotation(date);