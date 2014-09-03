package com.kebuu.exception;

public class ListWrapperCopyException extends RuntimeException {

    public ListWrapperCopyException() {
        super("List wrapper subclasses need to declare a public empty constructor in order to be copiable");
    }
}
