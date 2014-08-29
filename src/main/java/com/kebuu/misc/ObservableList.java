package com.kebuu.misc;

import org.apache.commons.lang3.event.EventListenerSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class ObservableList<T> extends ArrayList<T> {

    private final EventListenerSupport<ListObserver> innerListeners = EventListenerSupport.create(ListObserver.class);

    @SafeVarargs
    public ObservableList(ListObserver<T>... listeners) {
        addListener(listeners);
    }

    @SafeVarargs
    public final ObservableList<T> addListener(ListObserver<T>... listeners) {
        Arrays.asList(listeners).stream().forEach(innerListeners::addListener);
        return this;
    }

    @Override
    public boolean add(T t) {
        innerListeners.fire().onAdd(t);
        return super.add(t);
    }

    @Override
    public void add(int index, T t) {
        innerListeners.fire().onAdd(t);
        super.add(index, t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        innerListeners.fire().onAdd(c);
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        innerListeners.fire().onAdd(c);
        return super.addAll(index, c);
    }

    @Override
    public T set(int index, T element) {
        innerListeners.fire().onRemove(get(index));
        innerListeners.fire().onAdd(element);
        return super.set(index, element);
    }

    @Override
    public T remove(int index) {
        innerListeners.fire().onRemove(get(index));
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        innerListeners.fire().onRemove(o);
        return super.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        innerListeners.fire().onRemove(c);
        return super.removeAll(c);
    }
}
