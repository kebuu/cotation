package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;

public class IntegerCotationAttribute extends AbstractAttribute<Integer> {

    public IntegerCotationAttribute(String name) {
        super(name);
    }

    @Override
    public String getArffType() {
        return Constant.ARFF_TYPE_INTEGER;
    }
}
