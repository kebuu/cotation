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

        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).get()).isEqualTo(1.0);
        Assertions.assertThat(builtCotations.getValue(4, builder.attribute()).get()).isEqualTo(1.0);
        Assertions.assertThat(builtCotations.getValue(5, builder.attribute()).get()).isEqualTo(-1.0);
        Assertions.assertThat(builtCotations.getValue(6, builder.attribute()).get()).isEqualTo(9.0);
    }

    @Test
    public void testBuildCotations_whenLowestEqualsHigest() {
        builder = new ChaikinMoneyFlowBuilder(2);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).get()).isEqualTo(1.0);
    }

    @Test
    public void testCreationBuilderFailed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Weighted mobile mean range should be greater than 0");

        new ChaikinMoneyFlowBuilder(0);
    }

    @Override
    protected ChaikinMoneyFlowBuilder createBuilder() {
        return new ChaikinMoneyFlowBuilder(1);
    }
}
