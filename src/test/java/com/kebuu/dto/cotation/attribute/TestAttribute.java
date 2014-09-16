package com.kebuu.dto.cotation.attribute;

public class TestAttribute<T> extends AbstractAttribute<T> {
    public TestAttribute() {
        super("test");
    }

    public String getArffType() {
        return "test";
    }
}
