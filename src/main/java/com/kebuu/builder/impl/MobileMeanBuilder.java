package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.NominalCotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.DoubleCotationValue;
import com.kebuu.dto.cotation.value.EmptyCotationValue;
import com.kebuu.dto.cotation.value.NominalCotationValue;
import com.kebuu.enums.IndicatorPosition;
import com.kebuu.utils.StreamUtils;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.stream.Collectors;

/**
 * La moyenne mobile à n séance, n étant > 0.
 * La moyenne mobile à 1 séance est la moyenne du cours d'aujourd'hui et du cours d'hier.
 */
public class MobileMeanBuilder extends AbstractBuilder {

    public static final String MOBILE_MEAN_PREFIX_NAME = "mobile_mean_";
    public static final String POSITIONPREFIX_NAME = "position_";

    private int mobileMeanRange;

    public MobileMeanBuilder(int mobileMeanRange) {
        Preconditions.checkArgument(mobileMeanRange > 0);
        this.mobileMeanRange = mobileMeanRange;
    }

    @Override
    public CotationAttributes attributes() {
        CotationAttributes attributes = new CotationAttributes();
        attributes.add(new RealCotationAttribute(MOBILE_MEAN_PREFIX_NAME + mobileMeanRange));
        attributes.add(new NominalCotationAttribute(MOBILE_MEAN_PREFIX_NAME + POSITIONPREFIX_NAME + mobileMeanRange, IndicatorPosition.class));
        return attributes;
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations) {
        DoubleSummaryStatistics mobileMeanSummary = StreamUtils.stream(cotations)
            .sorted(Comparator.comparingInt(Cotation::getPosition).reversed())
            .filter(currentCotation -> currentCotation.getPosition() <= cotation.getPosition())
            .limit(mobileMeanRange)
            .collect(Collectors.summarizingDouble(Cotation::getEnd));

        CotationValue mobileMeanValue = canCalculateMobileMean(mobileMeanSummary) ? new DoubleCotationValue(mobileMeanSummary.getAverage()) : EmptyCotationValue.INSTANCE;
        CotationValue nominalCotationValue = EmptyCotationValue.INSTANCE;

        if (canCalculateMobileMean(mobileMeanSummary)) {
            nominalCotationValue = new NominalCotationValue(IndicatorPosition.basedOn(cotation.getEnd(), mobileMeanSummary.getAverage()));
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeanValue, nominalCotationValue);
    }

    private boolean canCalculateMobileMean(DoubleSummaryStatistics mobileMeanSummary) {
        return mobileMeanSummary.getCount() >= mobileMeanRange;
    }
}
