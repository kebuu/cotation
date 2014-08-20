package com.kebuu.dto.cotation.attribute;

import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.formatter.NominalFormatter;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NominalCotationAttribute extends AbstractAttribute<Enum<? extends Enum<?>>> {

    private Class<? extends Enum<? extends Enum<?>>> enumClass;

    public NominalCotationAttribute(String name, Class<? extends Enum<? extends Enum<?>>> enumClass) {
        super(name);
        this.enumClass = enumClass;
        this.formatter = new NominalFormatter();
    }

    @Override
    public String getArffType() {
        return Arrays.asList(enumClass.getEnumConstants()).stream()
           .map(Enum::name)
           .collect(Collectors.joining(Constant.ARFF_TYPE_NOMINAL_VALUE_SEPARATOR, Constant.ARFF_TYPE_NOMINAL_PREFIX, Constant.ARFF_TYPE_NOMINAL_SUFFIX));
    }
}

