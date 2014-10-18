package com.kebuu.utils;

import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;
import java.util.function.Function;

public abstract class FunctionUtils {

    private FunctionUtils() {}

    public static <T> Function<CotationBuilderInfo, Optional<T>> transformerFromAttribute(CotationAttribute<T> attribute) {
        return (CotationBuilderInfo cotationBuilderInfo) -> cotationBuilderInfo.getAlreadyBuiltCotation().getValueByAttribute(attribute);
    }
}
