import java.util.function.BiConsumer;

final class RaindropConverter {

    String convert(final int number) {
        final var result = new StringBuilder();
        final BiConsumer<Integer, String> appendIfFactored = (n, str) -> {
            if (number % n == 0) {
                result.append(str);
            }
        };
        appendIfFactored.accept(3, "Pling");
        appendIfFactored.accept(5, "Plang");
        appendIfFactored.accept(7, "Plong");
        return result.length() != 0
                ? result.toString()
                : String.valueOf(number);
    }

}
