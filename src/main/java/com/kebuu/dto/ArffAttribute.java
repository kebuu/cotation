package com.kebuu.dto;

import lombok.Value;

import java.util.Optional;
import java.util.function.Function;

@Value
public class ArffAttribute<T> {

    public static final String ATTRIBUTE = "@attribute";
    public static final String MISSING_VALUE = "?";

    private String name;
    private String type;
    private Function<T, Object> valueExtractor;

    public String toHeaderText(Optional<String> attributeSuffix) {
        return ATTRIBUTE + " " + name + attributeSuffix.orElse("") + " " + type;
    }

    public String toValueText(T valueBean) {
        return Optional.ofNullable(valueExtractor.apply(valueBean))
            .map(Object::toString)
            .orElse(MISSING_VALUE);
    }
}
