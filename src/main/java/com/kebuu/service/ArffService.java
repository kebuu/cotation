package com.kebuu.service;

import com.kebuu.domain.Cotation;
import com.kebuu.domain.TimeSeriesCotation;

import java.io.OutputStream;
import java.util.List;

public interface ArffService {

    void write(List<Cotation> cotations, OutputStream outputStream);

    void write(OutputStream outputStream);

    String toArff();

    String toArff(List<Cotation> cotations);

    String toArff(TimeSeriesCotation timeSeriesCotation);
}
