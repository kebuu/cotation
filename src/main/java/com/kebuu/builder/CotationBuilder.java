package com.kebuu.builder;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public interface CotationBuilder {

    CotationAttributes builtAttributes();

    BuiltCotations build(Cotations cotations, BuiltCotations alreadyBuiltCotations);
}
