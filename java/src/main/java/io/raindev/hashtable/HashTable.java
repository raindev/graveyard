package io.raindev.hashtable;

import java.util.Optional;
import java.util.Objects;
import java.util.LinkedList;
import java.util.Iterator;

@interface NonNull {}
@interface VisibleForTesting {}

class HashTable<K, V> {
    private static class Entry<K, V> {
        K key;
        V value;

        Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private static final double LOAD_FACTOR = 0.75;
    private static final int EXPANSION_FACTOR = 2;
    @VisibleForTesting int capacity = 16;
    @SuppressWarnings("unchecked")
    private LinkedList<Entry<K, V>>[] table = new LinkedList[capacity];
    private int size = 0;

    public Optional<V> put(@NonNull K key, @NonNull V newValue) {
        Objects.requireNonNull(newValue);
        int hash = hash(key);
        if (table[hash] == null) {
            table[hash] = new LinkedList<>();
        }
        Optional<Entry<K, V>> oldEntry = findEntry(hash, key);
        if (oldEntry.isPresent()) {
            V oldValue = oldEntry.get().value;
            oldEntry.get().value = newValue;
            return Optional.of(oldValue);
        }
        table[hash].add(new Entry<>(key, newValue));
        size++;
        if (size > capacity * LOAD_FACTOR) {
            expand();
        }
        return Optional.empty();
    }

    @SuppressWarnings("unchecked")
    private void expand() {
        capacity *= EXPANSION_FACTOR;
        LinkedList<Entry<K, V>>[] oldTable = table;
        table = new LinkedList[capacity];
        for (LinkedList<Entry<K, V>> bucket : oldTable) {
            if (bucket == null) continue;
            for (Entry<K, V> entry : bucket) {
                int hash = hash(entry.key);
                if (table[hash] == null) {
                    table[hash] = new LinkedList<>();
                }
                table[hash].add(entry);
            }
        }
    }

    public Optional<V> get(@NonNull K key) {
        return findEntry(hash(key), key)
            .map(entry -> entry.value);
    }

    private Optional<Entry<K, V>> findEntry(int hash, K key) {
        if (table[hash] != null) {
            for (Entry<K, V> entry : table[hash]) {
                if (entry.key.equals(key)) {
                    return Optional.of(entry);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<V> remove(@NonNull K key) {
        int hash = hash(key);
        if (table[hash] != null) {
            Iterator<Entry<K, V>> entries = table[hash].iterator();
            while (entries.hasNext()) {
                Entry<K, V> entry = entries.next();
                if (entry.key.equals(key)) {
                    entries.remove();
                    size--;
                    return Optional.of(entry.value);
                }
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    public int size() {
        return size;
    }

}
