package com.kebuu.dto.cotation;

import com.kebuu.domain.Cotation;
import lombok.Value;
import lombok.experimental.Wither;

@Value
@Wither
public class CotationBuilderInfo {

    private Cotation cotation;
    private Cotations cotations;
    private BuiltCotations builtCotations;
    private BuiltCotations alreadyBuiltCotations;
}
