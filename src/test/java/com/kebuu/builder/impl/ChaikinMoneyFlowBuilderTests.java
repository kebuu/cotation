package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ChaikinMoneyFlowBuilderTests extends AbstractBuilderTests<ChaikinMoneyFlowBuilder> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(4, builder.attribute()).get()).isEqualTo((30.0 * (2.0 * 2.0 - 1.0 - 2.0)) / ((2.0 - 1.0) * 60.0));
    }

    @Test
    public void testBuildCotations_whenLowestEqualsHigest() {
        builder = new ChaikinMoneyFlowBuilder(1);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).get()).isEqualTo(20.0 / 30.0);
    }

    @Test
    public void testCreationBuilderFailed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Period should be greater or equals to 0");

        new ChaikinMoneyFlowBuilder(-1);
    }

    @Override
    protected ChaikinMoneyFlowBuilder createBuilder() {
        return new ChaikinMoneyFlowBuilder(3);
    }
}
