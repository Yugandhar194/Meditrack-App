package com.airtribe.meditrack.util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Generic in-memory data store providing basic CRUD operations for any
 * entity type T, keyed by a String id extracted via idExtractor.
 * Demonstrates generics + functional interfaces + streams.
 */
public class DataStore<T> implements Iterable<T> {
    private final Map<String, T> store = new LinkedHashMap<>();
    private final Function<T, String> idExtractor;

    public DataStore(Function<T, String> idExtractor) {
        this.idExtractor = idExtractor;
    }

    public void add(T item) {
        store.put(idExtractor.apply(item), item);
    }

    public Optional<T> getById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    public boolean remove(String id) {
        return store.remove(id) != null;
    }

    public List<T> getAll() {
        return new ArrayList<>(store.values());
    }

    public boolean exists(String id) {
        return store.containsKey(id);
    }

    public int count() {
        return store.size();
    }

    public List<T> filter(Predicate<T> predicate) {
        return store.values().stream().filter(predicate).collect(Collectors.toList());
    }

    @Override
    public java.util.Iterator<T> iterator() { return store.values().iterator(); }
}
