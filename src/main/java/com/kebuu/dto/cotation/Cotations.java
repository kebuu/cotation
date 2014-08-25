package com.kebuu.dto.cotation;

import com.kebuu.domain.Cotation;
import com.kebuu.misc.IndexedListWrapper;

import java.util.function.Function;

public class Cotations extends IndexedListWrapper<Cotation, Integer> {

    public Cotations(Iterable<Cotation> iterable) {
        super(iterable);
    }

    @Override
    protected Function<Cotation, Integer> getIndexFunction() {
        return Cotation::getPosition;
    }
}
