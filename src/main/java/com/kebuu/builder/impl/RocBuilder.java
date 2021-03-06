package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

import java.util.Optional;

/**
 * Calcul du Rate Of Change : ROC(i) = 100 * ((cours(i) / cours(i - period)) - 1)
 */
public class RocBuilder extends AbstractSingleAttributeBuilder<Double> {

    private static final String ROC_PREFIX_NAME = "roc_";
    private static final int DEFAULT_PERIOD = 12;

    @Getter private final int period;
    @Getter private final RealCotationAttribute rocValueAttribute;

    public RocBuilder(int period) {
        Preconditions.checkArgument(period > 0, "Period should be greater than 0");
        this.period = period;

        this.rocValueAttribute = new RealCotationAttribute(ROC_PREFIX_NAME + period);
    }

    public RocBuilder() {
        this(DEFAULT_PERIOD);
    }

    @Override
    public CotationAttribute<Double> attribute() {
        return rocValueAttribute;
    }

    @Override
    public CotationValue<Double> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<Double> rocValue = new SimpleCotationValue<>(rocValueAttribute);

        Cotation cotation = cotationBuilderInfo.getCotation();

        Optional<Cotation> cotationAtPeriod = cotationBuilderInfo.getCotations().getByIndex(cotation.getPosition() - period);

        if (cotationAtPeriod.isPresent()) {
            rocValue = rocValue.withValue(calculateRocValue(cotation.getEnd(), cotationAtPeriod.get().getEnd()));
        }

        return rocValue;
    }

    private double calculateRocValue(double currentValue, double valueAtPeriod) {
        return 100.0 * ((currentValue / valueAtPeriod) - 1.0);
    }
}
