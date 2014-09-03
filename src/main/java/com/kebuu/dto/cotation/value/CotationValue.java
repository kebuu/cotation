package com.kebuu.dto.cotation.value;

import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;

public interface CotationValue<T> {

    Optional<T> getValue();

    CotationAttribute<T> getAttribute();

    default T forceGetValue() {
        return getValue().get();
    }
}
