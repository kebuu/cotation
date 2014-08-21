package com.kebuu.dto.cotation.attribute;

import com.kebuu.misc.ListWrapper;

public class CotationAttributes extends ListWrapper<CotationAttribute<?>> {

    public CotationAttributes() {
    }

    public CotationAttributes(CotationAttribute<?>... values) {
        super(values);
    }
}
