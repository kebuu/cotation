package com.kebuu.config;

import com.kebuu.builder.impl.*;
import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
import com.kebuu.builder.impl.relation.ValuesEnumPositionBuilder;
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

        SimpleMobileMeanBuilder simpleMobileMeanBuilder20 = new SimpleMobileMeanBuilder(20);
        SimpleMobileMeanBuilder simpleMobileMeanBuilder50 = new SimpleMobileMeanBuilder(50);

        ValueDirectionBuilder mobileMeanBuilder20Direction = new ValueDirectionBuilder(simpleMobileMeanBuilder20.getAttribute());
        ValuesEnumPositionBuilder mobileMeanBuilder20Position = new ValuesEnumPositionBuilder(simpleMobileMeanBuilder20.getAttribute(), endInfoBuilder.getSingleAttribute());

        MobileMeansCrossingBuilder mobileMeansCrossBuilder2050 = new MobileMeansCrossingBuilder(simpleMobileMeanBuilder20, simpleMobileMeanBuilder50);

        StochasticBuilder stochasticBuilder = new StochasticBuilder();
        RocBuilder rocBuilder = new RocBuilder();
        MacdBuilder macdBuilder = new MacdBuilder(1, 2);

        NextDaysEndDirectionBuilder nextDaysEndDirectionBuilder = new NextDaysEndDirectionBuilder(1);

        return new CompositeCotationBuilder(yearInfoBuilder, monthInfoBuilder, dayOfMonthInfoBuilder, dayOfWeekInfoBuilder, endInfoBuilder,
            simpleMobileMeanBuilder20, mobileMeanBuilder20Direction, mobileMeanBuilder20Position, simpleMobileMeanBuilder50, mobileMeansCrossBuilder2050,
            nextDaysEndDirectionBuilder, stochasticBuilder, rocBuilder, macdBuilder);
    }
}
