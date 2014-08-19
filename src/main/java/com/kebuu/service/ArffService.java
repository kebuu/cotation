package com.kebuu.service;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public interface ArffService {

    String ToArff(CotationAttributes attributes, BuiltCotations builtCotations);
}
