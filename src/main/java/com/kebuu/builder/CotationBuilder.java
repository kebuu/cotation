package com.kebuu.builder;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public interface CotationBuilder {

    /**
     * Return the (wrapped) list of attributes built by this builder
     */
    CotationAttributes attributes();

    BuiltCotations build(Cotations cotations, BuiltCotations alreadyBuiltCotations);
}
