package com.kebuu.builder.impl.simple;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.LongCotationAttribute;
import org.joda.time.DateTime;

public class DayOfMonthInfoBuilder extends SimpleCotationInfoBuilder<Long> {

    private static final String ATTRIBUTE_NAME = "day_of_month";
    private final LongCotationAttribute dayOfMonthCotationAttribute = new LongCotationAttribute(ATTRIBUTE_NAME);

    public DayOfMonthInfoBuilder() {
        super(cotation -> (long) new DateTime(cotation.getDate()).getDayOfMonth());
    }

    @Override
    public CotationAttribute<Long> getSingleAttribute() {
        return dayOfMonthCotationAttribute;
    }
}
