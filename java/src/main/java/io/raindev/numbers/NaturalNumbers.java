package io.raindev.numbers;

import java.util.BitSet;

class NaturalNumbers {
    /**
     * Returns smallest positive integer missing from {@code numbers}.
     *
     * @param numbers array of arbitrary integers
     * @return smallest n: n ∈ ℕ₁ and n ∉ @{code numbers}
     */
    public int smallestMissing(int[] numbers) {
        BitSet occurrences = new BitSet();
        for (int n : numbers) {
            if (n > 0) {
                occurrences.set(n - 1);
            }
        }
        for (int i = 0; i < occurrences.size(); i++) {
            if (!occurrences.get(i)) {
                return i + 1;
            }
        }
        return 1;
    }
}
