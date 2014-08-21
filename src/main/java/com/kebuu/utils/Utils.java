package com.kebuu.utils;

import java.util.function.Supplier;

public final class Utils {
    private Utils() { }

    public static <T> Supplier<T> supply(T t) {
        return () -> t;
    }
}
