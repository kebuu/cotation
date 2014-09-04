package com.kebuu.config;

import com.kebuu.builder.impl.*;
import com.kebuu.builder.impl.relation.ValuesPositionBuilder;
import com.kebuu.builder.impl.simple.DayOfWeekInfoBuilder;
import com.kebuu.builder.impl.simple.EndInfoBuilder;
import com.kebuu.builder.impl.simple.MonthInfoBuilder;
import com.kebuu.builder.impl.simple.YearInfoBuilder;
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
        YearInfoBuilder yearInfoBuilder = new YearInfoBuilder();
        MonthInfoBuilder monthInfoBuilder = new MonthInfoBuilder();
        DayOfWeekInfoBuilder dayOfWeekInfoBuilder = new DayOfWeekInfoBuilder();
        EndInfoBuilder endInfoBuilder = new EndInfoBuilder();

        MobileMeanBuilder mobileMeanBuilder20 = new MobileMeanBuilder(20);
        MobileMeanBuilder mobileMeanBuilder50 = new MobileMeanBuilder(50);

        ValueDirectionBuilder mobileMeanBuilder20Direction = new ValueDirectionBuilder(mobileMeanBuilder20.getMobileMeanValueAttribute());
        ValuesPositionBuilder mobileMeanBuilder20Position = new ValuesPositionBuilder(mobileMeanBuilder20.getMobileMeanValueAttribute(), endInfoBuilder.getAttribute());

        MobileMeansCrossingBuilder mobileMeansCrossBuilder2050 = new MobileMeansCrossingBuilder(mobileMeanBuilder20, mobileMeanBuilder50);

        StochasticBuilder stochasticBuilder = new StochasticBuilder();
        RocBuilder rocBuilder = new RocBuilder();
        MacdBuilder macdBuilder = new MacdBuilder();

        NextDaysEndDirectionBuilder nextDaysEndDirectionBuilder = new NextDaysEndDirectionBuilder(1);

        return new CompositeCotationBuilder(yearInfoBuilder, monthInfoBuilder, dayOfWeekInfoBuilder, endInfoBuilder,
                mobileMeanBuilder20, mobileMeanBuilder20Direction, mobileMeanBuilder20Position, mobileMeanBuilder50, mobileMeansCrossBuilder2050,
                nextDaysEndDirectionBuilder, stochasticBuilder, rocBuilder, macdBuilder);
    }
}
