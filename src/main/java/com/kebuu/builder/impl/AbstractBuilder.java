package com.kebuu.builder.impl;

import com.kebuu.builder.CotationBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;

public abstract class AbstractBuilder implements CotationBuilder {

    @Override
    public BuiltCotations build(Cotations cotations) {
        BuiltCotations builtCotations = new BuiltCotations();

        for (Cotation cotation : cotations) {
            BuiltCotation builtCotation = build(cotation, cotations, builtCotations);
            builtCotations.add(builtCotation);
        }

        return builtCotations;
    }

    protected abstract BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations);
}
