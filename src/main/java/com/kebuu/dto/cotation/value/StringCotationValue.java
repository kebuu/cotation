package com.kebuu.dto.cotation.value;

import lombok.Value;

@Value
public class StringCotationValue implements CotationValue {

    private String value;

    @Override
    public String getValueAsText() {
        return value;
    }
}
