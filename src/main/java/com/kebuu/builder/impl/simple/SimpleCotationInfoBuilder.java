package com.kebuu.builder.impl.simple;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.exception.InvalidCotationInfoException;

import java.util.function.Function;

public abstract class SimpleCotationInfoBuilder<T> extends AbstractSingleAttributeBuilder<T> {

    private final Function<Cotation, T> tranformer;

    protected SimpleCotationInfoBuilder(Function<Cotation, T> tranformer) {
        this.tranformer = tranformer;
    }

    @Override
    public CotationValue calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<T> simpleCotationValue = new SimpleCotationValue(attribute());

        T value = tranformer.apply(cotationBuilderInfo.getCotation());

        if (value != null) {
            simpleCotationValue = simpleCotationValue.withValue(value);
        }

        if (!isValidValue(simpleCotationValue)) {
            throw new InvalidCotationInfoException();
        } else {
            return simpleCotationValue;
        }
    }

    protected boolean isValidValue(SimpleCotationValue<T> simpleCotationValue) {
        return true;
    }
}
