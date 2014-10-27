package com.kebuu.dto.cotation.attribute.decorator;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

public class TechnicalAttribute<T> extends CotationAttributeDecorator<T> {

    public TechnicalAttribute(CotationAttribute<T> delegate) {
        super(delegate);
    }

    public static <K> TechnicalAttribute<K> of(CotationAttribute<K> delegate) {
        return new TechnicalAttribute<>(delegate);
    }

    @Override
    public boolean isTechnical() {
        return true;
    }
}
