package com.kebuu.dto.cotation;

import com.kebuu.domain.Cotation;
import com.kebuu.misc.ListWrapper;

import java.util.Optional;

public class Cotations extends ListWrapper<Cotation> {

    public Cotations() {
    }

    public Cotations(Iterable<Cotation> iterable) {
        super(iterable);
    }

    public Optional<Cotation> getCotationAtPosition(int position) {
        return wrappedList.stream()
            .filter(cotation -> cotation.getPosition() == position)
            .findFirst();
    }
}
