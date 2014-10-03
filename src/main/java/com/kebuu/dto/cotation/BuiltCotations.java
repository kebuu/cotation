package com.kebuu.dto.cotation;

import com.google.common.base.Preconditions;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.value.CotationValue;
import com.kebuu.exception.NoBuiltCotationAtPositionException;
import com.kebuu.misc.IndexedListWrapper;
import com.kebuu.utils.StreamUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BuiltCotations extends IndexedListWrapper<BuiltCotation, Integer> {

    public BuiltCotations() {
        this(new ArrayList<>());
    }

    public BuiltCotations(Iterable<BuiltCotation> iterable) {
        super(iterable);
    }

    @Override
    protected Function<BuiltCotation, Integer> getIndexFunction() {
        return BuiltCotation::getPosition;
    }

    public static BuiltCotations merge(BuiltCotations builtCotations1, BuiltCotations builtCotations2) {
        Preconditions.checkArgument(builtCotations1.size() == builtCotations2.size(), "Both parameters have to have the same size to be merged. First parameter has size %s whereas the second has size %s", builtCotations1.size(), builtCotations2.size());

        List<BuiltCotation> mergedBuiltCotation = StreamUtils.stream(builtCotations1)
            .map(builtCotation1 -> {
                int builtCotationPosition = builtCotation1.getPosition();
                BuiltCotation builtCotation2 = builtCotations2.getByIndex(builtCotationPosition)
                    .orElseThrow(() -> new NoBuiltCotationAtPositionException(builtCotationPosition));
                return BuiltCotation.merge(builtCotation1, builtCotation2);
            }).collect(Collectors.toList());

        return new BuiltCotations(mergedBuiltCotation);
    }

    public <T> Optional<? extends CotationValue<T>> getCotationValue(int cotationPosition, CotationAttribute<T> attribute) {
        return getByIndex(cotationPosition)
               .flatMap(optionalBuiltCotation -> optionalBuiltCotation.getCotationValueByAttribute(attribute));
    }

    public <T> Optional<T> getValue(int cotationPosition, CotationAttribute<T> attribute) {
        return getCotationValue(cotationPosition, attribute).flatMap(CotationValue::getValue);
    }

    public <T> T forceGetValue(int cotationPosition, CotationAttribute<T> attribute) {
        return getValue(cotationPosition, attribute).get();
    }
}
