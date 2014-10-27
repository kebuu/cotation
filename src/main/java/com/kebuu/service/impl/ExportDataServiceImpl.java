package com.kebuu.service.impl;

import com.google.common.base.Joiner;
import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttribute;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.service.ExportDataService;
import com.kebuu.utils.StreamUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExportDataServiceImpl implements ExportDataService {

    @Override
    public String toCsv(CotationAttributes attributes, BuiltCotations builtCotations) {
        String header = StreamUtils.stream(attributes)
            .filter(cotationAttribute -> !cotationAttribute.isTechnical())
            .map(CotationAttribute::getName)
            .collect(Collectors.joining(","));

        return buildCsv(header, getValuesLines(builtCotations));
    }

    @Override
    public String ToArff(CotationAttributes attributes, BuiltCotations builtCotations) {
        List<String> headerLines = StreamUtils.stream(attributes)
            .filter(cotationAttribute -> !cotationAttribute.isTechnical())
            .map(cotationAttribute -> Constant.ARFF_ATTRIBUTE_TAG + " " + cotationAttribute.getName() + " " + cotationAttribute.getArffType())
            .collect(Collectors.toList());

        return buildArff(headerLines, getValuesLines(builtCotations));
    }

    private List<String> getValuesLines(BuiltCotations builtCotations) {
        return (List<String>) StreamUtils.stream(builtCotations)
                .map(builtCotation -> {
                    return builtCotation.getValues().stream()
                       .filter(cotationValue -> !cotationValue.getAttribute().isTechnical())
                       .map(cotationValue -> cotationValue.getAttribute().getFormatter().format(cotationValue))
                       .collect(Collectors.joining(Constant.VALUE_SEPARATOR));
                })
                .collect(Collectors.toList());
    }

    private String buildCsv(String header, List<String> valueLines) {
        return header + IOUtils.LINE_SEPARATOR +
                       Joiner.on(IOUtils.LINE_SEPARATOR).join(valueLines);
    }

    private String buildArff(List<String> headerLines, List<String> valueLines) {
        return Constant.ARFF_RELATION_TAG + " \"Cotations\"" +
                       IOUtils.LINE_SEPARATOR +
                       Joiner.on(IOUtils.LINE_SEPARATOR).join(headerLines) +
                       IOUtils.LINE_SEPARATOR +
                       Constant.ARFF_DATA_TAG + IOUtils.LINE_SEPARATOR +
                       Joiner.on(IOUtils.LINE_SEPARATOR).join(valueLines);
    }
}
