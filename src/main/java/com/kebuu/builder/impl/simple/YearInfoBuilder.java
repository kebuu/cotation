package com.kebuu.builder.impl.simple;

import com.kebuu.builder.impl.AbstractBuilder;
import com.kebuu.builder.impl.RestrictedBuilder;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.ListedNominalCotationAttribute;
import com.kebuu.dto.cotation.attribute.LongCotationAttribute;
import org.joda.time.DateTime;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class YearInfoBuilder extends RestrictedBuilder<Long> {

    private static final List<Long> DEFAULT_YEAR_RANGE = LongStream.rangeClosed(1990, 2020).boxed().collect(Collectors.toList());

    public YearInfoBuilder(AbstractBuilder delegate, ListedNominalCotationAttribute<Long> listedAttribute) {
        super(delegate, listedAttribute);
    }

    public YearInfoBuilder(ListedNominalCotationAttribute<Long> listedAttribute) {
        this(new InnerYearInfoBuilder(), listedAttribute);
    }

    public YearInfoBuilder() {
        this(new ListedNominalCotationAttribute<Long>(InnerYearInfoBuilder.ATTRIBUTE_NAME, DEFAULT_YEAR_RANGE));
    }

    private static class InnerYearInfoBuilder extends SimpleCotationInfoBuilder<Long> {

        private static final String ATTRIBUTE_NAME = "year";

        private final LongCotationAttribute yearCotationAttribute = new LongCotationAttribute(ATTRIBUTE_NAME);

        protected InnerYearInfoBuilder() {
            super(cotation -> (long)new DateTime(cotation.getDate()).getYear());
        }

        @Override
        public CotationAttribute<Long> getAttribute() {
            return yearCotationAttribute;
        }
    }
}
