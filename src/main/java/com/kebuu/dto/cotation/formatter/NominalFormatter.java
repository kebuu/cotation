package com.kebuu.dto.cotation.formatter;

public class NominalFormatter<T extends Enum<? extends Enum<?>>> extends AbstractFormatter<T> {

    @Override
    public String doFormat(T value) {
        return value.name();
    }
}
