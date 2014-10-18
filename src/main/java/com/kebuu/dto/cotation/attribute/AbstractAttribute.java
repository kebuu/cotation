package com.kebuu.dto.cotation.attribute;

import com.kebuu.dto.cotation.formatter.CotationAttributeFormatter;
import com.kebuu.dto.cotation.formatter.DefaultFormatter;
import lombok.Getter;

@Getter
public abstract class AbstractAttribute<T> implements CotationAttribute<T> {

    protected final String name;
    protected CotationAttributeFormatter<T> formatter;

    protected AbstractAttribute(String name) {
        this(name, new DefaultFormatter<>());
    }

    protected AbstractAttribute(String name, CotationAttributeFormatter<T> formatter) {
        this.name = name;
        this.formatter = formatter;
    }
}
