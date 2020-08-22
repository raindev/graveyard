package io.raindev.continuousints;

import java.util.BitSet;

public class ContinuousInts {
    public static int crack(int[] numbers) {
        // largest consecutive positive number in the array is bound by its length
        BitSet intsSet = new BitSet(numbers.length);
        for (int n : numbers) {
            if (n > 0 && n <= numbers.length) {
                intsSet.set(n - 1);
            }
        }
        return intsSet.nextClearBit(0) + 1;
    }
}
