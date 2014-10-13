package com.kebuu.builder.impl.mobilemean;

import com.kebuu.builder.impl.AbstractBuilderTests;
import com.kebuu.dto.cotation.BuiltCotations;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class SimplifiedExponentialMobileMeanBuilderTests extends AbstractBuilderTests<SimplifiedExponentialMobileMeanBuilder> {

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(4, builder.attribute()).get()).isEqualTo(1.1428571428571428);
        Assertions.assertThat(builtCotations.getValue(5, builder.attribute()).get()).isEqualTo(1.0714285714285714);
    }

    @Override
    protected SimplifiedExponentialMobileMeanBuilder createBuilder() {
        return new SimplifiedExponentialMobileMeanBuilder(3);
    }
}
