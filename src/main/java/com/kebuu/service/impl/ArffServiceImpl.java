package com.kebuu.service.impl;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.kebuu.dao.EnhancedCotationRepository;
import com.kebuu.domain.ArffAttribute;
import com.kebuu.domain.Cotation;
import com.kebuu.domain.TimeSeriesCotation;
import com.kebuu.service.ArffService;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ArffServiceImpl implements ArffService {

    public static final String SEPARATOR = ",";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String MISSING_VALUE = "?";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EnhancedCotationRepository enhancedCotationRepository;

    @Override
    @SneakyThrows
    public void write(List<Cotation> cotations, OutputStream outputStream) {
        IOUtils.write(toArff(cotations), outputStream);
    }

    @Override
    @SneakyThrows
    public void write(OutputStream outputStream) {
        write(getCotations(), outputStream);
    }

    private List<Cotation> getCotations() {
        return jdbcTemplate.query("SELECT id, date, start, end, min, max, volume FROM cotation", new BeanPropertyRowMapper<Cotation>(Cotation.class));
    }

    private TimeSeriesCotation getTimeSeriesCotations(int timeSeriesLength) {
        return null;
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
        List<String> headerDataSepararor = Lists.newArrayList(IOUtils.LINE_SEPARATOR);
        List<String> dataLines = buildDataLines(cotations);

        return StreamSupport.stream(Iterables.concat(attributes, headerDataSepararor, dataLines)
                .spliterator(), false)
                .collect(Collectors.joining(IOUtils.LINE_SEPARATOR));
    }

    @Override
    public String toArff(TimeSeriesCotation timeSeriesCotation) {
        List<String> attributes = Lists.newArrayList();

        for (int i = timeSeriesCotation.getCotations().size(); i > 0; i--) {
            attributes.addAll(buildHeaderLines(Optional.of(String.valueOf(i))));
        }

        List<String> headerDataSepararor = Lists.newArrayList(IOUtils.LINE_SEPARATOR);
        List<String> dataLines = buildDataLines(timeSeriesCotation.getCotations());


        return StreamSupport.stream(Iterables.concat(attributes, headerDataSepararor, dataLines)
                    .spliterator(), false)
                    .collect(Collectors.joining(IOUtils.LINE_SEPARATOR));
    }

    @Override
    public String getEnhancedCotationsAsString() {
        return null;//enhancedCotationRepository.findAll();
    }

    private List<String> buildHeaderLines() {
        return buildHeaderLines(Optional.<String>empty());
    }

    private List<String> buildHeaderLines(Optional<String> attributeSuffix) {
        List<ArffAttribute<Cotation>> arffAttributes = Lists.newArrayList(
            new ArffAttribute<Cotation>("date", "date[" + YYYY_MM_DD + "]", Cotation::getDate),
            new ArffAttribute<Cotation>("start", "real", Cotation::getStart),
            new ArffAttribute<Cotation>("end", "real", Cotation::getEnd),
            new ArffAttribute<Cotation>("min", "real", Cotation::getMin),
            new ArffAttribute<Cotation>("max", "real", Cotation::getMax),
            new ArffAttribute<Cotation>("volume", "integer", Cotation::getVolume)
        );

        return arffAttributes
                .stream()
                .map(ArffAttribute::toHeaderText)
                .collect(Collectors.toList());
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
