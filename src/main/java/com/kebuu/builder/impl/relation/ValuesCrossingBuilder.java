package com.kebuu.builder.impl.relation;

import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.CrossingValuesStatus;

import java.util.Optional;
import java.util.function.Function;

public class ValuesCrossingBuilder extends ValuesEnumRelationBuilder<CrossingValuesStatus> {

    public ValuesCrossingBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        super(attribute1, attribute2);
    }

    public ValuesCrossingBuilder(CotationAttribute<Double> attribute1, double constant) {
        super(attribute1, constant);
    }

    public ValuesCrossingBuilder(String value1Name, Function<CotationBuilderInfo, Optional<Double>> value1Transfomer, String value2Name, Function<CotationBuilderInfo, Optional<Double>> value2Transfomer) {
        super(value1Name, value1Transfomer, value2Name, value2Transfomer);
    }

    @Override
    protected Class<CrossingValuesStatus> getEnumClass() {
        return CrossingValuesStatus.class;
    }

    @Override
    protected String getAttributeNamePrefix() {
        return "crossing_";
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
            CrossingValuesStatus crossingValueStatus = CrossingValuesStatus.fromValues(previousValue1.get(), currentValue1.get(), previousValue2.get(), currentValue2.get()
            );
            crossingValuesStatus = crossingValuesStatus.withValue(crossingValueStatus);
        }

        return crossingValuesStatus;
    }

    private boolean areNeededValuesPresent(Optional<Double> builder1ValueCurrentCotation, Optional<Double> builder2ValueCurrentCotation, Optional<Double> builder1ValuePreviousCotation, Optional<Double> builder2ValuePreviousCotation) {
        return builder1ValueCurrentCotation.isPresent() && builder2ValueCurrentCotation.isPresent() && builder1ValuePreviousCotation.isPresent() && builder2ValuePreviousCotation.isPresent();
    }
}
