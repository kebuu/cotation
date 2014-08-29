package com.kebuu.dto.cotation.attribute;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.kebuu.constant.Constant;
import lombok.Getter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ListedNominalCotationAttribute<T> extends AbstractAttribute<T> {

    @Getter private final Set<T> possibleValues;

    public ListedNominalCotationAttribute(String name, T... possibleValues) {
        this(name, Arrays.asList(possibleValues));
    }

    public ListedNominalCotationAttribute(String name, Iterable<T> possibleValues) {
        super(name);
        Preconditions.checkArgument(Iterables.size(possibleValues) > 0, "You should specified at least one possibleValues");
        this.possibleValues = ImmutableSet.copyOf(possibleValues);
    }

    @Override
    public String getArffType() {
        return possibleValues.stream()
           .map(Object::toString)
           .collect(Collectors.joining(Constant.ARFF_TYPE_NOMINAL_VALUE_SEPARATOR, Constant.ARFF_TYPE_NOMINAL_PREFIX, Constant.ARFF_TYPE_NOMINAL_SUFFIX));
    }
}

