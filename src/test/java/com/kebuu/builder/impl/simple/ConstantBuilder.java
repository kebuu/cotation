package com.kebuu.builder.impl.simple;

import com.kebuu.builder.impl.SingleAttributeAbstractBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.TestAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;

public class ConstantBuilder<T> extends SingleAttributeAbstractBuilder<T> {

    private final T value;
    private final CotationAttribute<T> attribute = new TestAttribute<>();

    private ConstantBuilder(T value) {
        this.value = value;
    }

    public static <K> ConstantBuilder<K> of(K value) {
        return new ConstantBuilder<>(value);
    }

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        return new BuiltCotation(cotation).withAdditionalValues(new SimpleCotationValue<T>(attribute, value));
    }

    @Override
    public CotationAttribute<T> getSingleAttribute() {
        return attribute;
    }
}
