package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.AbstractAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;
import java.util.function.Function;

import static com.kebuu.utils.FunctionUtils.createValueTransformer;

public abstract class ValuesRelationBuilder<T, K extends AbstractAttribute<T>> extends AbstractSingleAttributeBuilder<T> {

    protected final Function<CotationBuilderInfo, Optional<Double>> value1Transfomer;
    protected final Function<CotationBuilderInfo, Optional<Double>> value2Transfomer;
    protected final String value1Name;
    protected final String value2Name;

    protected final K attribute;

    protected abstract String getAttributeNamePrefix();
    protected abstract K createAttribute(String attributeName);

    public ValuesRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Function<CotationBuilderInfo, Optional<Double>> value2Transfomer) {
        this.value1Transfomer = value1Transfomer;
        this.value2Transfomer = value2Transfomer;
        this.value1Name = value1Name;
        this.value2Name = value2Name;

        attribute = createAttribute(getAttributeName(value1Name, value2Name));
    }

    @Override
    public CotationAttribute<T> attribute() {
        return attribute;
    }

    protected String getAttributeName(String value1Name, String value2Name) {
        String name = getAttributeNamePrefix() + value1Name;
        return value1Name.equals(value2Name) ? name : name + "_" + value2Name;
    }

    public ValuesRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, Double constantValue) {
        this(value1Name, value1Transfomer, constantValue.toString(), constantValue);
    }

    public ValuesRelationBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Double constantValue) {
        this(value1Name, value1Transfomer, value2Name, (CotationBuilderInfo x) -> Optional.of(constantValue));
    }

    public ValuesRelationBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        this(attribute1.getName(), createValueTransformer(attribute1), attribute2.getName(), createValueTransformer(attribute2));
    }

    public ValuesRelationBuilder(CotationAttribute<Double> attribute1, Double constantValue) {
        this(attribute1.getName(), createValueTransformer(attribute1), constantValue);
    }

    protected Optional<Double> getValue1(CotationBuilderInfo cotationBuilderInfo) {
        return value1Transfomer.apply(cotationBuilderInfo);
    }

    protected Optional<Double> getValue2(CotationBuilderInfo cotationBuilderInfo) {
        return value2Transfomer.apply(cotationBuilderInfo);
    }
}
