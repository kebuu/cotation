package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public class TimeSerieBuilder extends AbstractBuilder {

    private final int serieSize;

    public TimeSerieBuilder(int serieSize) {
        Preconditions.checkArgument(serieSize > 0, "You need to choose a positive serie size");
        this.serieSize = serieSize;
    }

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        BuiltCotation builtCotation = alreadyBuiltCotations.getByIndex(cotation.getPosition()).get();



        return new BuiltCotation(cotation);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes();
    }
}
