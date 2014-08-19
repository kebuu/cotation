package com.kebuu.dto.cotation.attribute;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class CotationAttribute {

    private String name;
    
    public abstract String getArffType();
}
