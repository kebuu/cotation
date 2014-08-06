package com.kebuu.config;

import com.kebuu.constant.Constant;
import com.kebuu.domain.EnhancedCotation;
import com.kebuu.dto.ArffAttribute;
import com.kebuu.dto.ArffAttributes;
import org.apache.commons.lang3.time.DateFormatUtils;
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
        ArffAttributes<EnhancedCotation> enhancedCotationAttributes = new ArffAttributes<>(
            new ArffAttribute<EnhancedCotation>("date", "date " + Constant.YYYY_MM_DD, (cotation -> DateFormatUtils.ISO_DATE_FORMAT.format(cotation.getDate()))),
            new ArffAttribute<EnhancedCotation>("start", "real", EnhancedCotation::getStart),
            new ArffAttribute<EnhancedCotation>("end", "real", EnhancedCotation::getEnd),
            new ArffAttribute<EnhancedCotation>("min", "real", EnhancedCotation::getMin),
            new ArffAttribute<EnhancedCotation>("max", "real", EnhancedCotation::getMax),
            new ArffAttribute<EnhancedCotation>("volume", "integer", EnhancedCotation::getVolume),
            new ArffAttribute<EnhancedCotation>("year", "integer", EnhancedCotation::getYear),
            new ArffAttribute<EnhancedCotation>("month", "integer", EnhancedCotation::getMonth),
            new ArffAttribute<EnhancedCotation>("dayOfMonth", "integer", EnhancedCotation::getDayOfMonth),
            new ArffAttribute<EnhancedCotation>("dayOfWeek", "integer", EnhancedCotation::getDayOfWeek)
        );

        return enhancedCotationAttributes;
    }
}
