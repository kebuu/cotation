package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractBuilderTests;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.enums.CrossingValuesStatus;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

public class ValuesCrossingBuilderTests extends AbstractBuilderTests<ValuesCrossingBuilder> {

    private static final Function<CotationBuilderInfo, Optional<Double>> TRANSFOMER1 = (CotationBuilderInfo x) -> Optional.of(x.getCotation().getEnd());
    private static final Function<CotationBuilderInfo, Optional<Double>> TRANSFOMER2 = (CotationBuilderInfo x) -> Optional.of(x.getCotation().getStart());

    @Test
    public void simpleTest() {
        builder = new ValuesCrossingBuilder("test1", TRANSFOMER1, "test2", TRANSFOMER2);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, getAttribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(2, getAttribute()).get()).isEqualTo(CrossingValuesStatus.NOT_CROSSING);
        Assertions.assertThat(builtCotations.getValue(5, getAttribute()).get()).isEqualTo(CrossingValuesStatus.FIRST_CROSSING_DOWN);
        Assertions.assertThat(builtCotations.getValue(6, getAttribute()).get()).isEqualTo(CrossingValuesStatus.FIRST_CROSSING_UP);
    }

    @Override
    protected ValuesCrossingBuilder createBuilder() {
        return super.createBuilder();
    }

    private CotationAttribute<CrossingValuesStatus> getAttribute() {
        return builder.attribute();
    }
}
