package com.kebuu.config;

import com.kebuu.builder.impl.*;
import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
import com.kebuu.builder.impl.mobilemean.SimplifiedExponentialMobileMeanBuilder;
import com.kebuu.builder.impl.relation.OverBuyOrSellBuilder;
import com.kebuu.builder.impl.relation.ValuesCrossingBuilder;
import com.kebuu.builder.impl.relation.ValuesPositionBuilder;
import com.kebuu.builder.impl.simple.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private static final double directionThreshold = 0.0;

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

        ValuesCrossingBuilder mobileMeansCrossBuilder20_50 = new ValuesCrossingBuilder(simpleMobileMeanBuilder20.attribute(), simpleMobileMeanBuilder50.attribute());
        ValuesCrossingBuilder mobileMeansCrossBuilder7_20 = new ValuesCrossingBuilder(simpleMobileMeanBuilder7.attribute(), simpleMobileMeanBuilder20.attribute());

        StochasticBuilder stochasticBuilder = new StochasticBuilder();
        SimpleMobileMeanBuilder stochasticSignalBuilder = new SimpleMobileMeanBuilder(5, stochasticBuilder.attribute());
        ValuesCrossingBuilder stochasticSignalCrossing = new ValuesCrossingBuilder(stochasticBuilder.attribute(), stochasticSignalBuilder.attribute());
        OverBuyOrSellBuilder stochasticOverBuyOrSell = new OverBuyOrSellBuilder(stochasticBuilder.attribute(), 80D, 20D);
        ValueDirectionBuilder stochasticDirection = new ValueDirectionBuilder(stochasticBuilder.attribute());
        ValuesPositionBuilder stochasticValueAndSignalPosition = new ValuesPositionBuilder(stochasticBuilder.attribute(), stochasticSignalBuilder.attribute());

        RocBuilder rocBuilder = new RocBuilder();
        OverBuyOrSellBuilder rocOverBuyOrSell = new OverBuyOrSellBuilder(rocBuilder.attribute(), 10D, -10D);

        MacdBuilder macdBuilder = new MacdBuilder();
        SimplifiedExponentialMobileMeanBuilder macdSignalBuilder = new SimplifiedExponentialMobileMeanBuilder(9, macdBuilder.getAttribute());
        ValuesCrossingBuilder macdSignalCrossing = new ValuesCrossingBuilder(macdBuilder.getAttribute(), macdSignalBuilder.attribute());
        ValueDirectionBuilder macdDirection = new ValueDirectionBuilder(macdBuilder.getAttribute());
        ValuesPositionBuilder macdValueAndSignalPosition = new ValuesPositionBuilder(macdBuilder.getAttribute(), macdSignalBuilder.attribute());

        ChaikinMoneyFlowBuilder chaikinMoneyFlowBuilder = new ChaikinMoneyFlowBuilder();
        ValueDirectionBuilder chaikinMoneyFlowDirection = new ValueDirectionBuilder(chaikinMoneyFlowBuilder.attribute());
        ValuesPositionBuilder chaikinMoneyFlowPosition = new ValuesPositionBuilder(chaikinMoneyFlowBuilder.attribute(), NumberUtils.DOUBLE_ZERO);
        ValuesCrossingBuilder chaikinMoneyFlowCrossing = new ValuesCrossingBuilder(chaikinMoneyFlowBuilder.attribute(), NumberUtils.DOUBLE_ZERO);

        EaseOfMovementBuilder easeOfMovementBuilder = new EaseOfMovementBuilder();
        ValueDirectionBuilder easeOfMovementDirection = new ValueDirectionBuilder(easeOfMovementBuilder.attribute());
        ValuesPositionBuilder easeOfMovementPosition = new ValuesPositionBuilder(easeOfMovementBuilder.attribute(), NumberUtils.DOUBLE_ZERO);
        ValuesCrossingBuilder easeOfMovementCrossing = new ValuesCrossingBuilder(easeOfMovementBuilder.attribute(), NumberUtils.DOUBLE_ZERO);

        ValueDirectionBuilder nextDaysEndDirectionBuilder1 = new ValueDirectionBuilder(endInfoBuilder.attribute(), 1, directionThreshold);
        ValueDirectionBuilder nextDaysEndDirectionBuilder2 = new ValueDirectionBuilder(endInfoBuilder.attribute(), 2, directionThreshold);
        ValueDirectionBuilder nextDaysEndDirectionBuilder3 = new ValueDirectionBuilder(endInfoBuilder.attribute(), 3, directionThreshold);
        ValueDirectionBuilder nextDaysEndDirectionBuilder5 = new ValueDirectionBuilder(endInfoBuilder.attribute(), 5, directionThreshold);

        return new CompositeCotationBuilder(
            yearInfoBuilder,
            monthInfoBuilder,
            dayOfMonthInfoBuilder,
            dayOfWeekInfoBuilder,
            endInfoBuilder,
            simpleMobileMeanBuilder7,
            simpleMobileMeanBuilder20,
            simpleMobileMeanBuilder50,
            mobileMeanBuilder7Direction,
            mobileMeanBuilder7Position,
            mobileMeanBuilder20Direction,
            mobileMeanBuilder20Position,
            mobileMeansCrossBuilder7_20,
            mobileMeansCrossBuilder20_50,
            stochasticBuilder,
            stochasticSignalBuilder,
            stochasticSignalCrossing,
            stochasticOverBuyOrSell,
            stochasticDirection,
            stochasticValueAndSignalPosition,
            rocBuilder,
            rocOverBuyOrSell,
            macdBuilder,
            macdDirection,
            macdSignalBuilder,
            macdValueAndSignalPosition,
            macdSignalCrossing,
            chaikinMoneyFlowBuilder,
            chaikinMoneyFlowDirection,
            chaikinMoneyFlowPosition,
            chaikinMoneyFlowCrossing,
            easeOfMovementBuilder,
            easeOfMovementDirection,
            easeOfMovementPosition,
            easeOfMovementCrossing,
            nextDaysEndDirectionBuilder1,
            nextDaysEndDirectionBuilder2,
            nextDaysEndDirectionBuilder3,
            nextDaysEndDirectionBuilder5
        );
    }
}
