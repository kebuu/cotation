package com.kebuu.builder.impl;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.CrossingValuesStatus;

import java.util.Optional;

import static com.kebuu.enums.CrossingValuesStatus.*;

/**
 * Indicateur de croisement de deux moyennes mobiles.
 */
public class MobileMeansCrossingBuilder extends AbstractBuilder {

    private static final String MOBILE_MEANS_CROSSING_PREFIX_NAME = "mobile_means_crossing_";

    private final MobileMeanBuilder mobileMeanBuilder1;
    private final MobileMeanBuilder mobileMeanBuilder2;
    private final EnumeratedNominalCotationAttribute<CrossingValuesStatus> enumeratedNominalCotationAttribute;

    public MobileMeansCrossingBuilder(MobileMeanBuilder mobileMeanBuilder1, MobileMeanBuilder mobileMeanBuilder2) {
        this.mobileMeanBuilder1 = mobileMeanBuilder1;
        this.mobileMeanBuilder2 = mobileMeanBuilder2;

        String nominalCotationAttributeName = MOBILE_MEANS_CROSSING_PREFIX_NAME + mobileMeanBuilder1.getMobileMeanRange() + "_" + mobileMeanBuilder2.getMobileMeanRange();
        this.enumeratedNominalCotationAttribute = new EnumeratedNominalCotationAttribute<>(nominalCotationAttributeName, CrossingValuesStatus.class);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(enumeratedNominalCotationAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        Optional<Double> builder1ValueCurrentCotation = alreadyBuiltCotations.getValue(cotation.getPosition(), mobileMeanBuilder1.getMobileMeanValueAttribute());
        Optional<Double> builder2ValueCurrentCotation = alreadyBuiltCotations.getValue(cotation.getPosition(), mobileMeanBuilder2.getMobileMeanValueAttribute());
        Optional<Double> builder1ValuePreviousCotation = alreadyBuiltCotations.getValue(cotation.getPosition() - 1, mobileMeanBuilder1.getMobileMeanValueAttribute());
        Optional<Double> builder2ValuePreviousCotation = alreadyBuiltCotations.getValue(cotation.getPosition() - 1, mobileMeanBuilder2.getMobileMeanValueAttribute());

        SimpleCotationValue<CrossingValuesStatus> mobileMeansCrossingValue = new SimpleCotationValue<>(enumeratedNominalCotationAttribute);

        if (areNeededValuesPresent(builder1ValueCurrentCotation, builder2ValueCurrentCotation, builder1ValuePreviousCotation, builder2ValuePreviousCotation)) {
            mobileMeansCrossingValue = mobileMeansCrossingValue.withValue(NOT_CROSSING);

            Double mobileMeanRange1MinusMobileMeanRange2CurrentCotation = builder1ValueCurrentCotation.get() - builder2ValueCurrentCotation.get();
            Double mobileMeanRange1MinusMobileMeanRange2PreviousCotation = builder1ValuePreviousCotation.get() - builder2ValuePreviousCotation.get();

            if (Math.signum(mobileMeanRange1MinusMobileMeanRange2CurrentCotation) != Math.signum(mobileMeanRange1MinusMobileMeanRange2PreviousCotation)) {
                if (mobileMeanRange1MinusMobileMeanRange2CurrentCotation >= 0) {
                    mobileMeansCrossingValue = mobileMeansCrossingValue.withValue(FIRST_CROSSING_DOWN);
                } else {
                    mobileMeansCrossingValue = mobileMeansCrossingValue.withValue(FIRST_CROSSING_UP);
                }
            }
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeansCrossingValue);
    }

    private boolean areNeededValuesPresent(Optional<Double> builder1ValueCurrentCotation, Optional<Double> builder2ValueCurrentCotation, Optional<Double> builder1ValuePreviousCotation, Optional<Double> builder2ValuePreviousCotation) {
        return builder1ValueCurrentCotation.isPresent() && builder2ValueCurrentCotation.isPresent() && builder1ValuePreviousCotation.isPresent() && builder2ValuePreviousCotation.isPresent();
    }
}
