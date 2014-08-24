package com.kebuu.builder.impl;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.LongCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;

public class VolumeInfoBuilder extends AbstractBuilder {

    public static final String BASE_COTATION_VOLUME_ATTRIBUTE_NAME = "base_cotation_volume";

    private final LongCotationAttribute volumeCotationAttribute = new LongCotationAttribute(BASE_COTATION_VOLUME_ATTRIBUTE_NAME);

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        return new BuiltCotation(cotation).withAdditionalValues(new SimpleCotationValue<>(volumeCotationAttribute, cotation.getVolume()));
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(volumeCotationAttribute);
    }
}
