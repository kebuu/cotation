package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;

import java.util.Optional;
import java.util.function.Function;

public abstract class ValuesEnumRelationBuilder<T extends Enum<? extends Enum<?>>> extends AbstractSingleAttributeBuilder<T> {

    private final Function<CotationBuilderInfo, Optional<Double>> value1Transfomer;
    private final Function<CotationBuilderInfo, Optional<Double>> value2Transfomer;
    private final String value1Name;
    private final String value2Name;

    protected final EnumeratedNominalCotationAttribute<T> attribute;
    protected abstract Class<T> getEnumClass();
    protected abstract String getAttributeNamePrefix();

    public ValuesEnumRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Function<CotationBuilderInfo, Optional<Double>> value2Transfomer) {
        this.value1Transfomer = value1Transfomer;
        this.value2Transfomer = value2Transfomer;
        this.value1Name = value1Name;
        this.value2Name = value2Name;

        attribute = new EnumeratedNominalCotationAttribute<>(getAttributeName(value1Name, value2Name), getEnumClass());
    }

    @Override
    public CotationAttribute<T> attribute() {
        return attribute;
    }

    private String getAttributeName(String value1Name, String value2Name) {
        String name = getAttributeNamePrefix() + value1Name;
        return value1Name.equals(value2Name) ? name : name + "_" + value2Name;
    }

    public ValuesEnumRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, Double constantValue) {
        this(value1Name, value1Transfomer, constantValue.toString(), constantValue);
    }

    public ValuesEnumRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Double constantValue) {
        this(value1Name, value1Transfomer, value2Name, (CotationBuilderInfo x) -> Optional.of(constantValue));
    }

    public ValuesEnumRelationBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        this(attribute1.getName(), createValueTransformer(attribute1), attribute2.getName(), createValueTransformer(attribute2));
    }

    public ValuesEnumRelationBuilder(CotationAttribute<Double> attribute1, Double constantValue) {
        this(attribute1.getName(), createValueTransformer(attribute1), constantValue);
    }

    private static Function<CotationBuilderInfo, Optional<Double>> createValueTransformer(CotationAttribute<Double> attribute) {
        return (CotationBuilderInfo cotationBuilderInfo) -> cotationBuilderInfo.getAlreadyBuiltCotation().getValueByAttribute(attribute);
    }

    protected Optional<Double> getValue1(CotationBuilderInfo cotationBuilderInfo) {
        return value1Transfomer.apply(cotationBuilderInfo);
    }

    protected Optional<Double> getValue2(CotationBuilderInfo cotationBuilderInfo) {
        return value2Transfomer.apply(cotationBuilderInfo);
    }
}
