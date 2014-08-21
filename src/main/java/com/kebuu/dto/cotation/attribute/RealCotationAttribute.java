package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.formatter.DoubleFormatter;

public class RealCotationAttribute extends AbstractAttribute<Double> {

    public RealCotationAttribute(String name) {
        super(name);
        formatter = new DoubleFormatter();
    }

    @Override
    public String getArffType() {
        return Constant.ARFF_TYPE_REAL;
    }
}
