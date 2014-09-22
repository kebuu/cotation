package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import lombok.Getter;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * La moyenne mobile à n séance, n étant > 0.
 * La moyenne mobile à 1 séance est les cours d'aujourd'hui, la moyenne mobile à 2 séances la moyenne du cours d'aujourd'hui et du cours d'hier.
 */
public class MobileMeanBuilder extends AbstractBuilder {

    private static final String MOBILE_MEAN_PREFIX_NAME = "mobile_mean_";

    @Getter private final int mobileMeanRange;
    @Getter private final RealCotationAttribute mobileMeanValueAttribute;

    public MobileMeanBuilder(int mobileMeanRange) {
        Preconditions.checkArgument(mobileMeanRange > 0, "Mobile mean range should be greater than 0");

        this.mobileMeanRange = mobileMeanRange;
        this.mobileMeanValueAttribute = new RealCotationAttribute(MOBILE_MEAN_PREFIX_NAME + mobileMeanRange);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(mobileMeanValueAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> mobileMeanValue = new SimpleCotationValue<>(mobileMeanValueAttribute);

        Optional<Double> previousMobileMeanValue = builtCotations.getValue(cotation.getPosition() - 1, mobileMeanValueAttribute);

        if (previousMobileMeanValue.isPresent()) {
            Cotation lastCotationUsedToCalculatePreviousMobileMean = cotations.getByIndex(cotation.getPosition() - (mobileMeanRange + 1)).get();
            mobileMeanValue = mobileMeanValue.withValue(previousMobileMeanValue.get() + ((cotation.getEnd() - lastCotationUsedToCalculatePreviousMobileMean.getEnd()) / (double)mobileMeanRange));
        } else if (cotations.getByIndex(cotation.getPosition() - mobileMeanRange).isPresent()) {
            Double mobileMeanDoubleValue = IntStream.range(0, mobileMeanRange)
               .mapToObj(i -> cotations.getByIndex(cotation.getPosition() - i).get())
               .collect(Collectors.averagingDouble(Cotation::getEnd));

            mobileMeanValue = mobileMeanValue.withValue(mobileMeanDoubleValue);
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeanValue);
    }
}
