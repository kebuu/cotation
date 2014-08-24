package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;

public class LongCotationAttribute extends AbstractAttribute<Long> {

    public LongCotationAttribute(String name) {
        super(name);
    }

    @Override
    public String getArffType() {
        return Constant.ARFF_TYPE_INTEGER;
    }
}
