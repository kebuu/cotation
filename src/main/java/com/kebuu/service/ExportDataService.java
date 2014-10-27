package com.kebuu.service;

import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;

public interface ExportDataService {

    String toCsv(CotationAttributes attributes, BuiltCotations builtCotations);

    String ToArff(CotationAttributes attributes, BuiltCotations builtCotations);
}
