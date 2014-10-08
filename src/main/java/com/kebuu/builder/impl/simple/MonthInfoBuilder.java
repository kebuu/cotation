package com.kebuu.builder.impl.simple;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import org.joda.time.DateTime;

import java.time.Month;

public class MonthInfoBuilder extends SimpleCotationInfoBuilder<Month> {

    private static final String ATTRIBUTE_NAME = "month";

    private final EnumeratedNominalCotationAttribute<Month> monthCotationAttribute = new EnumeratedNominalCotationAttribute<>(ATTRIBUTE_NAME, Month.class);

    public MonthInfoBuilder() {
        super(cotation -> Month.of(new DateTime(cotation.getDate()).getMonthOfYear()));
    }

    @Override
    public CotationAttribute<Month> attribute() {
        return monthCotationAttribute;
    }
}
