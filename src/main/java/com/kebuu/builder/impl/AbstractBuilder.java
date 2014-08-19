package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.builder.CotationBuilder;
import com.kebuu.utils.StreamUtils;

public abstract class AbstractBuilder implements CotationBuilder {

    @Override
    public BuiltCotations build(Cotations cotations) {
        return StreamUtils.stream(cotations)
            .map(cotation -> build(cotation, cotations))
            .reduce(
                new BuiltCotations(),
                (improvedCotations, improvedCotation) -> improvedCotations.add(improvedCotation),
                BuiltCotations::concat
            );
    }
}
