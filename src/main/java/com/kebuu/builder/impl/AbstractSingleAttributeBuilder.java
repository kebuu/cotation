package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.value.CotationValue;

public abstract class AbstractSingleAttributeBuilder<T> extends AbstractBuilder {

    /**
     * Return the single attribute built by this builder
     */
    public abstract CotationAttribute<T> attribute();

    public abstract CotationValue<T> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo);

    @Override
    public CotationAttributes attributes() {
        return new CotationAttributes(attribute());
    }

    @Override
    public BuiltCotation build(CotationBuilderInfo cotationBuilderInfo) {
        return new BuiltCotation(cotationBuilderInfo.getCotation()).withAdditionalValues(calculateSingleValue(cotationBuilderInfo));
    }

}
