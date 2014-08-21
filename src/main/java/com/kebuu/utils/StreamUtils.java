package com.kebuu.utils;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public final class StreamUtils {
    private StreamUtils() { }

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
