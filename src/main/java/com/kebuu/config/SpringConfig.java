package com.kebuu.config;

import com.kebuu.constant.Constant;
import com.kebuu.dto.ArffAttribute;
import com.kebuu.dto.ArffAttributes;
import com.kebuu.domain.Cotation;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    @Autowired private DataSource dataSource;

    @Bean
    public Flyway flyway() {
        Flyway flyway = new Flyway();
        flyway.setDataSource(dataSource);
        flyway.setInitOnMigrate(true);
        flyway.setInitVersion("0");
        flyway.migrate();
        return flyway;
    }

    @Bean
    public ArffAttributes enhancedCotationAttributes() {
        ArffAttributes enhancedCotationAttributes = new ArffAttributes(
            new ArffAttribute<Cotation>("date", "date[" + Constant.YYYY_MM_DD + "]", Cotation::getDate),
            new ArffAttribute<Cotation>("start", "real", Cotation::getStart),
            new ArffAttribute<Cotation>("end", "real", Cotation::getEnd),
            new ArffAttribute<Cotation>("min", "real", Cotation::getMin),
            new ArffAttribute<Cotation>("max", "real", Cotation::getMax),
            new ArffAttribute<Cotation>("volume", "integer", Cotation::getVolume)
        );
        return enhancedCotationAttributes;
    }
}
