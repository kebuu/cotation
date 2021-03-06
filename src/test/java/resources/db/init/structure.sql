CREATE TABLE PUBLIC.COTATION (
	ID serial,
	DATE TIMESTAMP NOT NULL,
	END DOUBLE NOT NULL,
	MAX DOUBLE NOT NULL,
	MIN DOUBLE NOT NULL,
	START DOUBLE NOT NULL,
	VOLUME BIGINT,
	POSITION INTEGER NOT NULL,
	CODE VARCHAR(2147483647) NOT NULL
);

CREATE UNIQUE INDEX UK_COTATION_POSITION ON PUBLIC.COTATION (POSITION);
CREATE UNIQUE INDEX UK_COTATION_DATE ON PUBLIC.COTATION (DATE);
