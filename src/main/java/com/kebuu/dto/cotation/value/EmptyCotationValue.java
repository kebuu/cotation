package com.kebuu.dto.cotation.value;

import com.kebuu.constant.Constant;

public class EmptyCotationValue implements CotationValue {

    public static final EmptyCotationValue INSTANCE = new EmptyCotationValue();

    private EmptyCotationValue() {}

    @Override
    public String getValueAsText() {
        return Constant.EMPTY_VALUE;
    }
}
