package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.builder.CotationBuilder;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public class TimeSerieBuilder extends CompositeCotationBuilder {

    private final int serieSize;

    public TimeSerieBuilder(int serieSize, CotationBuilder... builders) {
        super(builders);
        Preconditions.checkArgument(serieSize >= 0, "You cannot choose a negative serie size");
        this.serieSize = serieSize;
    }

    @Override
    public CotationAttributes attributes() {
        //TODO
        return super.attributes();
    }

    @Override
    public BuiltCotations basedOn(Cotations cotations) {
        //TODO
        return super.basedOn(cotations);
    }
}
