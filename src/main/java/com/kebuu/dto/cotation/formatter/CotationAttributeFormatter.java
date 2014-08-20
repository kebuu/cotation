package com.kebuu.dto.cotation.formatter;

import com.kebuu.dto.cotation.value.CotationValue;

public interface CotationAttributeFormatter<T> {

    String format(CotationValue<T> cotationValue);
}
