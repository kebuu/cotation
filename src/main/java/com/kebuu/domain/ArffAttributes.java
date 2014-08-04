package com.kebuu.domain;

import com.google.common.collect.Lists;
import lombok.Value;
import org.apache.commons.io.IOUtils;

import java.util.List;

import static java.util.stream.Collectors.joining;

@Value
public class ArffAttributes<T> {

    public static final String VALUE_SEPARATOR = ",";

    private List<ArffAttribute<T>> attributes = Lists.newArrayList();

    public String toValueText(List<T> valueBeans) {
        return valueBeans.stream()
            .map(valueBean -> {
                return attributes.stream()
                   .map(attribute -> attribute.toValueText(valueBean))
                   .collect(joining(VALUE_SEPARATOR));
            })
            .collect(joining(VALUE_SEPARATOR));
    }

    public String toHeaderText() {
        return attributes.stream()
           .map(ArffAttribute::toHeaderText)
           .collect(joining(IOUtils.LINE_SEPARATOR));
    }
}