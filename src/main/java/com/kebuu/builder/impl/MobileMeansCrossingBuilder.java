package com.kebuu.builder.impl;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.NominalCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.MobileMeansCrossingStatus;

import static com.kebuu.enums.MobileMeansCrossingStatus.*;

/**
 * Indicateur de croisement de deux moyennes mobiles.
 * On considere qu'il y a signal d'achat lorsque
 */
public class MobileMeansCrossingBuilder extends AbstractBuilder {

    public static final String MOBILE_MEANS_CROSSING_PREFIX_NAME = "mobile_means_crossing_";

    private int mobileMeanRange1;
    private int mobileMeanRange2;
    private NominalCotationAttribute nominalCotationAttribute;

    public MobileMeansCrossingBuilder(int mobileMeanRange1, int mobileMeanRange2) {
        this.mobileMeanRange1 = mobileMeanRange1;
        this.mobileMeanRange2 = mobileMeanRange2;
        this.nominalCotationAttribute = new NominalCotationAttribute(MOBILE_MEANS_CROSSING_PREFIX_NAME + mobileMeanRange1 + "_" + mobileMeanRange1, MobileMeansCrossingStatus.class);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(nominalCotationAttribute);
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations) {
        builtCotations.getBuiltCotationAtPosition(cotation.getPosition()).get().getValues()
            .stream().filter(x -> true)
            .findFirst();

        Double mobileMeanRange1ValueCurrentCotation = null;
        Double mobileMeanRange2ValueCurrentCotation = null;
        Double mobileMeanRange1ValuePreviousCotation = null;
        Double mobileMeanRange2ValuePreviousCotation = null;

        Double mobileMeanRange1MinusMobileMeanRange2CurrentCotation = mobileMeanRange1ValueCurrentCotation - mobileMeanRange2ValueCurrentCotation;
        Double mobileMeanRange1MinusMobileMeanRange2PreviousCotation = mobileMeanRange1ValuePreviousCotation - mobileMeanRange2ValuePreviousCotation;

        SimpleCotationValue<MobileMeansCrossingStatus> mobileMeansCrossingValue = new SimpleCotationValue(nominalCotationAttribute, NOT_CROSSING);

        if (Math.signum(mobileMeanRange1MinusMobileMeanRange2CurrentCotation) != Math.signum(mobileMeanRange1MinusMobileMeanRange2PreviousCotation)) {
            if (mobileMeanRange1MinusMobileMeanRange2CurrentCotation >= 0) {
                mobileMeansCrossingValue = mobileMeansCrossingValue.withValue(FIRST_CROSSING_DOWN);
            } else {
                mobileMeansCrossingValue = mobileMeansCrossingValue.withValue(FIRST_CROSSING_UP);
            }
        }

        return new BuiltCotation(cotation).withAdditionalValues(mobileMeansCrossingValue);
    }
}
