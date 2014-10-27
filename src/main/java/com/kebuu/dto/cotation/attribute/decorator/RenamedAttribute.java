package com.kebuu.dto.cotation.attribute.decorator;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

public class RenamedAttribute<T> extends CotationAttributeDecorator<T> {

    protected String name;

    public RenamedAttribute(String name, CotationAttribute<T> delegate) {
        super(delegate);
        this.name = name;
    }

    public static <K> RenamedAttribute<K> of(CotationAttribute<K> delegate) {
        return new RenamedAttribute<>(delegate.getName(), delegate);
    }

    @Override
    public String getName() {
        return name;
    }
}
