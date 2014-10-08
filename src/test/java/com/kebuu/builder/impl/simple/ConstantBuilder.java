package com.kebuu.builder.impl.simple;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.TestAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;

public class ConstantBuilder<T> extends AbstractSingleAttributeBuilder<T> {

    private final T value;
    private final CotationAttribute<T> attribute = new TestAttribute<>();

    private ConstantBuilder(T value) {
        this.value = value;
    }

    public static <K> ConstantBuilder<K> of(K value) {
        return new ConstantBuilder<>(value);
    }

    @Override
    public CotationAttribute<T> attribute() {
        return attribute;
    }

    @Override
    public CotationValue calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        return new SimpleCotationValue<T>(attribute, value);
    }
}
