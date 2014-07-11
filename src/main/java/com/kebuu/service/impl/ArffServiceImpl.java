package com.kebuu.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.kebuu.domain.Cotation;
import com.kebuu.domain.TimeSerieCotation;
import com.kebuu.service.ArffService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.apache.commons.vfs2.FileObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ArffServiceImpl implements ArffService {

    public static final String SEPARATOR = ",";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String ATTRIBUTE = "@attribute";
    public static final String MISSING_VALUE = "?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @SneakyThrows
    public void writeToFile(List<Cotation> cotations, FileObject fileObject) {
        IOUtils.write(toArff(cotations), fileObject.getContent().getOutputStream(false));
    }

    @Override
    @SneakyThrows
    public void writeToFile(FileObject fileObject) {
        writeToFile(getCotations(), fileObject);
    }

    private List<Cotation> getCotations() {
        return jdbcTemplate.query("SELECT id, date, start, end, min, max, volume FROM cotation", new BeanPropertyRowMapper<Cotation>(Cotation.class));
    }

    @Override
    @SneakyThrows
    public String toArff() {
        return toArff(getCotations());
    }

    @Override
    @SneakyThrows
    public String toArff(List<Cotation> cotations) {
        List<String> attributes = buildHeaderLines();
        List<String> dataLines = buildDataLines(cotations);

        return StreamSupport.stream(Iterables.concat(attributes, dataLines).spliterator(), false)
                   .collect(Collectors.joining(IOUtils.LINE_SEPARATOR));
    }

    @Override
    public String toArff(TimeSerieCotation timeSerieCotation) {
        List<String> attributes = Lists.newArrayList();

        for (int i = timeSerieCotation.getPreviousCotations().size(); i > 0; i--) {
            attributes.addAll(buildHeaderLines());
        }
        return null;
    }

    private List<String> buildHeaderLines() {
        return buildHeaderLines(Optional.<String>empty());
    }

    private List<String> buildHeaderLines(Optional<String> attributeSuffix) {
        List<String> attributes = Lists.newArrayList();
        attributes.add(ATTRIBUTE + " date" + attributeSuffix.map(x -> "_" + x).orElse("") + " date[" + YYYY_MM_DD + "]");
        attributes.add(ATTRIBUTE + " start" + attributeSuffix.map(x -> "_" + x).orElse("") + " real");
        attributes.add(ATTRIBUTE + " end" + attributeSuffix.map(x -> "_" + x).orElse("") + " real");
        attributes.add(ATTRIBUTE + " min" + attributeSuffix.map(x -> "_" + x).orElse("") + " real");
        attributes.add(ATTRIBUTE + " max" + attributeSuffix.map(x -> "_" + x).orElse("") + " real");
        attributes.add(ATTRIBUTE + " volume" + attributeSuffix.map(x -> "_" + x).orElse("") + " integer");
        attributes.add(IOUtils.LINE_SEPARATOR);
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
                                  (cotation.getVolume().equals(NumberUtils.LONG_ZERO) ? MISSING_VALUE : String.valueOf(cotation.getVolume()));
               })
               .collect(Collectors.toList());
    }
}
