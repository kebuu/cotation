package com.kebuu.builder.impl.mobilemean;

import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;
import java.util.function.Function;

/**
 * La moyenne mobile à n séance, n étant > 0.
 * La moyenne mobile à 1 séance est les cours d'aujourd'hui, la moyenne mobile à 2 séances la moyenne du cours d'aujourd'hui et du cours d'hier.
 */
public class SimpleMobileMeanBuilder extends WeightedMobileMeanBuilder {

    private static final String MOBILE_MEAN_BASE_NAME = "mobile_mean";

    public SimpleMobileMeanBuilder(int mobileMeanRange) {
        super(mobileMeanRange, MOBILE_MEAN_BASE_NAME);
    }

    public SimpleMobileMeanBuilder(int mobileMeanRange, CotationAttribute<Double> attributeToAverage) {
        super(mobileMeanRange, MOBILE_MEAN_BASE_NAME, attributeToAverage);
    }

    public SimpleMobileMeanBuilder(int mobileMeanRange, Function<CotationBuilderInfo, Optional<Double>> valueToAverageExtractor) {
        this(mobileMeanRange, MOBILE_MEAN_BASE_NAME, valueToAverageExtractor);
    }

    public SimpleMobileMeanBuilder(int mobileMeanRange, String baseName, Function<CotationBuilderInfo, Optional<Double>> valueToAverageExtractor) {
        super(mobileMeanRange, baseName, valueToAverageExtractor);
    }

    @Override
    protected double getWeight(int i) {
        return 1.0;
    }
}
