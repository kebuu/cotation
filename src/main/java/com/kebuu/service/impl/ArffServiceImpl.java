package com.kebuu.service.impl;

import com.google.common.base.Joiner;
import com.kebuu.constant.Constant;
import com.kebuu.dto.cotation.BuiltCotations;
import com.kebuu.dto.cotation.attribute.CotationAttributes;
import com.kebuu.service.ArffService;
import com.kebuu.utils.StreamUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArffServiceImpl implements ArffService {

    @Override
    public String ToArff(CotationAttributes attributes, BuiltCotations builtCotations) {
        List<String> headerLines = StreamUtils.stream(attributes)
                .filter(cotationAttribute -> !cotationAttribute.isTechnical())
                .map(cotationAttribute -> Constant.ARFF_ATTRIBUTE_TAG + " " + cotationAttribute.getName() + " " + cotationAttribute.getArffType())
                .collect(Collectors.toList());

        List<String> valueLines = StreamUtils.stream(builtCotations)
                .map(builtCotation -> {
                    return builtCotation.getValues().stream()
                       .filter(cotationValue -> !cotationValue.getAttribute().isTechnical())
                       .map(cotationValue -> cotationValue.getAttribute().getFormatter().format(cotationValue))
                       .collect(Collectors.joining(Constant.ARFF_VALUE_SEPARATOR));
                })
                .collect(Collectors.toList());

        return buildArff(headerLines, valueLines);
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
