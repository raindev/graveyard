package io.raindev.binarysearch;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import static io.raindev.binarysearch.BinarySearch.binarySearch;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BinarySearchTest {
    Integer[] array =  new Integer[]{null, 3, 4, 6, 7, 8, 11};

    @Test public void findExisting() {
        assertEquals(Optional.of(2),
                binarySearch(array, 4));
    }

    @Test public void searchForMissing() {
        assertEquals(Optional.empty(),
                binarySearch(array, 5));
    }

    @Test public void searchForNull() {
        assertEquals(Optional.of(0),
                binarySearch(array, null));
    }

    @Test public void searchInEmpty() {
        assertEquals(Optional.empty(),
                binarySearch(new Integer[]{}, 9));
    }

    @Test public void findInSingletonArray() {
        assertEquals(Optional.of(0),
                binarySearch(new Integer[]{9}, 9));
    }

    @Test public void searchForMissingInSingletonArray() {
        assertEquals(Optional.empty(),
                binarySearch(new Integer[]{9}, 7));
    }

}
