package com.kebuu.builder.impl;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.Direction;

import java.util.Optional;

public class ValueDirectionBuilder extends AbstractBuilder {

    public static final String ATTRIBUTE_NAME_PREFIX = "direction_";

    private final EnumeratedNominalCotationAttribute<Direction> directionAttribute;
    private final CotationAttribute<Double> targetAttribute;

    public ValueDirectionBuilder(CotationAttribute<Double> targetAttribute) {
        this.targetAttribute = targetAttribute;
        this.directionAttribute = new EnumeratedNominalCotationAttribute<>(ATTRIBUTE_NAME_PREFIX + targetAttribute.getName(), Direction.class);
    }

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Direction> directionValue = new SimpleCotationValue<Direction>(directionAttribute, Direction.NONE);

        Optional<Double> currentValue = alreadyBuiltCotations.getValue(cotation.getPosition(), targetAttribute);
        Optional<Double> previousValue = alreadyBuiltCotations.getValue(cotation.getPosition() - 1, targetAttribute);

        if (previousValue.isPresent()) {
            directionValue = directionValue.withValue(Direction.fromConsecutiveValues(previousValue.get(), currentValue.get()));
        }
        return new BuiltCotation(cotation).withAdditionalValues(directionValue);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(directionAttribute);
    }
}
