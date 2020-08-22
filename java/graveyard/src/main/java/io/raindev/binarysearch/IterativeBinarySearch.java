package io.raindev.binarysearch;

import java.util.Optional;
import java.util.Objects;
import static io.raindev.binarysearch.BinarySearch.compare;

class IterativeBinarySearch {
    static <T extends Comparable<T>> Optional<Integer> binarySearch(
        T[] sortedArray, T element) {
        int start = 0;
        int end = sortedArray.length;
        while (start < end) {
            int middle = (start + end) / 2;
            T middleElement = sortedArray[middle];
            if (Objects.equals(element, middleElement)) {
                return Optional.of(middle);
            } else if (compare(element, middleElement) > 0) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return Optional.empty();
    }
}
