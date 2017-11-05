package io.raindev.binarysearch;

import java.util.Objects;
import java.util.Optional;

public class BinarySearch {

    static <T extends Comparable<T>> Optional<Integer> binarySearch(
            T[] sortedArray, T element) {
        return binarySearch(sortedArray, element, 0, sortedArray.length - 1);
    }

    private static <T extends Comparable<T>> Optional<Integer> binarySearch(
                    T[] arr, T elem, int start, int end) {
        if (start > end) {
            return Optional.empty();
        }
        int middle = (start + end) / 2;
        T middleElem = arr[middle];
        if (Objects.equals(middleElem, elem)) {
            return Optional.of(middle);
        // greater than
        } else if (compare(elem, middleElem) > 0) {
            return binarySearch(arr, elem, middle + 1, end);
        } else {
            return binarySearch(arr, elem, start, middle - 1);
        }
    }

    // null first
    static <T extends Comparable<T>> int compare(T x, T y) {
        if (x == null) {
            if (y == null) {
                return 0;
            } else {
                return -1;
            }
        }
        return x.compareTo(y);
    }

}
