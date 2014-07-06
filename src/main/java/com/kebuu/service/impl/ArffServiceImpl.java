package com.kebuu.service.impl;

import com.google.common.collect.Lists;
import com.kebuu.domain.Cotation;
import com.kebuu.service.ArffService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.vfs2.FileObject;

import java.util.List;
import java.util.stream.Collectors;

public class ArffServiceImpl implements ArffService {

    public static final String SEPARATOR = ",";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String ATTRIBUTE = "@attribute";

    @Override
    @SneakyThrows
    public void writeToFile(List<Cotation> cotations, FileObject fileObject) {
        List<String> attributes = buildHeaderLines();
        List<String> data = buildDataLines(cotations);

        IOUtils.writeLines(attributes, IOUtils.LINE_SEPARATOR, fileObject.getContent().getOutputStream());
        IOUtils.writeLines(data, IOUtils.LINE_SEPARATOR, fileObject.getContent().getOutputStream(true));
    }

    private List<String> buildHeaderLines() {
        List<String> attributes = Lists.newArrayList();
        attributes.add(ATTRIBUTE + " date date[" + YYYY_MM_DD + "]");
        attributes.add(ATTRIBUTE + " start real");
        attributes.add(ATTRIBUTE + " end real");
        attributes.add(ATTRIBUTE + " min real");
        attributes.add(ATTRIBUTE + " max real");
        attributes.add(ATTRIBUTE + " volume integer");
        return attributes;
    }

    private List<String> buildDataLines(List<Cotation> cotations) {
        return (List<String>) cotations.stream()
               .map(cotation -> {
                   return FastDateFormat.getInstance(YYYY_MM_DD).format(cotation.getDate()) + SEPARATOR +
                                  cotation.getStart() + SEPARATOR +
                                  cotation.getEnd() + SEPARATOR +
                                  cotation.getMin() + SEPARATOR +
                                  cotation.getMax() + SEPARATOR +
                                  cotation.getVolume();
               })
               .collect(Collectors.toList());
    }
}
