package com.kebuu.dto.cotation.attribute;

import com.kebuu.misc.ListWrapper;

public class CotationAttributes extends ListWrapper<CotationAttribute<?>> {

    public CotationAttributes(CotationAttribute<?>... values) {
        super(values);
    }

    public <T> CotationAttributes(Iterable<CotationAttribute<T>> iterable) {
        super((Iterable)iterable);
    }
}
