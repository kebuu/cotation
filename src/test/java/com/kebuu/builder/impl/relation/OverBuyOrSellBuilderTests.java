package com.kebuu.builder.impl.relation;

import com.kebuu.builder.impl.AbstractBuilderTests;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.CotationBuilderInfo;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.enums.OverBuyOrSellStatus;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Optional;
import java.util.function.Function;

public class OverBuyOrSellBuilderTests extends AbstractBuilderTests<OverBuyOrSellBuilder> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private static final Function<CotationBuilderInfo, Optional<Double>> TRANSFOMER1 = (CotationBuilderInfo x) -> Optional.of(x.getCotation().getEnd());

    @Test
    public void testSimple() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);

        Assertions.assertThat(builtCotations.getValue(1, getAttribute()).get()).isEqualTo(OverBuyOrSellStatus.OVER_SELL);
        Assertions.assertThat(builtCotations.getValue(7, getAttribute()).get()).isEqualTo(OverBuyOrSellStatus.OVER_BUY);
        Assertions.assertThat(builtCotations.getValue(6, getAttribute()).get()).isEqualTo(OverBuyOrSellStatus.NOTHING);
    }

    @Test
    public void testPrecondition() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("The overbuy threshold (1.5) should be higher than oversell threshold (2.5)");
        new OverBuyOrSellBuilder("test", TRANSFOMER1, 1.5, 2.5);
    }

    @Override
    protected OverBuyOrSellBuilder createBuilder() {
        return new OverBuyOrSellBuilder("test", TRANSFOMER1, 3.5, 2.5);
    }

    private CotationAttribute<OverBuyOrSellStatus> getAttribute() {
        return builder.attribute();
    }
}
