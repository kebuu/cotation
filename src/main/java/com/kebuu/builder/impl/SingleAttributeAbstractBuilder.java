package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public abstract class SingleAttributeAbstractBuilder<T> extends AbstractBuilder {

    public abstract CotationAttribute<T> getSingleAttribute();

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(getSingleAttribute());
    }
}
