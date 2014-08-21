package com.kebuu.config;

import com.kebuu.builder.impl.MobileMeansCrossingBuilder;
import com.kebuu.builder.impl.MultiCotationBuilder;
import com.kebuu.builder.impl.MobileMeanBuilder;
import com.kebuu.dto.arff.ArffAttributes;
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
        return new MultiCotationBuilder(mobileMeanBuilder20, mobileMeanBuilder50, mobileMeansCrossBuilder2050);
    }

    @Bean
    public ArffAttributes enhancedCotationAttributes() {
//        ArffAttributes<EnhancedCotation> enhancedCotationAttributes = new ArffAttributes<EnhancedCotation>(
//            new ArffAttribute<EnhancedCotation>("date", "date " + Constant.YYYY_MM_DD, (cotation -> DateFormatUtils.ISO_DATE_FORMAT.format(cotation.getDate()))),
//            new ArffAttribute<EnhancedCotation>("start", "real", EnhancedCotation::getStart),
//            new ArffAttribute<EnhancedCotation>("end", "real", EnhancedCotation::getEnd),
//            new ArffAttribute<EnhancedCotation>("min", "real", EnhancedCotation::getMin),
//            new ArffAttribute<EnhancedCotation>("max", "real", EnhancedCotation::getMax),
//            new ArffAttribute<EnhancedCotation>("volume", "integer", EnhancedCotation::getVolume),
//            new ArffAttribute<EnhancedCotation>("dailyEndDelta", "real", x -> getToFormattedNumberFunction().apply(x.getDailyEndDelta())),
//            new ArffAttribute<EnhancedCotation>("dailyEndDeltaPercent", "real", x -> getToFormattedNumberFunction().apply(x.getDailyEndDeltaPercent())),
//            new ArffAttribute<EnhancedCotation>("intraDayDelta", "real", x -> getToFormattedNumberFunction().apply(x.getIntraDayDelta())),
//            new ArffAttribute<EnhancedCotation>("intraDayMaxMinusMin", "real", x -> getToFormattedNumberFunction().apply(x.getIntraDayMaxMinusMin())),
//            new ArffAttribute<EnhancedCotation>("sameDirectionConsecutiveCount", "integer", EnhancedCotation::getSameDirectionConsecutiveCount),
//            new ArffAttribute<EnhancedCotation>("slippingMean20", "real", x -> getToFormattedNumberFunction().apply(x.getSlippingMean20())),
//            new ArffAttribute<EnhancedCotation>("slippingMean20Delta", "real", x -> getToFormattedNumberFunction().apply(x.getSlippingMean20Delta())),
//            new ArffAttribute<EnhancedCotation>("year", "integer", EnhancedCotation::getYear),
//            new ArffAttribute<EnhancedCotation>("month", "{" + rangeAsCommaSeparatedString(IntStream.rangeClosed(1, 12)) + "}", EnhancedCotation::getMonth),
//            new ArffAttribute<EnhancedCotation>("dayOfMonth", "{" + rangeAsCommaSeparatedString(IntStream.rangeClosed(1, 31)) + "}", EnhancedCotation::getDayOfMonth),
//            new ArffAttribute<EnhancedCotation>("dayOfWeek", "{" + rangeAsCommaSeparatedString(IntStream.rangeClosed(1, 7)) + "}", EnhancedCotation::getDayOfWeek),
//            new ArffAttribute<EnhancedCotation>("direction", "{" + asCommaSeparatedString(Direction.class) + "}", EnhancedCotation::getDirection),
//            new ArffAttribute<EnhancedCotation>("M20_direction", "{" + asCommaSeparatedString(MMDirection.class) + "}", EnhancedCotation::getM20Direction),
//            new ArffAttribute<EnhancedCotation>("M20_position", "{" + asCommaSeparatedString(MMPosition.class) + "}", EnhancedCotation::getM20Position)
//        );

        return null;
    }
}
