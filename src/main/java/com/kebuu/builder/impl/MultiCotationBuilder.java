package com.kebuu.builder.impl;

import com.kebuu.builder.CotationBuilder;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.misc.ListWrapper;

public class MultiCotationBuilder extends ListWrapper<CotationBuilder> {

    public MultiCotationBuilder(CotationBuilder... values) {
        super(values);
    }

    public CotationAttributes attributes() {
        return wrappedList.stream()
            .map(CotationBuilder::builtAttributes)
            .reduce(new CotationAttributes(), CotationAttributes::concat);
    }

    public BuiltCotations basedOn(Cotations cotations) {
        BuiltCotations aggregatedBuiltCotations = new BuiltCotations();

        for (CotationBuilder cotationBuilder : wrappedList) {
            BuiltCotations builtCotations = cotationBuilder.build(cotations, aggregatedBuiltCotations);

            if (aggregatedBuiltCotations.isEmpty()) {
                aggregatedBuiltCotations = builtCotations;
            } else {
                aggregatedBuiltCotations = BuiltCotations.merge(aggregatedBuiltCotations, builtCotations);
            }
        }

        return aggregatedBuiltCotations;
    }
}
