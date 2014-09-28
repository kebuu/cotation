package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class WeightedMobileMeanBuilder extends AbstractBuilder {

    @Getter private final int mobileMeanRange;
    @Getter private final RealCotationAttribute mobileMeanValueAttribute;

    public WeightedMobileMeanBuilder(int mobileMeanRange, String attributeBaseName) {
        Preconditions.checkArgument(mobileMeanRange > 0, "Weighted mobile mean range should be greater than 0");

        this.mobileMeanRange = mobileMeanRange;
        this.mobileMeanValueAttribute = new RealCotationAttribute(attributeBaseName + "_" + mobileMeanRange);
    }

    protected abstract double getWeight(int i);

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(mobileMeanValueAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> mobileMeanValue = new SimpleCotationValue<>(mobileMeanValueAttribute);

        if (cotations.getByIndex(cotation.getPosition() - mobileMeanRange).isPresent()) {
            List<ValueAndWeight> valuesAndWeights = IntStream.range(0, mobileMeanRange)
                .mapToObj(i -> cotations.getByIndex(cotation.getPosition() - i).get())
                .map(cotationAtIndex -> new ValueAndWeight(cotationAtIndex.getEnd(), getWeight(cotation.getPosition() - cotationAtIndex.getPosition())))
                .collect(toList());


            mobileMeanValue = mobileMeanValue.withValue(calculateWeightedValue(valuesAndWeights));
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeanValue);
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


    @Value
    private static class ValueAndWeight {
        private final double value;
        private final double weight;

        public double getWeightedValue() {
            return value * weight;
        }
    }
}
