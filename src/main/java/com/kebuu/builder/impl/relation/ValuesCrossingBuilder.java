package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractBuilder;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.CrossingValuesStatus;

import java.util.Optional;
import java.util.function.Function;

import static com.kebuu.enums.CrossingValuesStatus.*;

public class ValuesCrossingBuilder extends AbstractBuilder {

    public static final String ATTRIBUTE_NAME_PREFIX = "values_crossing_";

    private final EnumeratedNominalCotationAttribute<CrossingValuesStatus> attribute;

    private final Function<BuiltCotation, Optional<Double>> value1Transfomer;
    private final Function<BuiltCotation, Optional<Double>> value2Transfomer;
    private final String value1Name;
    private final String value2Name;

    public ValuesCrossingBuilder(String value1Name, Function<BuiltCotation, Optional<Double>> value1Transfomer, String value2Name, Function<BuiltCotation, Optional<Double>> value2Transfomer) {
        this.value1Transfomer = value1Transfomer;
        this.value2Transfomer = value2Transfomer;
        this.value1Name = value1Name;
        this.value2Name = value2Name;

        attribute = new EnumeratedNominalCotationAttribute<>(ATTRIBUTE_NAME_PREFIX + value1Name + "_" + value2Name, CrossingValuesStatus.class);
    }

    public ValuesCrossingBuilder(String value1Name, Function<BuiltCotation, Optional<Double>> value1Transfomer, Double constantValue) {
        this(value1Name, value1Transfomer, constantValue.toString(), constantValue);
    }

    public ValuesCrossingBuilder(String value1Name, Function<BuiltCotation, Optional<Double>> value1Transfomer, String value2Name, Double constantValue) {
        this(value1Name, value1Transfomer, value2Name, (BuiltCotation x) -> Optional.of(constantValue));
    }

    public ValuesCrossingBuilder(CotationAttribute<Double> attribute1, CotationAttribute<Double> attribute2) {
        this(attribute1.getName(), createValueTransformer(attribute1), attribute2.getName(), createValueTransformer(attribute2));
    }

    public ValuesCrossingBuilder(CotationAttribute<Double> attribute1, Double constantValue) {
        this(attribute1.getName(), createValueTransformer(attribute1), constantValue);
    }

    private static Function<BuiltCotation, Optional<Double>> createValueTransformer(CotationAttribute<Double> attribute) {
        return (BuiltCotation x) -> x.getValueByAttribute(attribute).get().getValue();
    }

    @Override
    protected BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        Optional<Double> currentValue1 = alreadyBuiltCotations.getByIndex(cotation.getPosition()).flatMap(value1Transfomer);
        Optional<Double> currentValue2 = alreadyBuiltCotations.getByIndex(cotation.getPosition()).flatMap(value2Transfomer);
        Optional<Double> previousValue1 = alreadyBuiltCotations.getByIndex(cotation.getPosition() - 1).flatMap(value1Transfomer);
        Optional<Double> previousValue2 = alreadyBuiltCotations.getByIndex(cotation.getPosition() - 1).flatMap(value2Transfomer);

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

        return new BuiltCotation(cotation).withAdditionalValues(crossingValuesStatus);
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(attribute);
    }

    private boolean areNeededValuesPresent(Optional<Double> builder1ValueCurrentCotation, Optional<Double> builder2ValueCurrentCotation, Optional<Double> builder1ValuePreviousCotation, Optional<Double> builder2ValuePreviousCotation) {
        return builder1ValueCurrentCotation.isPresent() && builder2ValueCurrentCotation.isPresent() && builder1ValuePreviousCotation.isPresent() && builder2ValuePreviousCotation.isPresent();
    }
}
