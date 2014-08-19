package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;

public class StringCotationAttribute extends CotationAttribute {

    public StringCotationAttribute(String name) {
        super(name);
    }

    @Override
    public String getArffType() {
        return Constant.ARFF_TYPE_STRING;
    }
}
