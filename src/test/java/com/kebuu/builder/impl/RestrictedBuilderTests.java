package com.kebuu.builder.impl;

import com.google.common.collect.Lists;
import com.kebuu.builder.impl.simple.ConstantBuilder;
import com.kebuu.dto.cotation.BuiltCotations;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class RestrictedBuilderTests extends AbstractBuilderTests<RestrictedValueBuilder<Integer>> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testBuildCotations_Error() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Current value '0' is not in listed possible values [1, 2, 3]");

        builder = new RestrictedValueBuilder<>(ConstantBuilder.of(0), Lists.newArrayList(1, 2, 3));
        builder.build(cotations, alreadyBuiltCotations);
    }

    @Test
    public void testBuildCotations_OK() {
        builder = new RestrictedValueBuilder<>(ConstantBuilder.of(0), Lists.newArrayList(0, 1, 2, 3));
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        assertThat(forceGetValue(builtCotations, 1, builder.getSingleAttribute())).isEqualTo(0);
        assertThat(forceGetValue(builtCotations, 2, builder.getSingleAttribute())).isEqualTo(0);
    }

    @Override
    protected RestrictedValueBuilder createBuilder() {
        return null;
    }
}
