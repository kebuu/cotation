package com.kebuu.dto.cotation.attribute.decorator;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.formatter.CotationAttributeFormatter;

public class CotationAttributeDecorator<T> implements CotationAttribute<T> {

    protected CotationAttribute<T> delegate;

    public CotationAttributeDecorator(CotationAttribute<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public CotationAttributeFormatter<T> getFormatter() {
        return delegate.getFormatter();
    }

    @Override
    public String getName() {
        return delegate.getName();
    }

    @Override
    public String getArffType() {
        return delegate.getArffType();
    }

    @Override
    public boolean isTechnical() {
        return delegate.isTechnical();
    }
}
