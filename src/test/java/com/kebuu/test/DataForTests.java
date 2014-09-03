package com.kebuu.test;

import com.google.common.collect.Lists;
import com.kebuu.domain.Cotation;

import java.util.Date;
import java.util.List;

public class DataForTests {

    public static List<Cotation> cotations() {
        return Lists.newArrayList(
            createCotation(1L, new Date(), "code1", 1, 2.0, 1.0, 1.0, 1.0, 0L),
            createCotation(2L, new Date(), "code1", 2, 1.0, 2.0, 1.0, 1.0, 10L),
            createCotation(3L, new Date(), "code1", 3, 1.0, 1.0, 2.0, 1.0, 20L),
            createCotation(4L, new Date(), "code1", 4, 1.0, 1.0, 1.0, 2.0, 30L),
            createCotation(5L, new Date(), "code1", 5, 3.0, 1.0, 3.0, 1.0, 40L),
            createCotation(6L, new Date(), "code1", 6, 1.0, 3.0, 1.0, 3.0, 50L),
            createCotation(7L, new Date(), "code1", 7, 1.0, 1.0, 3.0, 1.0, 60L),
            createCotation(8L, new Date(), "code1", 8, 1.0, 1.0, 1.0, 3.0, 70L),
            createCotation(9L, new Date(), "code1", 9, 1.0, 1.0, 1.0, 1.0, 80L)
        );
    }

    private static Cotation createCotation(Long id, Date date, String code, int position, double start, double min, double max, double end, long volume) {
        Cotation cotation = new Cotation();
        cotation.setId(id);
        cotation.setDate(date);
        cotation.setCode(code);
        cotation.setPosition(position);
        cotation.setStart(start);
        cotation.setMin(min);
        cotation.setMax(max);
        cotation.setEnd(end);
        cotation.setVolume(volume);
        return cotation;
    }
}