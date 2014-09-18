package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StochasticBuilderTests extends AbstractBuilderTests<StochasticBuilder> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testBuildCotations_withPeriod2() {
        builder = new StochasticBuilder(2);
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(2, builder.getStochasticValueAttribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(3, builder.getStochasticValueAttribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(4, builder.getStochasticValueAttribute()).get()).isEqualTo(100.0);
        Assertions.assertThat(builtCotations.getValue(8, builder.getStochasticValueAttribute()).get()).isEqualTo(0.0);
    }

    @Test
    public void testBuildCotations_withPeriod3() {
        builder = new StochasticBuilder(3);
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(8, builder.getStochasticValueAttribute()).get()).isEqualTo(100.0 * (3.0 - 1.0) / (4.0 - 1.0));
    }

    @Test
    public void testCreationBuilderFailed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Period should be greater than 0");

        new StochasticBuilder(0);
    }

    @Override
    protected StochasticBuilder createBuilder() {
        return null;
    }
}
