package com.kebuu.builder.impl;

import com.kebuu.builder.CotationBuilder;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.misc.ListWrapper;

public class MultiCotationBuilder extends ListWrapper<CotationBuilder> {

    public CotationAttributes attributes() {
        return wrappedList.stream()
            .map(CotationBuilder::attributes)
            .reduce(new CotationAttributes(), CotationAttributes::concat);
    }

    public BuiltCotations basedOn(Cotations cotations) {
        return wrappedList.stream()
           .map(improver -> improver.build(cotations))
           .reduce(BuiltCotations::merge).orElse(new BuiltCotations());
    }
}
