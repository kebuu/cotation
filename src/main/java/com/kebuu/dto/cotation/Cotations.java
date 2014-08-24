package com.kebuu.dto.cotation;

import com.kebuu.domain.Cotation;
import com.kebuu.misc.IndexedListWrapper;

public class Cotations extends IndexedListWrapper<Cotation, Integer> {

    public Cotations(Iterable<Cotation> iterable) {
        super(iterable, Cotation::getPosition);
    }
}
