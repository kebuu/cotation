package com.kebuu.config;

import com.kebuu.builder.impl.CompositeCotationBuilder;
import com.kebuu.builder.impl.MacdBuilder;
import com.kebuu.builder.impl.RocBuilder;
import com.kebuu.builder.impl.StochasticBuilder;
import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
import com.kebuu.builder.impl.relation.*;
import com.kebuu.builder.impl.simple.*;
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
        flyway.repair();
        flyway.migrate();
        return flyway;
    }

    @Bean
    public CompositeCotationBuilder cotationBuilders() {
        YearInfoValueBuilder yearInfoBuilder = new YearInfoValueBuilder();
        MonthInfoBuilder monthInfoBuilder = new MonthInfoBuilder();
        DayOfMonthInfoBuilder dayOfMonthInfoBuilder = new DayOfMonthInfoBuilder();
        DayOfWeekInfoBuilder dayOfWeekInfoBuilder = new DayOfWeekInfoBuilder();
        EndInfoBuilder endInfoBuilder = new EndInfoBuilder();

        SimpleMobileMeanBuilder simpleMobileMeanBuilder7 = new SimpleMobileMeanBuilder(7);
        SimpleMobileMeanBuilder simpleMobileMeanBuilder20 = new SimpleMobileMeanBuilder(20);
        SimpleMobileMeanBuilder simpleMobileMeanBuilder50 = new SimpleMobileMeanBuilder(50);

        ValueDirectionBuilder mobileMeanBuilder20Direction = new ValueDirectionBuilder(simpleMobileMeanBuilder20.attribute());
        ValuesPositionBuilder mobileMeanBuilder20Position = new ValuesPositionBuilder(simpleMobileMeanBuilder20.attribute(), endInfoBuilder.attribute());
        ValueDirectionBuilder mobileMeanBuilder7Direction = new ValueDirectionBuilder(simpleMobileMeanBuilder7.attribute());
        ValuesPositionBuilder mobileMeanBuilder7Position = new ValuesPositionBuilder(simpleMobileMeanBuilder7.attribute(), endInfoBuilder.attribute());

        ValuesCrossingBuilder mobileMeansCrossBuilder2050 = new ValuesCrossingBuilder(simpleMobileMeanBuilder20.attribute(), simpleMobileMeanBuilder50.attribute());
        ValuesCrossingBuilder mobileMeansCrossBuilder720 = new ValuesCrossingBuilder(simpleMobileMeanBuilder7.attribute(), simpleMobileMeanBuilder20.attribute());

        StochasticBuilder stochasticBuilder = new StochasticBuilder();
        SimpleMobileMeanBuilder stochasticSignalBuilder = new SimpleMobileMeanBuilder(14, stochasticBuilder.attribute());

        RocBuilder rocBuilder = new RocBuilder();
        MacdBuilder macdBuilder = new MacdBuilder();

        ValueDirectionBuilder nextDaysEndDirectionBuilder1 = new ValueDirectionBuilder(endInfoBuilder.attribute(), 1);
        ValueDirectionBuilder nextDaysEndDirectionBuilder5 = new ValueDirectionBuilder(endInfoBuilder.attribute(), 5);

        return new CompositeCotationBuilder(yearInfoBuilder, monthInfoBuilder, dayOfMonthInfoBuilder, dayOfWeekInfoBuilder, endInfoBuilder,
            simpleMobileMeanBuilder20, mobileMeanBuilder20Direction, mobileMeanBuilder20Position, simpleMobileMeanBuilder50,
            nextDaysEndDirectionBuilder1, stochasticBuilder, rocBuilder, macdBuilder, mobileMeansCrossBuilder2050);
    }
}
