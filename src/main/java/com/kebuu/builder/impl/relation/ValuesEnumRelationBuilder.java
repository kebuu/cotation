package com.kebuu.builder.impl.relation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;

import java.util.Optional;
import java.util.function.Function;


public abstract class ValuesEnumRelationBuilder<T extends Enum<? extends Enum<?>>> extends ValuesRelationBuilder<T, EnumeratedNominalCotationAttribute<T>> {

    protected abstract Class<T> getEnumClass();

    @Override
    protected EnumeratedNominalCotationAttribute<T> createAttribute(String attributeName) {
        return new EnumeratedNominalCotationAttribute<>(attributeName, getEnumClass());
    }

    public ValuesEnumRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Function<CotationBuilderInfo, Optional<Double>> value2Transfomer) {
        super(value1Name, value1Transfomer, value2Name, value2Transfomer);
    }

    public ValuesEnumRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, Double constantValue) {
        super(value1Name, value1Transfomer, constantValue);
    }

    public ValuesEnumRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Double constantValue) {
        super(value1Name, value1Transfomer, value2Name, constantValue);
    }

    public ValuesEnumRelationBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        super(attribute1, attribute2);
    }

    public ValuesEnumRelationBuilder(CotationAttribute<Double> attribute1, Double constantValue) {
        super(attribute1, constantValue);
    }
}
