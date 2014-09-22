package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractBuilder;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;

import java.util.Optional;
import java.util.function.Function;

public abstract class ValuesRelationBuilder<T extends Enum<? extends Enum<?>>> extends AbstractBuilder {

    private final Function<BuiltCotation, Optional<Double>> value1Transfomer;
    private final Function<BuiltCotation, Optional<Double>> value2Transfomer;
    private final String value1Name;
    private final String value2Name;

    protected final EnumeratedNominalCotationAttribute<T> attribute;
    protected abstract Class<T> getEnumClass();
    protected abstract String getAttributeNamePrefix();

    public ValuesRelationBuilder(String value1Name, Function<BuiltCotation, Optional<Double>> value1Transfomer, String value2Name, Function<BuiltCotation, Optional<Double>> value2Transfomer) {
        this.value1Transfomer = value1Transfomer;
        this.value2Transfomer = value2Transfomer;
        this.value1Name = value1Name;
        this.value2Name = value2Name;

        attribute = new EnumeratedNominalCotationAttribute<>(getAttributeNamePrefix() + value1Name + "_" + value2Name, getEnumClass());
    }

    public ValuesRelationBuilder(String value1Name, Function<BuiltCotation, Optional<Double>> value1Transfomer, Double constantValue) {
        this(value1Name, value1Transfomer, constantValue.toString(), constantValue);
    }

    public ValuesRelationBuilder(String value1Name, Function<BuiltCotation, Optional<Double>> value1Transfomer, String value2Name, Double constantValue) {
        this(value1Name, value1Transfomer, value2Name, (BuiltCotation x) -> Optional.of(constantValue));
    }

    public ValuesRelationBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        this(attribute1.getName(), createValueTransformer(attribute1), attribute2.getName(), createValueTransformer(attribute2));
    }

    public ValuesRelationBuilder(CotationAttribute<Double> attribute1, Double constantValue) {
        this(attribute1.getName(), createValueTransformer(attribute1), constantValue);
    }

    private static Function<BuiltCotation, Optional<Double>> createValueTransformer(CotationAttribute<Double> attribute) {
        return (BuiltCotation x) -> x.getValueByAttribute(attribute);
    }

    protected Optional<Double> getValue1(BuiltCotations builtCotations, int position) {
        return builtCotations.getByIndex(position).flatMap(value1Transfomer);
    }

    protected Optional<Double> getValue2(BuiltCotations builtCotations, int position) {
        return builtCotations.getByIndex(position).flatMap(value2Transfomer);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(attribute);
    }
}
