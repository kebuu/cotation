package com.kebuu.dto.cotation;

import com.google.common.base.Preconditions;
import com.kebuu.exception.NoBuiltCotationAtPosition;
import com.kebuu.misc.ListWrapper;
import com.kebuu.utils.StreamUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class BuiltCotations extends ListWrapper<BuiltCotation> {

    public BuiltCotations() {
    }

    public BuiltCotations(Iterable<BuiltCotation> iterable) {
        super(iterable);
    }

    public static BuiltCotations merge(BuiltCotations builtCotations1, BuiltCotations builtCotations2) {
        Preconditions.checkArgument(builtCotations1.size() == builtCotations2.size(), "Both parameters have to have the same size to be merged. First parameter has size %s whereas the second has size %s", builtCotations1.size(), builtCotations2.size());

        List<BuiltCotation> mergedBuiltCotation = StreamUtils.stream(builtCotations1)
            .map(builtCotation1 -> {
                int builtCotationPosition = builtCotation1.getPosition();
                BuiltCotation builtCotation2 = builtCotations2.getBuiltCotationAtPosition(builtCotationPosition).orElseThrow(NoBuiltCotationAtPosition.from(builtCotationPosition));
                return BuiltCotation.merge(builtCotation1, builtCotation2);
            }).collect(Collectors.toList());

        return new BuiltCotations(mergedBuiltCotation);
    }

    public Optional<BuiltCotation> getBuiltCotationAtPosition(int position) {
        return wrappedList.stream()
                .filter(builtCotation -> builtCotation.getPosition() == position)
                .findFirst();
    }
}
