package com.kebuu.dto.cotation;

import com.kebuu.domain.Cotation;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.Optional;

@Value
@Wither
public class CotationBuilderInfo {

    private Cotation cotation;
    private Cotations cotations;
    private BuiltCotations builtCotations;
    private BuiltCotations alreadyBuiltCotations;

    public int position() {
        return cotation.getPosition();
    }

    public CotationBuilderInfo withBuiltCotation(BuiltCotation builtCotation) {
        return this.withBuiltCotations(this.getBuiltCotations().copy().add(builtCotation));
    }

    public BuiltCotation getAlreadyBuiltCotation() {
        return alreadyBuiltCotations.forceGetByIndex(cotation.getPosition());
    }

    public Optional<Cotation> getPreviousCotation() {
        return cotations.getByIndex(cotation.getPosition() - 1);
    }
}
