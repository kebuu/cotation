package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class MobileMeanBuilderTests extends AbstractBuilderTests<SimpleMobileMeanBuilder> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, builder.getMobileMeanValueAttribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(8, builder.getMobileMeanValueAttribute()).get()).isEqualTo(10.0 / 3.0);
    }

    @Test
    public void testCreateBuilder() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Mobile mean range should be greater than 0");

        new SimpleMobileMeanBuilder(-1);
    }

    @Override
    protected SimpleMobileMeanBuilder createBuilder() {
        return new MobileMeanBuilder(3);
    }
}
