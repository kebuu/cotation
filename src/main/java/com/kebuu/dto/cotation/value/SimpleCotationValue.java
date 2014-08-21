package com.kebuu.dto.cotation.value;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;

public class SimpleCotationValue<T> implements CotationValue<T> {

    protected final CotationAttribute<T> attribute;
    protected final Optional<T> value;

    public SimpleCotationValue(CotationAttribute<T> attribute, Optional<T> value) {
        this.attribute = attribute;
        this.value = value;
    }

    public SimpleCotationValue(CotationAttribute<T> attribute, T value) {
        this(attribute, Optional.of(value));
    }

    public SimpleCotationValue(CotationAttribute<T> attribute) {
        this(attribute, Optional.empty());
    }

    public SimpleCotationValue<T> withValue(T value) {
        return new SimpleCotationValue<T>(attribute, value);
    }

    @Override
    public Optional<T> getValue() {
        return value;
    }

    @Override
    public CotationAttribute<T> getAttribute() {
        return attribute;
    }
}
