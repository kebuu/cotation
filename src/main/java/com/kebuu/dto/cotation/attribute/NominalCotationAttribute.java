package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.formatter.NominalFormatter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NominalCotationAttribute<T extends Enum<? extends Enum<?>>> extends AbstractAttribute<T> {

    private final Class<T> enumClass;

    public NominalCotationAttribute(String name, Class<T> enumClass) {
        super(name);
        this.enumClass = enumClass;
        this.formatter = new NominalFormatter<>();
    }

    @Override
    public String getArffType() {
        return Arrays.asList(enumClass.getEnumConstants()).stream()
           .map(Enum::name)
           .collect(Collectors.joining(Constant.ARFF_TYPE_NOMINAL_VALUE_SEPARATOR, Constant.ARFF_TYPE_NOMINAL_PREFIX, Constant.ARFF_TYPE_NOMINAL_SUFFIX));
    }
}

