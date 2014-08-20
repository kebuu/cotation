package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.NominalCotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.DoubleCotationValue;
import com.kebuu.dto.cotation.value.NominalCotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
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
    private RealCotationAttribute mobileMeanValueAttribute;
    private NominalCotationAttribute mobileMeanPositionAttribute;

    public MobileMeanBuilder(int mobileMeanRange) {
        Preconditions.checkArgument(mobileMeanRange > 0);

        this.mobileMeanRange = mobileMeanRange;
        this.mobileMeanValueAttribute = new RealCotationAttribute(MOBILE_MEAN_PREFIX_NAME + mobileMeanRange);
        this.mobileMeanPositionAttribute = new NominalCotationAttribute(MOBILE_MEAN_PREFIX_NAME + POSITIONPREFIX_NAME + mobileMeanRange, IndicatorPosition.class);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(mobileMeanValueAttribute, mobileMeanPositionAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations) {
        DoubleSummaryStatistics mobileMeanSummary = StreamUtils.stream(cotations)
            .sorted(Comparator.comparingInt(Cotation::getPosition).reversed())
            .filter(currentCotation -> currentCotation.getPosition() <= cotation.getPosition())
            .limit(mobileMeanRange)
            .collect(Collectors.summarizingDouble(Cotation::getEnd));

        SimpleCotationValue<Double> mobileMeanValue = new DoubleCotationValue(mobileMeanValueAttribute);
        SimpleCotationValue mobileMeanPosition = new NominalCotationValue(mobileMeanPositionAttribute);

        if(canCalculateMobileMean(mobileMeanSummary)) {
            mobileMeanValue = mobileMeanValue.withValue(mobileMeanSummary.getAverage());
            mobileMeanPosition = mobileMeanPosition.withValue(IndicatorPosition.basedOn(cotation.getEnd(), mobileMeanSummary.getAverage()));
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeanValue, mobileMeanPosition);
    }

    private boolean canCalculateMobileMean(DoubleSummaryStatistics mobileMeanSummary) {
        return mobileMeanSummary.getCount() >= mobileMeanRange;
    }
}
