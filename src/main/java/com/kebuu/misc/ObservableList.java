package com.kebuu.misc;

import org.apache.commons.lang3.event.EventListenerSupport;

import java.util.ArrayList;
import java.util.Collection;

public class ObservableList<T> extends ArrayList<T> {

    private EventListenerSupport<ListObserver> listeners = EventListenerSupport.create(ListObserver.class);

    public void addListener(ListObserver<T> listener) {
        listeners.addListener(listener);
    }

    @Override
    public boolean add(T t) {
        listeners.fire().onAdd(t);
        return super.add(t);
    }

    @Override
    public void add(int index, T t) {
        listeners.fire().onAdd(t);
        super.add(index, t);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        listeners.fire().onAdd(c);
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        listeners.fire().onAdd(c);
        return super.addAll(index, c);
    }

    @Override
    public T set(int index, T element) {
        listeners.fire().onRemove(get(index));
        listeners.fire().onAdd(element);
        return super.set(index, element);
    }

    @Override
    public T remove(int index) {
        listeners.fire().onRemove(get(index));
        return super.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        listeners.fire().onRemove(o);
        return super.remove(o);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        listeners.fire().onRemove(c);
        return super.removeAll(c);
    }
}
