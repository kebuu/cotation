package com.kebuu.builder.impl.decorator;

import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.decorator.TechnicalAttribute;
import com.kebuu.dto.cotation.value.CotationValue;

public class TechnicalBuilder<T> extends AbstractSingleAttributeBuilder<T> {

    protected AbstractSingleAttributeBuilder<T> delegate;

    public <K extends AbstractSingleAttributeBuilder<T>> TechnicalBuilder(K delegate) {
        this.delegate = delegate;
    }

    public static <U, K extends AbstractSingleAttributeBuilder> TechnicalBuilder<U> of(K delegate) {
        return new TechnicalBuilder<U>(delegate);
    }

    @Override
    public CotationAttribute<T> attribute() {
        return TechnicalAttribute.of(delegate.attribute());
    }

    @Override
    public CotationValue calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        return delegate.calculateSingleValue(cotationBuilderInfo);
    }
}
