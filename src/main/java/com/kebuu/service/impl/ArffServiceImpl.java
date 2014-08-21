package com.kebuu.service.impl;

import com.google.common.base.Joiner;
import com.kebuu.dto.arff.ArffAttributes;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.factory.TimeSerieFactory;
import com.kebuu.service.ArffService;
import com.kebuu.utils.StreamUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArffServiceImpl implements ArffService {

    public static final String DATA_TAG = "@data";
    public static final String ATTRIBUTE_TAG = "@attribute";
    public static final String RELATION_TAG = "@relation";

    @Autowired private ArffAttributes enhancedCotationAttributes;
    @Autowired private TimeSerieFactory timeSerieFactory;

//    @Override
//    public String timeSeriesToArff(Iterable<EnhancedCotation> enhancedCotations, int elementsInSerie) {
//        Iterable<TimeSerie<EnhancedCotation>> timeSerieCotation = timeSerieFactory.toTimeSeries(enhancedCotations, elementsInSerie);
//
//        List<String> headerLines = IntStream.range(0, elementsInSerie)
//            .mapToObj(x -> enhancedCotationAttributes.toHeaderText(Optional.of("_" + x)))
//            .collect(Collectors.toList());
//
//        List<String> valueLines = StreamSupport.stream(timeSerieCotation.spliterator(), false)
//            .map(timeSerie -> {
//                List valueTextLines = enhancedCotationAttributes.toValueTextLines(timeSerie.getData());
//                return Joiner.on(ArffAttributes.VALUE_SEPARATOR).join(valueTextLines);
//            })
//            .collect(Collectors.toList());
//
//
//        return buildArff(headerLines, valueLines);
//    }

    @Override
    public String ToArff(CotationAttributes attributes, BuiltCotations builtCotations) {
        List<String> headerLines = StreamUtils.stream(attributes)
                .map(cotationAttribute -> ATTRIBUTE_TAG + " " + cotationAttribute.getName() + " " + cotationAttribute.getArffType())
                .collect(Collectors.toList());

        List<String> valueLines = StreamUtils.stream(builtCotations)
                .map(builtCotation -> {
                    String valueLine = builtCotation.getValues().stream()
                       .map(cotationValue -> cotationValue.getAttribute().getFormatter().format(cotationValue))
                       .collect(Collectors.joining(ArffAttributes.VALUE_SEPARATOR));
                    return valueLine;
                })
                .collect(Collectors.toList());

        return buildArff(headerLines, valueLines);
    }

    private String buildArff(List<String> headerLines, List<String> valueLines) {
        return RELATION_TAG + " \"Cotations\"" +
               IOUtils.LINE_SEPARATOR +
               Joiner.on(IOUtils.LINE_SEPARATOR).join(headerLines) +
               IOUtils.LINE_SEPARATOR +
               DATA_TAG + IOUtils.LINE_SEPARATOR +
               Joiner.on(IOUtils.LINE_SEPARATOR).join(valueLines);
    }
}
