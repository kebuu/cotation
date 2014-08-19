package com.kebuu.exception;

import java.util.function.Supplier;

public class NoBuiltCotationAtPosition extends RuntimeException {

    public NoBuiltCotationAtPosition(int position) {
        super("No built cotation found at position " + position);
    }

    public static Supplier<NoBuiltCotationAtPosition> from(int position) {
        return () -> new NoBuiltCotationAtPosition(position);
    }
}
