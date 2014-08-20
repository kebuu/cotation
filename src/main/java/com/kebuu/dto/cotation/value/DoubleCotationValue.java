package com.kebuu.dto.cotation.value;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

public class DoubleCotationValue extends SimpleCotationValue<Double> {

    public DoubleCotationValue(CotationAttribute<Double> attribute, Double value) {
        super(attribute, value);
    }

    public DoubleCotationValue(CotationAttribute<Double> attribute) {
        super(attribute);
    }
}
