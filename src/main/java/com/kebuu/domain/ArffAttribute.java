package com.kebuu.domain;

import lombok.Value;

import java.util.Optional;

@Value
public class ArffAttribute {

    public static final String ATTRIBUTE = "@attribute";

    private String name;
    private String type;

    public String toText(Optional<String> attributeSuffix) {
        return ATTRIBUTE + " " + name + attributeSuffix.map(x -> "_" + x).orElse("") + " " + type;
    }

    public String toText() {
        return toText(Optional.empty());
    }
}
