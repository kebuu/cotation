package com.kebuu.exception;

public class NoBuiltCotationAtPosition extends RuntimeException {

    public NoBuiltCotationAtPosition(int position) {
        super("No built cotation found at position " + position);
    }
}
