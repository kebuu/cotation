package com.kebuu.misc;

import com.kebuu.utils.StreamUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class IndexedListWrapper<T, K> extends ListWrapper<T> implements ListObserver<T> {

    private final Map<K, T> index = new HashMap<>();

    public IndexedListWrapper(Iterable<T> iterable) {
        wrappedList = new ObservableList<>(this);
        addAll(iterable);
    }

    public Optional<T> getByIndex(K k) {
        return Optional.ofNullable(index.get(k));
    }

    protected abstract Function<T, K> getIndexFunction();

    protected void index(Iterable<T> values) {
        index.putAll(toMap(values));
    }

    protected void index(T t) {
        index.put(getIndexFunction().apply(t), t);
    }

    @Override
    public void onAdd(T t) {
        index(t);
    }

    @Override
    public void onAdd(Iterable<T> values) {
        index(values);
    }

    @Override
    public void onRemove(T t) {
        index.remove(getIndexFunction().apply(t));
    }

    @Override
    public void onRemove(Iterable<T> values) {
        values.forEach(this::onRemove);
    }

    private Map<K, T> toMap(Iterable<T> values) {
        return StreamUtils.stream(values).collect(Collectors.toMap(getIndexFunction(), Function.<T>identity()));
    }
}
