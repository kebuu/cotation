package com.kebuu.service.impl;

import com.google.common.base.Joiner;
import com.kebuu.domain.EnhancedCotation;
import com.kebuu.dto.ArffAttributes;
import com.kebuu.dto.TimeSerie;
import com.kebuu.factory.TimeSerieFactory;
import com.kebuu.service.ArffService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

@Service
public class ArffServiceImpl implements ArffService {

    public static final String DATA_TAG = "@data";
    public static final String RELATION_TAG = "@relation";

    @Autowired private ArffAttributes enhancedCotationAttributes;
    @Autowired private TimeSerieFactory timeSerieFactory;

    @Override
    @SneakyThrows
    public String enhancedCotationsToArff(Iterable<EnhancedCotation> cotations) {
        return buildArff(enhancedCotationAttributes.toHeaderText(), enhancedCotationAttributes.toValueTextLines(cotations));
    }

    @Override
    public String timeSeriesToArff(Iterable<EnhancedCotation> enhancedCotations, int elementsInSerie) {
        Iterable<TimeSerie<EnhancedCotation>> timeSerieCotation = timeSerieFactory.toTimeSeries(enhancedCotations, elementsInSerie);

        String header = IntStream.range(0, elementsInSerie)
            .mapToObj(x -> enhancedCotationAttributes.toHeaderText(Optional.of("_" + x)))
            .collect(Collectors.joining(IOUtils.LINE_SEPARATOR));

        List<String> valueLines = StreamSupport.stream(timeSerieCotation.spliterator(), false)
            .map(timeSerie -> {
                List valueTextLines = enhancedCotationAttributes.toValueTextLines(timeSerie.getData());
                return Joiner.on(ArffAttributes.VALUE_SEPARATOR).join(valueTextLines);
            })
            .collect(Collectors.toList());


        return buildArff(header, valueLines);
    }

    private String buildArff(String header, List<String> valueLines) {
        return RELATION_TAG + " \"Cotations\"" +
               IOUtils.LINE_SEPARATOR +
               header +
               IOUtils.LINE_SEPARATOR +
               DATA_TAG + IOUtils.LINE_SEPARATOR +
               Joiner.on(IOUtils.LINE_SEPARATOR).join(valueLines);
    }
}
