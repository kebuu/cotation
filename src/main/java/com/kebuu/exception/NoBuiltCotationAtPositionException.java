package com.kebuu.exception;

public class NoBuiltCotationAtPositionException extends RuntimeException {

    public NoBuiltCotationAtPositionException(int position) {
        super("No built cotation found at position " + position);
    }
}
