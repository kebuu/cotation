package com.kebuu.misc;

import com.google.common.collect.Lists;
import com.kebuu.exception.ListWrapperCopyException;

import java.util.Iterator;
import java.util.List;

public abstract class ListWrapper<T> implements Iterable<T> {

    protected List<T> wrappedList = Lists.newArrayList();

    protected ListWrapper() { }

    protected ListWrapper(Iterable<T> iterable) {
        addAll(iterable);
    }

    @SafeVarargs
    protected ListWrapper(T...values) {
        this(Lists.newArrayList(values));
    }

    @Override
    public Iterator<T> iterator() {
        return wrappedList.iterator();
    }

    public <K extends ListWrapper<T>> K concat(K listWrapper) {
        return addAll(listWrapper.wrappedList);
    }

    public <K extends ListWrapper<T>> K addAll(Iterable<T> iterable) {
        wrappedList.addAll(Lists.newArrayList(iterable));
        return (K) this;
    }

    public <K extends ListWrapper<T>> K add(T t) {
        return addAll(Lists.newArrayList(t));
    }

    public int size() {
        return wrappedList.size();
    }

    public boolean isEmpty() {
        return wrappedList.isEmpty();
    }

    public <K extends ListWrapper<T>> K copy() {
        try {
            K newInstance = (K) this.getClass().newInstance();
            return newInstance.concat((K) this);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ListWrapperCopyException();
        }
    }
}
