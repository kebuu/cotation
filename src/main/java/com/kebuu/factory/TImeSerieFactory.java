package com.kebuu.factory;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.kebuu.dto.TimeSerie;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class TimeSerieFactory {

    public <T> List<TimeSerie<T>> toTimeSeries(Iterable<T> data, int elementsInSerie) {
        List<T> dataAsList = Lists.newArrayList(data);

        int numberOfTimeSerieToCreate = Ints.max(0, Iterables.size(dataAsList) - elementsInSerie + 1);

        return IntStream.range(0, numberOfTimeSerieToCreate)
            .mapToObj(x -> new TimeSerie<T>(dataAsList.subList(x, x + elementsInSerie)))
            .collect(Collectors.toList());
    }
}
