package com.kebuu.dto.cotation.attribute;

import com.kebuu.dto.cotation.formatter.CotationAttributeFormatter;
import com.kebuu.dto.cotation.formatter.DefaultFormatter;
import lombok.Getter;

@Getter
public abstract class AbstractAttribute<T>  implements CotationAttribute<T> {

    protected final String name;
    protected final AttributeMode mode;
    protected CotationAttributeFormatter<T> formatter;

    protected AbstractAttribute(String name) {
        this(name, new DefaultFormatter<>(), AttributeMode.NORMAL);
    }

    protected AbstractAttribute(String name, AttributeMode mode) {
        this(name, new DefaultFormatter<>(), mode);
    }

    protected AbstractAttribute(String name, CotationAttributeFormatter<T> formatter) {
        this(name, formatter, AttributeMode.NORMAL);
    }

    protected AbstractAttribute(String name, CotationAttributeFormatter<T> formatter, AttributeMode mode) {
        this.name = name;
        this.formatter = formatter;
        this.mode = mode;
    }

    @Override
    public boolean isTechnical() {
        return AttributeMode.TECHNICAL.equals(mode);
    }
}
