package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractBuilderTests;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.enums.ValueComparisonPosition;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Optional;
import java.util.function.Function;

public class ValuePositionBuilderTests extends AbstractBuilderTests<ValuesPositionBuilder> {

    private static final Function<CotationBuilderInfo, Optional<Double>> TRANSFOMER1 = (CotationBuilderInfo x) -> Optional.of(x.getCotation().getEnd());
    private static final Function<CotationBuilderInfo, Optional<Double>> TRANSFOMER2 = (CotationBuilderInfo x) -> Optional.of(x.getCotation().getStart());

    @Test
    public void testWithNegativeDirectionStep() {
        builder = new ValuesPositionBuilder("test1", TRANSFOMER1, "test2", TRANSFOMER2);

        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, getAttribute()).get()).isEqualTo(ValueComparisonPosition.EQUAL_VALUES);
        Assertions.assertThat(builtCotations.getValue(4, getAttribute()).get()).isEqualTo(ValueComparisonPosition.FIRST_VALUE_UPPER);
        Assertions.assertThat(builtCotations.getValue(5, getAttribute()).get()).isEqualTo(ValueComparisonPosition.FIRST_VALUE_LOWER);
    }

    private CotationAttribute<ValueComparisonPosition> getAttribute() {
        return builder.attribute();
    }
}
