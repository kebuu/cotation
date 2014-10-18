package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.Direction;
import com.kebuu.utils.FunctionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ValueDirectionBuilder extends AbstractSingleAttributeBuilder<Direction> {

    private static final int DEFAULT_DIRECTION_STEP = -1;
    private static final String NAME_PREFIX = "direction_";
    private final EnumeratedNominalCotationAttribute<Direction> attribute;
    private final Function<CotationBuilderInfo, Optional<Double>> transfomer;

    /**
     * Set a positive direction step to get the direction to the next step
     * and a negative direction steop to get the direction since the la step
     */
    protected final int directionStep;

    public ValueDirectionBuilder(CotationAttribute<Double> attribute) {
        this(attribute, DEFAULT_DIRECTION_STEP);
    }

    public ValueDirectionBuilder(CotationAttribute<Double> attribute, int directionStep) {
        this(attribute.getName(), FunctionUtils.transformerFromAttribute(attribute), directionStep);
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transformer) {
        this(name, transformer, DEFAULT_DIRECTION_STEP);
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transformer, int directionStep) {
        this.directionStep = directionStep;
        this.transfomer = transformer;
        this.attribute = new EnumeratedNominalCotationAttribute<>(NAME_PREFIX + name + "_" + directionStep, Direction.class);
    }

    @Override
    public CotationAttribute<Direction> attribute() {
        return attribute;
    }

    @Override
    public CotationValue<Direction> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Direction> directionValue = new SimpleCotationValue<Direction>(attribute);

        Cotation cotation = cotationBuilderInfo.getCotation();

        Optional<Double> currentValue = transfomer.apply(cotationBuilderInfo);
        Optional<Double> stepValue = cotationBuilderInfo.getCotations().getByIndex(cotation.getPosition() + directionStep)
            .flatMap(stepCotation -> transfomer.apply(cotationBuilderInfo.withCotation(stepCotation)));

        if (stepValue.isPresent()) {
            directionValue = directionValue.withValue(calculateDirection(currentValue, stepValue));
        }
        return directionValue;
    }

    private Direction calculateDirection(Optional<Double> currentValue, Optional<Double> stepValue) {
        if (directionStep > 0) {
            return Direction.fromConsecutiveValues(currentValue.get(), stepValue.get());
        } else {
            return Direction.fromConsecutiveValues(stepValue.get(), currentValue.get());
        }
    }
}
