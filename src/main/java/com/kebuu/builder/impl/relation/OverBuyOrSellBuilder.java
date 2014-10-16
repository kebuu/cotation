package com.kebuu.builder.impl.relation;

import com.google.common.base.Preconditions;
import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.OverBuyOrSellStatus;

import java.util.Optional;

public class OverBuyOrSellBuilder extends AbstractSingleAttributeBuilder<OverBuyOrSellStatus> {

    private static final String PREFIX_NAME = "over_buy_or_sell_";

    private final double overBuyThreshold;
    private final double overSellThreshold;
    private final EnumeratedNominalCotationAttribute<OverBuyOrSellStatus> attribute;
    private final CotationAttribute<Double> baseAttribute;

    public OverBuyOrSellBuilder(CotationAttribute<Double> baseAttribute, double overBuyThreshold, double overSellThreshold) {
        Preconditions.checkArgument(overBuyThreshold > overSellThreshold, "The overbuy threshold should be higher than oversell threshold");

        this.overBuyThreshold = overBuyThreshold;
        this.overSellThreshold = overSellThreshold;
        this.baseAttribute = baseAttribute;
        this.attribute = new EnumeratedNominalCotationAttribute<>(PREFIX_NAME + baseAttribute.getName(), OverBuyOrSellStatus.class);
    }

    @Override
    public CotationAttribute<OverBuyOrSellStatus> attribute() {
        return attribute;
    }

    @Override
    public CotationValue<OverBuyOrSellStatus> calculateSingleValue(CotationBuilderInfo cotationBuilderInfo) {
        SimpleCotationValue<OverBuyOrSellStatus> overBuyOrSellStatus = new SimpleCotationValue<>(attribute);

        Optional<Double> baseAttributeValue = cotationBuilderInfo.getAlreadyBuiltCotations().getValue(cotationBuilderInfo.position(), baseAttribute);

        if (baseAttributeValue.isPresent()) {
            overBuyOrSellStatus = overBuyOrSellStatus.withValue(OverBuyOrSellStatus.fromThresholds(baseAttributeValue.get(), overBuyThreshold, overSellThreshold));
        }

        return overBuyOrSellStatus;
    }
}
