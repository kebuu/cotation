package com.kebuu.builder.impl.relation;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.ValueComparisonPosition;

import java.util.Optional;

public class ValuesPositionBuilder extends ValuesRelationBuilder<ValueComparisonPosition> {

    public ValuesPositionBuilder(CotationAttribute attribute1, Double constantValue) {
        super(attribute1, constantValue);
    }

    public ValuesPositionBuilder(CotationAttribute attribute1, CotationAttribute attribute2) {
        super(attribute1, attribute2);
    }

    @Override
    protected Class<ValueComparisonPosition> getEnumClass() {
        return ValueComparisonPosition.class;
    }

    @Override
    protected String getAttributeNamePrefix() {
        return "values_position_";
    }

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<ValueComparisonPosition> valuesPosition = new SimpleCotationValue<>(attribute);

        Optional<Double> value1 = getValue1(alreadyBuiltCotations, cotation.getPosition());
        Optional<Double> value2 = getValue2(alreadyBuiltCotations, cotation.getPosition());

        if (value1.isPresent() && value2.isPresent()) {
            valuesPosition = valuesPosition.withValue(ValueComparisonPosition.from(value1.get(), value2.get()));
        }

        return new BuiltCotation(cotation).withAdditionalValues(valuesPosition);
    }
}
