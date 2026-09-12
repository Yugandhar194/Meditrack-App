package com.airtribe.meditrack.interfaces;

import java.util.List;

/**
 * Generic contract for services that support keyword search.
 */
public interface Searchable<T> {
    List<T> search(String keyword);

    default boolean hasMatches(String keyword) {
        return !search(keyword).isEmpty();
    }
}
