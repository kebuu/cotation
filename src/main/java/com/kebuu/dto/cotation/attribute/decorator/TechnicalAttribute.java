package com.kebuu.dto.cotation.attribute.decorator;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.formatter.CotationAttributeFormatter;

public class TechnicalAttribute<T> implements CotationAttribute<T> {

    protected CotationAttribute<T> delegate;

    public TechnicalAttribute(String name, CotationAttribute<T> delegate) {
        this.delegate = delegate;
    }

    public static <K> TechnicalAttribute<K> of(CotationAttribute<K> delegate) {
        return new TechnicalAttribute<>(delegate.getName(), delegate);
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
        return true;
    }
}
