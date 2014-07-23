package com.kebuu.service;

import com.kebuu.domain.Cotation;
import com.kebuu.domain.TimeSeriesCotation;
import org.apache.commons.vfs2.FileObject;

import java.util.List;

public interface ArffService {

    void writeToFile(List<Cotation> cotations, FileObject fileObject);

    void writeToFile(FileObject fileObject);

    String toArff();

    String toArff(List<Cotation> cotations);

    String toArff(TimeSeriesCotation timeSeriesCotation);
}
