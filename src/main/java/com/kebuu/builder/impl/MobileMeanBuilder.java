package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.attribute.RealCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.Direction;
import com.kebuu.enums.IndicatorPosition;
import com.kebuu.utils.StreamUtils;
import lombok.Getter;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * La moyenne mobile à n séance, n étant > 0.
 * La moyenne mobile à 1 séance est la moyenne du cours d'aujourd'hui et du cours d'hier.
 */
public class MobileMeanBuilder extends AbstractBuilder {

    private static final String MOBILE_MEAN_PREFIX_NAME = "mobile_mean_";
    private static final String POSITION_PREFIX_NAME = "position_";
    private static final String DIRECTION_PREFIX_NAME = "direction_";

    @Getter private final int mobileMeanRange;
    @Getter private final RealCotationAttribute mobileMeanValueAttribute;
    @Getter private final EnumeratedNominalCotationAttribute<IndicatorPosition> mobileMeanPositionAttribute;
    @Getter private final EnumeratedNominalCotationAttribute<Direction> mobileMeanDirectionAttribute;

    public MobileMeanBuilder(int mobileMeanRange) {
        Preconditions.checkArgument(mobileMeanRange > 0, "Mobile mean range should be greater than 0");

        this.mobileMeanRange = mobileMeanRange;
        this.mobileMeanValueAttribute = new RealCotationAttribute(MOBILE_MEAN_PREFIX_NAME + mobileMeanRange);
        this.mobileMeanPositionAttribute = new EnumeratedNominalCotationAttribute<>(MOBILE_MEAN_PREFIX_NAME + POSITION_PREFIX_NAME + mobileMeanRange, IndicatorPosition.class);
        this.mobileMeanDirectionAttribute = new EnumeratedNominalCotationAttribute<>(MOBILE_MEAN_PREFIX_NAME + DIRECTION_PREFIX_NAME + mobileMeanRange, Direction.class);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(mobileMeanValueAttribute, mobileMeanPositionAttribute, mobileMeanDirectionAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        SimpleCotationValue<Double> mobileMeanValue = new SimpleCotationValue<>(mobileMeanValueAttribute);
        SimpleCotationValue<IndicatorPosition> mobileMeanPosition = new SimpleCotationValue<>(mobileMeanPositionAttribute);
        SimpleCotationValue<Direction> mobileMeanDirection = new SimpleCotationValue<>(mobileMeanDirectionAttribute);

        Optional<Double> previousMobileMeanValue = builtCotations.getValue(cotation.getPosition() - 1, mobileMeanValueAttribute);

        if (previousMobileMeanValue.isPresent()) {
            Cotation lastCotationUsedToCalculatePreviousMobileMean = cotations.getByIndex(cotation.getPosition() - (mobileMeanRange + 1)).get();
            mobileMeanValue = mobileMeanValue.withValue(previousMobileMeanValue.get() + ((cotation.getEnd() - lastCotationUsedToCalculatePreviousMobileMean.getEnd()) / (double)mobileMeanRange));
            mobileMeanDirection = mobileMeanDirection.withValue(Direction.fromConsecutiveValues(previousMobileMeanValue.get(), mobileMeanValue.unwrapValue()));
        } else if (cotations.getByIndex(cotation.getPosition() - mobileMeanRange).isPresent()) {
            DoubleSummaryStatistics mobileMeanSummary = StreamUtils.stream(cotations)
                .sorted(Comparator.comparingInt(Cotation::getPosition).reversed())
                .filter(currentCotation -> currentCotation.getPosition() <= cotation.getPosition())
                .limit(mobileMeanRange)
                .collect(Collectors.summarizingDouble(Cotation::getEnd));

            mobileMeanValue = mobileMeanValue.withValue(mobileMeanSummary.getAverage());
        }

        if (mobileMeanValue.hasValue()) {
            mobileMeanPosition = mobileMeanPosition.withValue(IndicatorPosition.basedOn(cotation.getEnd(), mobileMeanValue.unwrapValue()));
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeanValue, mobileMeanPosition, mobileMeanDirection);
    }
}
