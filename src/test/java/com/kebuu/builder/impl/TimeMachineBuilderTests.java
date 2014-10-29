package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotation;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.Cotations;
import com.kebuu.dto.cotation.attribute.LongCotationAttribute;
import com.kebuu.dto.cotation.value.SimpleCotationValue;
import com.kebuu.utils.StreamUtils;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.stream.Collectors;

public class TimeMachineBuilderTests extends AbstractBuilderTests<TimeMachineBuilder> {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private LongCotationAttribute baseAttribute = new LongCotationAttribute("baseAttribute");

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, fillAlreadyBuiltCotations(alreadyBuiltCotations, cotations));

        Assertions.assertThat(builtCotations.getValue(2, builder.attribute()).isPresent()).isFalse();
        Assertions.assertThat(builtCotations.getValue(3, builder.attribute()).get()).isEqualTo(0L);
        Assertions.assertThat(builtCotations.getValue(9, builder.attribute()).get()).isEqualTo(60L);
    }

    @Test
    public void testCreationBuilderFailed() {
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Time step should be greater than 0 (but note it represents a step back in past)");

        new TimeMachineBuilder(-1, null);
    }

    @Override
    protected TimeMachineBuilder createBuilder() {
        return new TimeMachineBuilder(2, baseAttribute);
    }

    private BuiltCotations fillAlreadyBuiltCotations(BuiltCotations alreadyBuiltCotations, Cotations cotations) {
        Iterable<BuiltCotation> builtCotations = StreamUtils.stream(cotations)
             .map(cotation -> new BuiltCotation(cotation).withAdditionalValues(new SimpleCotationValue(baseAttribute, cotation.getVolume())))
             .collect(Collectors.toList());

        return alreadyBuiltCotations.addAll(builtCotations);
    }
}
