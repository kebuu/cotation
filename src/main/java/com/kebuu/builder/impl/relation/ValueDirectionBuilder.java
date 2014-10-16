package com.kebuu.builder.impl.relation;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.Direction;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ValueDirectionBuilder extends ValuesEnumRelationBuilder<Direction> {

    private static final int DEFAULT_DIRECTION_STEP = -1;
    private static final String NAME_PREFIX = "direction_";

    /**
     * Set a positive direction step to get the direction to the next step
     * and a negative direction steop to get the direction since the la step
     */
    protected final int directionStep;

    public ValueDirectionBuilder(CotationAttribute<Double> attribute) {
        this(attribute, DEFAULT_DIRECTION_STEP);
    }

    public ValueDirectionBuilder(CotationAttribute<Double> attribute, int directionStep) {
        super(attribute, attribute);
        this.directionStep = directionStep;
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transfomer) {
        this(name, transfomer, DEFAULT_DIRECTION_STEP);
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transfomer, int directionStep) {
        super(name, transfomer, name, transfomer);
        this.directionStep = directionStep;
    }

    @Override
    protected Class<Direction> getEnumClass() {
        return Direction.class;
    }

    @Override
    protected String getAttributeNamePrefix() {
        return NAME_PREFIX;
    }

    @Override
    public CotationValue<Direction> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Direction> directionValue = new SimpleCotationValue<Direction>(attribute);

        Cotation cotation = cotationBuilderInfo.getCotation();

        Optional<Double> currentValue = getValue1(cotationBuilderInfo);
        Optional<Double> stepValue = cotationBuilderInfo.getCotations().getByIndex(cotation.getPosition() + directionStep)
            .flatMap(stepCotation -> getValue2(cotationBuilderInfo.withCotation(stepCotation)));

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
