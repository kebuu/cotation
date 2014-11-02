package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EaseOfMovementBuilderTests extends AbstractBuilderTests<EaseOfMovementBuilder> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(2, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).get()).isEqualTo(NumberUtils.DOUBLE_ZERO);
        Assertions.assertThat(builtCotations.getValue(4, builder.attribute()).get()).isEqualTo(10000.0 * (((2.0 + 1.0) - (1.0 + 1.0)) / 2.0) * (2.0 - 1.0) / 30.0);
        Assertions.assertThat(builtCotations.getValue(5, builder.attribute()).get()).isEqualTo(10000.0 * (((3.0 + 1.0) - (2.0 + 1.0)) / 2.0) * (3.0 - 1.0) / 40.0);
    }

    @Test
    public void testBuildCotations_withRange_1() {
        builder = new EaseOfMovementBuilder(2);
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(2, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(5, builder.attribute()).get()).isEqualTo((250.0 + 500.0 / 3.0) / 2.0);
    }

    @Override
    protected EaseOfMovementBuilder createBuilder() {
        return new EaseOfMovementBuilder(1);
    }
}
