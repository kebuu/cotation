package com.kebuu.dto.arff;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.io.IOUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class ArffAttributes<T> {

    public static final String VALUE_SEPARATOR = ",";

    private List<ArffAttribute<T>> attributes = Lists.newArrayList();

    public ArffAttributes(ArffAttribute<T>... arffAttributes) {
        attributes = ImmutableList.copyOf(arffAttributes);
    }

    public List<String> toValueTextLines(Iterable<T> valueBeans) {
        return StreamSupport.stream(valueBeans.spliterator(), false)
            .map(valueBean -> {
                return attributes.stream()
                               .map(attribute -> attribute.toValueText(valueBean))
                               .collect(joining(VALUE_SEPARATOR));
            })
            .collect(toList());
    }

    public String toHeaderText(Optional<String> attributeSuffix) {
        return attributes.stream()
           .map(x -> x.toHeaderText(attributeSuffix))
           .collect(joining(IOUtils.LINE_SEPARATOR));
    }

    public String toHeaderText() {
        return toHeaderText(Optional.empty());
    }
}