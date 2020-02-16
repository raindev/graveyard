package io.raindev.uppercase;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Uppercase {
    public static String of3Letters(String string) {
        String upperCased = Arrays.stream(string.split(" "))
                .map(w -> w.chars().filter(Character::isAlphabetic).count() == 3 ?
                        w.toUpperCase()
                        : w)
                .collect(Collectors.joining(" "));
        return upperCased.length() < string.length() ?
                upperCased + ' '
                : upperCased;
    }
}
