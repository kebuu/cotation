package com.kebuu.builder.impl;

import com.kebuu.dto.cotation.BuiltCotations;
import org.junit.Test;

public class MobileMeanBuilderTests extends AbstractBuilderTests<MobileMeanBuilder> {

    @Test
    public void testBuildCotations() {
        BuiltCotations builtCotations = builder.build(cotations, alreadyBuiltCotations);
    }

    @Override
    protected MobileMeanBuilder createBuilder() {
        return new MobileMeanBuilder(3);
    }
}
