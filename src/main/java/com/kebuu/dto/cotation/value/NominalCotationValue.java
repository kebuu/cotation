package com.kebuu.dto.cotation.value;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

public class NominalCotationValue extends SimpleCotationValue<Enum<? extends Enum<?>>> {

    public NominalCotationValue(CotationAttribute<Enum<? extends Enum<?>>> attribute, Enum<? extends Enum<?>> value) {
        super(attribute, value);
    }

    public NominalCotationValue(CotationAttribute<Enum<? extends Enum<?>>> attribute) {
        super(attribute);
    }
}
