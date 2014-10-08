package com.kebuu.builder.impl.simple;

import com.kebuu.builder.impl.RestrictedValueBuilder;
import com.kebuu.builder.impl.AbstractSingleAttributeBuilder;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.LongCotationAttribute;
import org.joda.time.DateTime;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class YearInfoValueBuilder extends RestrictedValueBuilder<Long> {

    private static final List<Long> DEFAULT_YEAR_RANGE = LongStream.rangeClosed(1990, 2020).boxed().collect(Collectors.toList());

    public YearInfoValueBuilder(AbstractSingleAttributeBuilder<Long> delegate, Iterable<Long> restrictedValues) {
        super(delegate, restrictedValues);
    }

    public YearInfoValueBuilder(Iterable<Long> listedAttribute) {
        this(new InnerYearInfoBuilder(), listedAttribute);
    }

    public YearInfoValueBuilder() {
        this(DEFAULT_YEAR_RANGE);
    }

    private static class InnerYearInfoBuilder extends SimpleCotationInfoBuilder<Long> {

        private static final String ATTRIBUTE_NAME = "year";

        private final LongCotationAttribute yearCotationAttribute = new LongCotationAttribute(ATTRIBUTE_NAME);

        protected InnerYearInfoBuilder() {
            super(cotation -> (long)new DateTime(cotation.getDate()).getYear());
        }

        @Override
        public CotationAttribute<Long> attribute() {
            return yearCotationAttribute;
        }
    }
}
