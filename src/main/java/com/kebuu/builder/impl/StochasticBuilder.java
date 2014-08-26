package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import lombok.Getter;

/**
 * Calcul du stochastic : %K = 100 * (end - plusBas) / (plusHaut - plusBas)
 */
public class StochasticBuilder extends AbstractBuilder {

    public static final String STOCHASTIC_PREFIX_NAME = "stochastic_";

    public static final int DEFAULT_PERIOD = 14;

    @Getter private final int period;
    @Getter private final RealCotationAttribute stochasticValueAttribute;

    public StochasticBuilder(int period) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;

        this.stochasticValueAttribute = new RealCotationAttribute(STOCHASTIC_PREFIX_NAME + period);
    }

    public StochasticBuilder() {
        this(DEFAULT_PERIOD);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes();
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {


        return new BuiltCotation(cotation);
    }
}
