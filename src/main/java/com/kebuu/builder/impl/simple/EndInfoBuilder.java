package com.kebuu.builder.impl.simple;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;

public class EndInfoBuilder extends SimpleCotationInfoBuilder<Double> {

    private static final String ATTRIBUTE_NAME = "base_cotation_end";
    private final RealCotationAttribute endCotationAttribute = new RealCotationAttribute(ATTRIBUTE_NAME);

    public EndInfoBuilder() {
        super(Cotation::getEnd);
    }

    @Override
    public CotationAttribute<Double> getSingleAttribute() {
        return endCotationAttribute;
    }
}
