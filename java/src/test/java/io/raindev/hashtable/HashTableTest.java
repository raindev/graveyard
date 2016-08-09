package io.raindev.hashtable;

import java.util.stream.IntStream;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class HashTableTest {
    HashTable<String, Integer> hashTable;

    @Before public void setUp() {
        hashTable = new HashTable<>();
    }

    @Test public void putAndGet() {
        hashTable.put("first", 1);
        assertEquals(Optional.of(1), hashTable.get("first"));
        assertEquals(1, hashTable.size());
    }

    @Test public void firstPut() {
        assertEquals(Optional.empty(), hashTable.put("first", 1));
        assertEquals(1, hashTable.size());
    }

    @Test public void getMissing() {
        assertEquals(Optional.empty(), hashTable.get("missing"));
    }

    @Test public void putReplaceOldValue() {
        hashTable.put("key", 1);
        assertEquals(Optional.of(1), hashTable.put("key", 2));
        assertEquals(1, hashTable.size());
    }

    @Test public void putTwice() {
        assert hashTable.hash("first") != hashTable.hash("another");
        hashTable.put("first", 1);
        hashTable.put("another", 2);
        assertEquals(Optional.of(2), hashTable.get("another"));
        assertEquals(Optional.of(1), hashTable.get("first"));
        assertEquals(2, hashTable.size());
    }

    @Test public void putSameEffectiveHash() {
        String key1 = "a";
        String key2 = String.valueOf((char)('a' + hashTable.capacity));
        assert hashTable.hash(key1) == hashTable.hash(key2);
        hashTable.put(key1, 1);
        hashTable.put(key2, 2);
        assertEquals(Optional.of(2), hashTable.get(key2));
        assertEquals(Optional.of(1), hashTable.get(key1));
        assertEquals(2, hashTable.size());
    }

    @Test public void putNegativeKeyHash() {
        assert "special key".hashCode() < 0;
        hashTable.put("special key", 2);
        assertEquals(Optional.of(2), hashTable.get("special key"));
        assertEquals(1, hashTable.size());
    }

    @Test public void putMultipleValues() {
        int hashTableSize = 100;
        IntStream.range(0, hashTableSize)
            .forEach(n -> hashTable.put(String.valueOf(n), n));
        assertEquals(hashTableSize, hashTable.size());
        IntStream.range(0, hashTableSize)
            .forEach(n -> assertEquals(
                        Optional.of(n),
                        hashTable.get(String.valueOf(n))));
    }

    @Test public void removeMultipleValues() {
        int hashTableSize = 100;
        IntStream.range(0, hashTableSize)
            .forEach(n -> hashTable.put(String.valueOf(n), n));
        IntStream.range(0, hashTableSize)
            .forEach(n -> assertEquals(
                        Optional.of(n),
                        hashTable.remove(String.valueOf(n))));
        assertEquals(0, hashTable.size());
    }

    @Test(expected = NullPointerException.class)
    public void putProhibitNullKeys() {
        hashTable.put(null, 1);
    }

    @Test(expected = NullPointerException.class)
    public void putProhibitNullValues() {
        hashTable.put("nothing", null);
    }

    @Test(expected = NullPointerException.class)
    public void getProhibitNullKeys() {
        hashTable.get(null);
    }

    @Test(expected = NullPointerException.class)
    public void removeProhibitNullKeys() {
        hashTable.remove(null);
    }

    @Test public void remove() {
        hashTable.put("first", 1);
        assertEquals(Optional.of(1), hashTable.remove("first"));
        assertEquals(Optional.empty(), hashTable.get("first"));
        assertEquals(0, hashTable.size());
    }

    @Test public void removeMissing() {
        assertEquals(Optional.empty(), hashTable.remove("missing"));
    }

}
