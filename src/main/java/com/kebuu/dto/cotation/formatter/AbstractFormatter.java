package com.kebuu.dto.cotation.formatter;

import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.value.CotationValue;

public abstract class AbstractFormatter<T> implements CotationAttributeFormatter<T> {

    @Override
    public String format(CotationValue<T> cotationValue) {
        return cotationValue.getValue()
            .map(value -> doFormat(value))
            .orElse(Constant.EMPTY_VALUE);
    }

    protected abstract String doFormat(T value);
}
