package com.kebuu.builder.impl;

import com.kebuu.builder.CotationBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.Cotations;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Slf4j
public abstract class AbstractBuilder implements CotationBuilder {

    @Override
    public BuiltCotations build(Cotations cotations, BuiltCotations alreadyBuiltCotations) {
        log.info("Building with builder : " + this);
        BuiltCotations builtCotations = new BuiltCotations();

        for (Cotation cotation : cotations) {
            BuiltCotation builtCotation = build(new CotationBuilderInfo(cotation, cotations, builtCotations, alreadyBuiltCotations));
            builtCotations.add(builtCotation);
        }

        return builtCotations;
    }

    protected abstract BuiltCotation build(CotationBuilderInfo cotationBuilderInfo);

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
