package com.kebuu.builder.impl;

import com.kebuu.builder.impl.mobilemean.SimpleMobileMeanBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Optional;

/**
 * Calcul du Ease of Movement : 10000 * (((highest + lowest) - (highestHier + lowestHier)) / 2) * (highest - lowest) / volume
 * A moyenner sur la période
 */
public class EaseOfMovementBuilder extends SimpleMobileMeanBuilder {

    public static final String PREFIX_NAME = "eom";
    public static final int DEFAULT_PERIOD = 14;

    public EaseOfMovementBuilder(int period) {
        super(period, PREFIX_NAME, EaseOfMovementBuilder::transformer);
    }

    public EaseOfMovementBuilder() {
        this(DEFAULT_PERIOD);
    }

    @Override
    public CotationAttribute<Double> attribute() {
        return attribute;
    }

    private static Optional<Double> transformer(CotationBuilderInfo cotationBuilderInfo) {
        Optional<Double> result = Optional.empty();

        Cotation cotation = cotationBuilderInfo.getCotation();
        if(cotation.hasVolume() && cotationBuilderInfo.getPreviousCotation().isPresent()) {
            Cotation previousCotation = cotationBuilderInfo.getPreviousCotation().get();

            double todayVolume = cotation.getVolume();
            double todayHighest = cotation.getMax();
            double todayLowest = cotation.getMin();
            double yesterdayHighest = previousCotation.getMax();
            double yesterdayLowest = previousCotation.getMin();

            if (todayVolume != NumberUtils.DOUBLE_ZERO) {
                double eomValue = 10000.0 * (((todayHighest + todayLowest) - (yesterdayHighest + yesterdayLowest)) / 2.0) * (todayHighest - todayLowest) / todayVolume;
                result = Optional.of(eomValue);
            }
        }

        return result;
    }
}
