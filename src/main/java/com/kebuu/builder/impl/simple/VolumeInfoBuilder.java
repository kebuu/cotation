package com.kebuu.builder.impl.simple;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.LongCotationAttribute;

public class VolumeInfoBuilder extends SimpleCotationInfoBuilder<Long> {

    private static final String ATTRIBUTE_NAME = "base_cotation_volume";
    private final LongCotationAttribute volumeCotationAttribute = new LongCotationAttribute(ATTRIBUTE_NAME);

    public VolumeInfoBuilder() {
        super(Cotation::getVolume);
    }

    @Override
    public CotationAttribute<Long> getAttribute() {
        return volumeCotationAttribute;
    }
}
