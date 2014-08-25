package com.kebuu.misc;

public interface ListObserver<T>  {

    void onAdd(T t);

    void onAdd(Iterable<T> t);

    void onRemove(T t);

    void onRemove(Iterable<T> t);
}
