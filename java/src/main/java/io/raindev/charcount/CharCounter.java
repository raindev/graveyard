package io.raindev.transfers;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.stream.Collectors;

public class CharCounter {
    public static Map<Character, Integer> countCharacters(@Nonnull String string) {
        return string.chars()
                .parallel()
                .mapToObj(i -> (char) i)
                .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));
    }

}
