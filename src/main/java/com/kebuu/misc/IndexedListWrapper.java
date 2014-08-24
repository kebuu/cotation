package com.kebuu.misc;

import com.kebuu.utils.StreamUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class IndexedListWrapper<T, K> extends ListWrapper<T> {

    private final Function<T, K> indexFunction;
    private final Map<K, T> index = new HashMap<>();

    public Optional<T> getByIndex(K k) {
        return Optional.ofNullable(index.get(k));
    }

    protected IndexedListWrapper(Iterable<T> iterable, Function<T, K> indexFunction) {
        super(iterable);
        this.indexFunction = indexFunction;
    }

    protected void index(Iterable<T> values) {
        index.putAll(StreamUtils.stream(values).collect(Collectors.toMap(indexFunction, Function.<T>identity())));
    }
}
