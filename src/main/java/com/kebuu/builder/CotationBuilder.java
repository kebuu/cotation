package com.kebuu.builder;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public interface CotationBuilder {

    CotationAttributes attributes();

    BuiltCotations build(Cotations cotations);

    BuiltCotation build(Cotation cotation, Cotations cotations);
}
