package com.kebuu.builder.impl;

import com.google.common.base.Preconditions;
import com.kebuu.domain.Cotation;
import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.dto.cotation.attribute.NominalCotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.enums.Direction;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class NextDaysEndDirectionBuilder extends AbstractBuilder {

    public static final String MOBILE_MEANS_CROSSING_PREFIX_NAME = "next_days_end_status_";

    private final SortedSet<Integer> nextDays;
    private final Map<Integer, CotationAttribute<Direction>> builtAttributesByNextDay = new LinkedHashMap<>();

    public NextDaysEndDirectionBuilder(SortedSet<Integer> nextDays) {
        Preconditions.checkArgument(!nextDays.isEmpty(), "You should chose at least one next day value");
        Preconditions.checkArgument(nextDays.stream().allMatch(x -> x > 0), "All should be higher than 0");

        this.nextDays = nextDays;
        this.nextDays.forEach(nextDay -> {
            builtAttributesByNextDay.put(nextDay, createAttribute(nextDay));
        });
    }

    public NextDaysEndDirectionBuilder(IntStream nextDays) {
        this(nextDays.boxed().collect(toCollection(TreeSet::new)));
    }

    public NextDaysEndDirectionBuilder(int... nextDays) {
        this(IntStream.of(nextDays));
    }

    @Override
    public CotationAttributes builtAttributes() {
        return new CotationAttributes(builtAttributesByNextDay.values());
    }

    @Override
    public BuiltCotation build(Cotation cotation, Cotations cotations, BuiltCotations builtCotations, BuiltCotations alreadyBuiltCotations) {
        List<CotationValue> cotationValues = nextDays.stream()
            .map(nextDayStep -> createCotationValue(cotation, cotations, nextDayStep))
            .collect(toList());

        return new BuiltCotation(cotation).withAdditionalValues(cotationValues);
    }

    public CotationAttribute<Direction> createAttribute(Integer nextDay) {
        return new NominalCotationAttribute<>(MOBILE_MEANS_CROSSING_PREFIX_NAME + nextDay, Direction.class);
    }

    private CotationValue<Direction> createCotationValue(Cotation cotation, Cotations cotations, int nextDay) {
        SimpleCotationValue<Direction> futureCotationEndValue = new SimpleCotationValue<>(builtAttributesByNextDay.get(nextDay));

        Double currentCotationEnd = cotation.getEnd();
        Optional<Cotation> futureCotationEnd = cotations.getByIndex(cotation.getPosition() + nextDay);

        if (futureCotationEnd.isPresent()) {
            futureCotationEndValue = futureCotationEndValue.withValue(Direction.fromConsecutiveValues(futureCotationEnd.get().getEnd(), currentCotationEnd));
        }

        return futureCotationEndValue;
    }
}
