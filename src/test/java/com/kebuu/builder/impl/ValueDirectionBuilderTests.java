package com.kebuu.builder.impl;

import com.kebuu.builder.impl.relation.ValueDirectionBuilder;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.enums.Direction;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

public class ValueDirectionBuilderTests extends AbstractBuilderTests<ValueDirectionBuilder> {

    private static final Function<CotationBuilderInfo, Optional<Double>> TRANSFOMER = (CotationBuilderInfo x) -> Optional.of(x.getCotation().getEnd());

    @Test
    public void testWithNegativeDirectionStep() {
        builder = new ValueDirectionBuilder("test", TRANSFOMER);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, getAttribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(2, getAttribute()).get()).isEqualTo(Direction.NONE);
        Assertions.assertThat(builtCotations.getValue(6, getAttribute()).get()).isEqualTo(Direction.UP);
        Assertions.assertThat(builtCotations.getValue(8, getAttribute()).get()).isEqualTo(Direction.DOWN);
    }

    @Test
    public void testWithPositiveDirectionStep() {
        builder = new ValueDirectionBuilder("test", TRANSFOMER, 1, 0.0);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, getAttribute()).get()).isEqualTo(Direction.NONE);
        Assertions.assertThat(builtCotations.getValue(6, getAttribute()).get()).isEqualTo(Direction.UP);
        Assertions.assertThat(builtCotations.getValue(8, getAttribute()).get()).isEqualTo(Direction.DOWN);
        Assertions.assertThat(builtCotations.getValue(9, getAttribute()).isPresent()).isFalse();
    }

    @Test
    public void testWithPositiveDirectionStepWithThreshold() {
        builder = new ValueDirectionBuilder("test", TRANSFOMER, 1, 1.0);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(2, getAttribute()).get()).isEqualTo(Direction.NONE);
        Assertions.assertThat(builtCotations.getValue(3, getAttribute()).get()).isEqualTo(Direction.UP);
        Assertions.assertThat(builtCotations.getValue(4, getAttribute()).get()).isEqualTo(Direction.DOWN);
        Assertions.assertThat(builtCotations.getValue(5, getAttribute()).get()).isEqualTo(Direction.UP);
        Assertions.assertThat(builtCotations.getValue(8, getAttribute()).get()).isEqualTo(Direction.DOWN);
        Assertions.assertThat(builtCotations.getValue(9, getAttribute()).isPresent()).isFalse();
    }

    private CotationAttribute<Direction> getAttribute() {
        return builder.attribute();
    }
}
