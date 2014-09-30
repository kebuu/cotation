package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class RocBuilderTests extends AbstractBuilderTests<RocBuilder> {

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(3, builder.getRocValueAttribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(4, builder.getRocValueAttribute()).get()).isEqualTo(100.0);
        Assertions.assertThat(builtCotations.getValue(9, builder.getRocValueAttribute()).get()).isEqualTo(-200.0 / 3.0);
    }

    @Override
    protected RocBuilder createBuilder() {
        return new RocBuilder(3);
    }
}
