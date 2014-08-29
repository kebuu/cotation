package com.kebuu.builder.impl.simple;

import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.EnumeratedNominalCotationAttribute;
import org.joda.time.DateTime;

import java.time.DayOfWeek;

public class DayOfWeekInfoBuilder extends SimpleCotationInfoBuilder<DayOfWeek> {

    private static final String ATTRIBUTE_NAME = "day";

    private final EnumeratedNominalCotationAttribute<DayOfWeek> dayCotationAttribute = new EnumeratedNominalCotationAttribute<>(ATTRIBUTE_NAME, DayOfWeek.class);

    public DayOfWeekInfoBuilder() {
        super(cotation -> DayOfWeek.of(new DateTime(cotation.getDate()).getDayOfWeek()));
    }

    @Override
    public CotationAttribute<DayOfWeek> getAttribute() {
        return dayCotationAttribute;
    }
}
