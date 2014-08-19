package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;

public class RealCotationAttribute extends CotationAttribute {

    public RealCotationAttribute(String name) {
        super(name);
    }

    @Override
    public String getArffType() {
        return Constant.ARFF_TYPE_REAL;
    }
}
