package io.raindev.pairedduplicates;

import java.util.HashMap;
import java.util.Map;

public class PairedDuplicates {
    public static final int PAIRS_THRESHOLD = 1_000_000_000;

    /**
     * Returns number of unique pairs of duplicates numbers.
     * E.g. if there're two duplicate numbers in {@code numbers}, the result will be 1; if there're three the result
     * will be 1. If number of duplicates is larger than {@link PairedDuplicates#PAIRS_THRESHOLD}, return early.
     *
     * @param numbers an array of arbitrary integers
     * @return number of unique pairs of all numbers that are repeated
     */
    public static int pairsCount(final int[] numbers) {
        final Map<Integer, Integer> numberCounts = new HashMap<>();
        int indexPairs = 0;
        for (int number : numbers) {
            final Integer count = numberCounts.get(number);
            if (count != null) {
                // each occurrence of a  number will form pair of indices with each previous occurrence
                indexPairs += count;
                if (indexPairs > PAIRS_THRESHOLD) {
                    return PAIRS_THRESHOLD;
                }
            }
            numberCounts.put(number, count == null ? 1 : count + 1);
        }
        return indexPairs;
    }
}
