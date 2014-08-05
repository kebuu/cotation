package com.kebuu.service;

import com.kebuu.domain.EnhancedCotation;
import com.kebuu.dto.TimeSerie;

public interface ArffService {

    String enhancedCotationsToArff(Iterable<EnhancedCotation> cotations);

    String timeSeriesToArff(Iterable<TimeSerie<EnhancedCotation>> timeSerieCotation);
}
