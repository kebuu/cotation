package com.kebuu.service;

import com.kebuu.domain.EnhancedCotation;

public interface ArffService {

    String enhancedCotationsToArff(Iterable<EnhancedCotation> cotations);

    String timeSeriesToArff(Iterable<EnhancedCotation> enhancedCotations, int elementsInSerie);
}
