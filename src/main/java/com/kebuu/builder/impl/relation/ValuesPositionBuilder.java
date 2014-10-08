package com.kebuu.builder.impl.relation;

import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.ValueComparisonPosition;

import java.util.Optional;

public class ValuesPositionBuilder extends ValuesEnumRelationBuilder<ValueComparisonPosition> {

    public ValuesPositionBuilder(CotationAttribute<Double> attribute1, Double constantValue) {
        super(attribute1, constantValue);
    }

    public ValuesPositionBuilder(CotationAttribute<Double> attribute1, CotationAttribute attribute2) {
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
    public CotationValue<ValueComparisonPosition> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<ValueComparisonPosition> valuesPosition = new SimpleCotationValue<>(attribute);

        Optional<Double> value1 = getValue1(cotationBuilderInfo);
        Optional<Double> value2 = getValue2(cotationBuilderInfo);

        if (value1.isPresent() && value2.isPresent()) {
            valuesPosition = valuesPosition.withValue(ValueComparisonPosition.from(value1.get(), value2.get()));
        }

        return valuesPosition;
    }
}
