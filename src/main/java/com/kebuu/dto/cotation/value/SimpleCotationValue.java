package com.kebuu.dto.cotation.value;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;
import java.util.function.Function;

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
        return new SimpleCotationValue<>(attribute, value);
    }

    public <K extends CotationValue<T>> SimpleCotationValue<T> withValue(K value) {
        return new SimpleCotationValue<>(attribute, value.getValue());
    }

    public SimpleCotationValue<T> map(Function<T, T> function) {
        return new SimpleCotationValue(attribute, value.map(function));
    }

    public T unwrapValue() {
        return value.get();
    }

    public boolean hasValue() {
        return value.isPresent();
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
