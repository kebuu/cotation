package com.kebuu.config;

import com.kebuu.constant.Constant;
import com.kebuu.domain.EnhancedCotation;
import com.kebuu.dto.ArffAttribute;
import com.kebuu.dto.ArffAttributes;
import com.kebuu.enums.Direction;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
public class SpringConfig {

    @Autowired private DataSource dataSource;

    @Bean
    public DecimalFormat decimalFormat() {
        DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(Locale.US);
        decimalFormat.applyPattern("0.0000");
        decimalFormat.setRoundingMode(RoundingMode.HALF_DOWN);
        return decimalFormat;
    }

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
        ArffAttributes<EnhancedCotation> enhancedCotationAttributes = new ArffAttributes<EnhancedCotation>(
            new ArffAttribute<EnhancedCotation>("date", "date " + Constant.YYYY_MM_DD, (cotation -> DateFormatUtils.ISO_DATE_FORMAT.format(cotation.getDate()))),
            new ArffAttribute<EnhancedCotation>("start", "real", EnhancedCotation::getStart),
            new ArffAttribute<EnhancedCotation>("end", "real", EnhancedCotation::getEnd),
            new ArffAttribute<EnhancedCotation>("min", "real", EnhancedCotation::getMin),
            new ArffAttribute<EnhancedCotation>("max", "real", EnhancedCotation::getMax),
            new ArffAttribute<EnhancedCotation>("volume", "integer", EnhancedCotation::getVolume),
            new ArffAttribute<EnhancedCotation>("dailyEndDelta", "real", x -> getToFormattedNumberFunction().apply(x.getDailyEndDelta())),
            new ArffAttribute<EnhancedCotation>("dailyEndDeltaPercent", "real", x -> getToFormattedNumberFunction().apply(x.getDailyEndDeltaPercent())),
            new ArffAttribute<EnhancedCotation>("intraDayDelta", "real", x -> getToFormattedNumberFunction().apply(x.getIntraDayDelta())),
            new ArffAttribute<EnhancedCotation>("intraDayMaxMinusMin", "real", x -> getToFormattedNumberFunction().apply(x.getIntraDayMaxMinusMin())),
            new ArffAttribute<EnhancedCotation>("sameDirectionConsecutiveCount", "integer", EnhancedCotation::getSameDirectionConsecutiveCount),
            new ArffAttribute<EnhancedCotation>("slippingMean20", "real", x -> getToFormattedNumberFunction().apply(x.getSlippingMean20())),
            new ArffAttribute<EnhancedCotation>("slippingMean20Delta", "real", x -> getToFormattedNumberFunction().apply(x.getSlippingMean20Delta())),
            new ArffAttribute<EnhancedCotation>("year", "integer", EnhancedCotation::getYear),
            new ArffAttribute<EnhancedCotation>("month", "{" + rangeAsCommaSeparatedString(IntStream.rangeClosed(1, 12)) + "}", EnhancedCotation::getMonth),
            new ArffAttribute<EnhancedCotation>("dayOfMonth", "{" + rangeAsCommaSeparatedString(IntStream.rangeClosed(1, 31)) + "}", EnhancedCotation::getDayOfMonth),
            new ArffAttribute<EnhancedCotation>("dayOfWeek", "{" + rangeAsCommaSeparatedString(IntStream.rangeClosed(1, 7)) + "}", EnhancedCotation::getDayOfWeek),
            new ArffAttribute<EnhancedCotation>("direction", "{" + asCommaSeparatedString(Direction.class) + "}", EnhancedCotation::getDirection)
        );

        return enhancedCotationAttributes;
    }

    private String rangeAsCommaSeparatedString(IntStream intStream) {
        return intStream.mapToObj(String::valueOf).collect(Collectors.joining(","));
    }

    private String asCommaSeparatedString(Class<? extends Enum<? extends Enum<?>>> enumClass) {
        return Arrays.asList(enumClass.getEnumConstants()).stream()
                .map(Enum::name)
                .collect(Collectors.joining(","));
    }

    private Function<Number, String> getToFormattedNumberFunction() {
        return x -> x == null ? ArffAttribute.MISSING_VALUE : decimalFormat().format(x);
    }
}
