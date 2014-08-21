package com.kebuu.dto.cotation.attribute;

import com.kebuu.dto.cotation.formatter.CotationAttributeFormatter;

public interface CotationAttribute<T> {

    CotationAttributeFormatter<T> getFormatter();

    String getName();
    
    String getArffType();
}
