package com.kebuu.builder.impl.simple;

import com.kebuu.builder.impl.AbstractBuilderTests;
import com.kebuu.dto.cotation.BuiltCotations;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EndInfoBuilderTests extends AbstractBuilderTests<EndInfoBuilder> {

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);
        assertThat(forceGetValue(builtCotations, 1, builder.getAttribute())).isEqualTo(1.0);
        assertThat(forceGetValue(builtCotations, 4, builder.getAttribute())).isEqualTo(2.0);
    }

    @Override
    protected EndInfoBuilder createBuilder() {
        return new EndInfoBuilder();
    }
}
