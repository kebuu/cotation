package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.enums.Direction;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class NextDaysEndDirectionBuilderTests extends AbstractBuilderTests<NextDaysEndDirectionBuilder> {

    private static final Integer THREE = 3;
    private static final Integer FOUR = 4;

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, getAttribute(THREE)).get()).isEqualTo(Direction.UP);
        Assertions.assertThat(builtCotations.getValue(1, getAttribute(FOUR)).get()).isEqualTo(Direction.NONE);
        Assertions.assertThat(builtCotations.getValue(6, getAttribute(THREE)).get()).isEqualTo(Direction.DOWN);
        Assertions.assertThat(builtCotations.getValue(6, getAttribute(FOUR)).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(7, getAttribute(THREE)).isPresent()).isFalse();
    }

    private CotationAttribute<Direction> getAttribute(Integer period) {
        return builder.getBuiltAttributesByNextDay().get(period);
    }

    @Override
    protected NextDaysEndDirectionBuilder createBuilder() {
        return new NextDaysEndDirectionBuilder(THREE, FOUR);
    }
}
