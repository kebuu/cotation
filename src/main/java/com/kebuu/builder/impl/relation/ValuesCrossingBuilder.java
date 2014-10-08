package com.kebuu.builder.impl.relation;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.CrossingValuesStatus;

import java.util.Optional;

import static com.kebuu.enums.CrossingValuesStatus.*;

public class ValuesCrossingBuilder extends ValuesEnumRelationBuilder<CrossingValuesStatus> {

    public ValuesCrossingBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        super(attribute1, attribute2);
    }

    @Override
    protected Class<CrossingValuesStatus> getEnumClass() {
        return CrossingValuesStatus.class;
    }

    @Override
    protected String getAttributeNamePrefix() {
        return "value_direction_";
    }

    @Override
    public CotationValue<CrossingValuesStatus> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        Optional<Cotation> optionalPreviousCotation = cotationBuilderInfo.getPreviousCotation();

        Optional<Double> currentValue1 = getValue1(cotationBuilderInfo);
        Optional<Double> currentValue2 = getValue2(cotationBuilderInfo);
        Optional<Double> previousValue1 = optionalPreviousCotation
            .flatMap(previousCotation -> getValue1(cotationBuilderInfo.withCotation(previousCotation)));
        Optional<Double> previousValue2 = optionalPreviousCotation
            .flatMap(previousCotation -> getValue2(cotationBuilderInfo.withCotation(previousCotation)));

        SimpleCotationValue<CrossingValuesStatus> crossingValuesStatus = new SimpleCotationValue<>(attribute);

        if (areNeededValuesPresent(currentValue1, currentValue2, previousValue1, previousValue2)) {
            crossingValuesStatus = crossingValuesStatus.withValue(NOT_CROSSING);

            Double currentValuesDelta = currentValue1.get() - currentValue2.get();
            Double previousValuesDelta = previousValue1.get() - previousValue2.get();

            if (Math.signum(currentValuesDelta) != Math.signum(previousValuesDelta)) {
                if (currentValuesDelta >= 0) {
                    crossingValuesStatus = crossingValuesStatus.withValue(FIRST_CROSSING_DOWN);
                } else {
                    crossingValuesStatus = crossingValuesStatus.withValue(FIRST_CROSSING_UP);
                }
            }
        }

        return crossingValuesStatus;
    }

    private boolean areNeededValuesPresent(Optional<Double> builder1ValueCurrentCotation, Optional<Double> builder2ValueCurrentCotation, Optional<Double> builder1ValuePreviousCotation, Optional<Double> builder2ValuePreviousCotation) {
        return builder1ValueCurrentCotation.isPresent() && builder2ValueCurrentCotation.isPresent() && builder1ValuePreviousCotation.isPresent() && builder2ValuePreviousCotation.isPresent();
    }
}
