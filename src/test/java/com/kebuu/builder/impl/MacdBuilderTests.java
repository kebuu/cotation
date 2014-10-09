package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.utils.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;

public class MacdBuilderTests extends AbstractBuilderTests<MacdBuilder> {

    private static final int NB_OF_DECIMAL = 6;

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(getRoundedValue(builtCotations, 1).isPresent()).isFalse();
        Assertions.assertThat(getRoundedValue(builtCotations, 4).get()).isEqualTo(0.257143);
        Assertions.assertThat(getRoundedValue(builtCotations, 9).get()).isEqualTo(-0.286769);
    }

    private Optional<Double> getRoundedValue(BuiltCotations builtCotations, int position) {
        return builtCotations.getValue(position, builder.getMacdValueAttribute()).map(value -> NumberUtils.round(value, NB_OF_DECIMAL));
    }

    @Override
    protected MacdBuilder createBuilder() {
        return new MacdBuilder(2, 3, 3);
    }
}
