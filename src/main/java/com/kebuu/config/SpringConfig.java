package com.kebuu.config;

import com.kebuu.builder.impl.*;
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
        //flyway.repair();
        //flyway.migrate();
        return flyway;
    }

    @Bean
    public MultiCotationBuilder cotationBuilders() {
        MobileMeanBuilder mobileMeanBuilder20 = new MobileMeanBuilder(20);
        MobileMeanBuilder mobileMeanBuilder50 = new MobileMeanBuilder(50);
        MobileMeansCrossingBuilder mobileMeansCrossBuilder2050 = new MobileMeansCrossingBuilder(mobileMeanBuilder20, mobileMeanBuilder50);
        NextDaysEndDirectionBuilder nextDaysEndDirectionBuilder = new NextDaysEndDirectionBuilder(1);
        StochasticBuilder stochasticBuilder = new StochasticBuilder();
        RocBuilder rocBuilder = new RocBuilder();

        return new MultiCotationBuilder(mobileMeanBuilder20, mobileMeanBuilder50, mobileMeansCrossBuilder2050,
                nextDaysEndDirectionBuilder, stochasticBuilder, rocBuilder);
    }
}
