package com.kebuu.utils;

import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;

import java.util.Optional;
import java.util.function.Function;

public class FunctionUtils {

    private FunctionUtils() {
    }

    public static Function<CotationBuilderInfo, Optional<Double>> createValueTransformer(CotationAttribute<Double> attribute) {
        return (CotationBuilderInfo cotationBuilderInfo) -> cotationBuilderInfo.getAlreadyBuiltCotation().getValueByAttribute(attribute);
    }
}
