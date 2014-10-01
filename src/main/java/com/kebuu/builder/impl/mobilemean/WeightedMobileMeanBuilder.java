package com.kebuu.builder.impl.mobilemean;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;
import lombok.Value;

import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public abstract class WeightedMobileMeanBuilder extends AbstractSingleAttributeBuilder<Double> {

    @Getter private final int mobileMeanRange;
    @Getter private final RealCotationAttribute attribute;

    protected abstract double getWeight(int i);

    public WeightedMobileMeanBuilder(int mobileMeanRange, String attributeBaseName) {
        Preconditions.checkArgument(mobileMeanRange > 0, "Weighted mobile mean range should be greater than 0");

        this.mobileMeanRange = mobileMeanRange;
        this.attribute = new RealCotationAttribute(attributeBaseName + "_" + mobileMeanRange);
    }

    @Override
    public CotationAttribute<Double> getSingleAttribute() {
        return attribute;
    }

    public SimpleCotationValue<Double> calculateSingleValue(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> mobileMeanValue = new SimpleCotationValue<>(attribute);

        if (cotations.getByIndex(cotation.getPosition() - mobileMeanRange).isPresent()) {
            List<ValueAndWeight> valuesAndWeights = IntStream.range(0, mobileMeanRange)
                .mapToObj(i -> cotations.getByIndex(cotation.getPosition() - i).get())
                .map(cotationAtIndex -> new ValueAndWeight(cotationAtIndex.getEnd(), getWeight(cotation.getPosition() - cotationAtIndex.getPosition())))
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

    @Value
    private static class ValueAndWeight {
        private final double value;
        private final double weight;

        public double getWeightedValue() {
            return value * weight;
        }
    }
}