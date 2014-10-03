package com.kebuu.builder.impl.mobilemean;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class WeightedMobileMeanBuilder extends AbstractSingleAttributeBuilder<Double> {

    @Getter protected final int mobileMeanRange;
    @Getter protected final RealCotationAttribute attribute;

    protected final Function<CotationBuilderInfo, Optional<Double>> valueToAverageExtractor;

    protected abstract double getWeight(int i);

    public WeightedMobileMeanBuilder(int mobileMeanRange, String attributeBaseName) {
        this(mobileMeanRange, attributeBaseName, (cotationBuilderInfo) -> Optional.of(cotationBuilderInfo.getCotation().getEnd()));
    }

    public WeightedMobileMeanBuilder(int mobileMeanRange, String attributeBaseName, Function<CotationBuilderInfo, Optional<Double>> valueToAverageExtractor) {
        Preconditions.checkArgument(mobileMeanRange > 0, "Weighted mobile mean range should be greater than 0");

        this.mobileMeanRange = mobileMeanRange;
        this.attribute = new RealCotationAttribute(attributeBaseName + "_"+ mobileMeanRange);
        this.valueToAverageExtractor = valueToAverageExtractor;
    }

    @Override
    public CotationAttribute<Double> getSingleAttribute() {
        return attribute;
    }

    public SimpleCotationValue<Double> calculateSingleValue(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> mobileMeanValue = new SimpleCotationValue<>(attribute);

        Optional<Double> valueToAverage = cotations.getByIndex(cotation.getPosition() - mobileMeanRange)
            .flatMap(firstCotationInRange -> getValueToAverage(firstCotationInRange, cotations, builtCotations, alreadyBuiltCotations));

        if (valueToAverage.isPresent()) {
            List<ValueAndWeight> valuesAndWeights = IntStream.range(0, mobileMeanRange)
                .mapToObj(i -> {
                    Optional<Double> baseValue = getValueToAverage(cotations.forceGetByIndex(cotation.getPosition() - i), cotations, builtCotations, alreadyBuiltCotations);
                    return new ValueAndWeight(baseValue.get(), getWeight(cotation.getPosition() - i));
                })
                .collect(toList());

            mobileMeanValue = mobileMeanValue.withValue(calculateWeightedValue(valuesAndWeights));
        }
        return mobileMeanValue;
    }

    private Double calculateWeightedValue(List<ValueAndWeight> valuesAndWeights) {
        double weightedValuesSum = valuesAndWeights.stream()
            .mapToDouble(ValueAndWeight::getWeightedValue)
            .sum();

        double weightsSum = valuesAndWeights.stream()
            .mapToDouble(ValueAndWeight::getWeight)
            .sum();

        return weightedValuesSum / weightsSum;
    }

    protected Optional<Double> getValueToAverage(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        CotationBuilderInfo cotationBuilderInfo = new CotationBuilderInfo(cotation, cotations, builtCotations, alreadyBuiltCotations);
        return valueToAverageExtractor.apply(cotationBuilderInfo);
    }

    @Value
    private static class ValueAndWeight {
        private final double value;
        private final double weight;

        public double getWeightedValue() {
            return value * weight;
        }
    }
}
