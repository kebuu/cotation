package com.kebuu.builder.impl;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.Direction;
import com.kebuu.enums.ValueDirectionMode;
import com.kebuu.utils.FunctionUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.function.Function;

@Slf4j
public class ValueDirectionBuilder extends AbstractSingleAttributeBuilder<Direction> {

    private static final double DEFAULT_NO_DIRECTION_THRESHOLD = 0.0;
    private static final int DEFAULT_DIRECTION_STEP = -1;
    private static final String NAME_PREFIX = "direction_";
    private static final ValueDirectionMode DEFAULT_THRESHOLD_MODE = ValueDirectionMode.PERCENT;

    private final EnumeratedNominalCotationAttribute<Direction> attribute;
    private final Function<CotationBuilderInfo, Optional<Double>> transformer;
    private final ValueDirectionMode thresholdMode;

    /**
     * The minimum difference between current and step values unless consider the direction as Direction.NONE
     */
    private final double noDirectionThreshold;

    /**
     * Set a positive direction step to get the direction to the next step
     * and a negative direction step to get the direction since the last step
     */
    protected final int directionStep;

    public ValueDirectionBuilder(CotationAttribute<Double> attribute) {
        this(attribute, DEFAULT_DIRECTION_STEP, DEFAULT_NO_DIRECTION_THRESHOLD, DEFAULT_THRESHOLD_MODE);
    }

    public ValueDirectionBuilder(CotationAttribute<Double> attribute, int directionStep) {
        this(attribute, directionStep, DEFAULT_THRESHOLD_MODE);
    }

    public ValueDirectionBuilder(CotationAttribute<Double> attribute, double noDirectionThreshold) {
        this(attribute, DEFAULT_DIRECTION_STEP, noDirectionThreshold, DEFAULT_THRESHOLD_MODE);
    }

    public ValueDirectionBuilder(CotationAttribute<Double> attribute, int directionStep, ValueDirectionMode thresholdMode) {
        this(attribute, directionStep, DEFAULT_NO_DIRECTION_THRESHOLD, thresholdMode);
    }

    public ValueDirectionBuilder(CotationAttribute<Double> attribute, int directionStep, double noDirectionThreshold, ValueDirectionMode thresholdMode) {
        this(attribute.getName(), FunctionUtils.transformerFromAttribute(attribute), directionStep, noDirectionThreshold, thresholdMode);
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transformer) {
        this(name, transformer, DEFAULT_DIRECTION_STEP, DEFAULT_NO_DIRECTION_THRESHOLD, DEFAULT_THRESHOLD_MODE);
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transformer, int directionStep, double noDirectionThreshold) {
        this(name, transformer, directionStep, noDirectionThreshold, DEFAULT_THRESHOLD_MODE);
    }

    public ValueDirectionBuilder(String name, Function<CotationBuilderInfo, Optional<Double>> transformer, int directionStep, double noDirectionThreshold, ValueDirectionMode thresholdMode) {
        this.directionStep = directionStep;
        this.noDirectionThreshold = noDirectionThreshold;
        this.transformer = transformer;
        this.attribute = new EnumeratedNominalCotationAttribute<>(NAME_PREFIX + name + "_" + directionStep, Direction.class);
        this.thresholdMode = thresholdMode;
    }

    @Override
    public CotationAttribute<Direction> attribute() {
        return attribute;
    }

    @Override
    public CotationValue<Direction> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Direction> directionValue = new SimpleCotationValue<Direction>(attribute);

        Cotation cotation = cotationBuilderInfo.getCotation();

        Optional<Double> currentValue = transformer.apply(cotationBuilderInfo);
        Optional<Double> stepValue = cotationBuilderInfo.getCotations().getByIndex(cotation.getPosition() + directionStep)
            .flatMap(stepCotation -> transformer.apply(cotationBuilderInfo.withCotation(stepCotation)));

        if (stepValue.isPresent()) {
            directionValue = directionValue.withValue(calculateDirection(currentValue, stepValue));
        }
        return directionValue;
    }

    private Direction calculateDirection(Optional<Double> currentValue, Optional<Double> stepValue) {
        if (directionStep > 0) {
            return thresholdMode.getDirection(currentValue.get(), stepValue.get(), noDirectionThreshold);
        } else {
            return thresholdMode.getDirection(stepValue.get(), currentValue.get(), noDirectionThreshold);
        }
    }
}
