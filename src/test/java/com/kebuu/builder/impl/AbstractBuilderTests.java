package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.test.DataForTests;
import org.junit.Before;

public abstract class AbstractBuilderTests<T extends AbstractBuilder> {

    protected final Cotations cotations = new Cotations(DataForTests.cotations());
    protected final BuiltCotations alreadyBuiltCotations = new BuiltCotations();
    protected T builder;

    @Before
    public void beforeAbstractBuilderTests() {
        builder = createBuilder();
    }

    protected Double forceGetValue(BuiltCotations builtCotations, int position, CotationAttribute<Double> attribute) {
        return builtCotations.getCotationValue(position, attribute).get().forceGetValue();
    }

    protected abstract T createBuilder();
}
