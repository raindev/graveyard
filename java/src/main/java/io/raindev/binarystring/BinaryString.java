package io.raindev.binarystring;

public class BinaryString {
    /**
     * Calculates number of steps necessary to reduce a number whose binary representation {@code binaryStr} holds.
     * Each step is either subtracting zero from odd numbers or dividing by two for even.
     *
     * @param binaryStr binary representation of the number in big endian, allowing for leading zeroes
     * @return number of steps applied to the number until it becomes zero
     */
    public static int stepsToZero(String binaryStr) {
        int i = 0;
        while (i < binaryStr.length() && binaryStr.charAt(i) == '0') {
            i++;
        }
        if (i == binaryStr.length()) { // string consists only of zeroes
            return 0;
        }

        int steps = 0;
        for (; i < binaryStr.length(); i++) {
            // each 1 in the binary representation would result first in subtraction then in division
            if (binaryStr.charAt(i) == '1') {
                steps += 2;
            } else {
                steps += 1;
            }
        }
        // the last zero doesn't require shifting (dividing by 2), hence - 1
        return steps - 1;
    }
}
