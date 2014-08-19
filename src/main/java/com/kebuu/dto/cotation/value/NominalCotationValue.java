package com.kebuu.dto.cotation.value;

import lombok.Value;

@Value
public class NominalCotationValue implements CotationValue {

    private Enum<? extends Enum<?>> value;

    @Override
    public String getValueAsText() {
        return value.name();
    }
}
