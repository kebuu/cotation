package com.kebuu.builder.impl;

import com.kebuu.builder.impl.simple.EndInfoBuilder;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.enums.ValueDeltaMode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ValueDeltaBuilderTests extends AbstractBuilderTests<ValueDeltaBuilder> {

    private ValueDeltaBuilder testedBuilder;

    @Test
    public void testBuild_withRawValue() {
        CompositeCotationBuilder compositeCotationBuilder = createCompositeBuilder(ValueDeltaMode.RAW);
        BuiltCotations builtCotations = compositeCotationBuilder.basedOn(cotations);

        Assertions.assertThat(builtCotations.getValue(1, testedBuilder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(2, testedBuilder.attribute()).get()).isEqualTo(0.0);
        Assertions.assertThat(builtCotations.getValue(6, testedBuilder.attribute()).get()).isEqualTo(2.0);
        Assertions.assertThat(builtCotations.getValue(8, testedBuilder.attribute()).get()).isEqualTo(-1.0);
    }

    @Test
    public void testBuild_withPercentValue() {
        CompositeCotationBuilder compositeCotationBuilder = createCompositeBuilder(ValueDeltaMode.PERCENT);
        BuiltCotations builtCotations = compositeCotationBuilder.basedOn(cotations);

        Assertions.assertThat(builtCotations.getValue(1, testedBuilder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(2, testedBuilder.attribute()).get()).isEqualTo(0.0);
        Assertions.assertThat(builtCotations.getValue(6, testedBuilder.attribute()).get()).isEqualTo(200.0);
        Assertions.assertThat(builtCotations.getValue(8, testedBuilder.attribute()).get()).isEqualTo(-25.0);
    }

    private CompositeCotationBuilder createCompositeBuilder(ValueDeltaMode mode) {
        EndInfoBuilder baseBuilder = new EndInfoBuilder();
        testedBuilder = new ValueDeltaBuilder(baseBuilder.attribute(), -1, mode);
        return new CompositeCotationBuilder(baseBuilder, testedBuilder);
    }
}
