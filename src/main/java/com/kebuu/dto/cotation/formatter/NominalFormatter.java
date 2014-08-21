package com.kebuu.dto.cotation.formatter;

public class NominalFormatter extends AbstractFormatter<Enum<? extends Enum<?>>> {

    @Override
    public String doFormat(Enum<? extends Enum<?>> value) {
        return value.name();
    }
}
