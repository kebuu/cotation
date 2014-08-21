package com.kebuu.dto.cotation.formatter;

public class DefaultFormatter<T> extends AbstractFormatter<T> {

    @Override
    public String doFormat(T value) {
        return value.toString();
    }
}
