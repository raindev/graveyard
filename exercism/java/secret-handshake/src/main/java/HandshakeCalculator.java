import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

final class HandshakeCalculator {

    final List<Signal> calculateHandshake(final int number) {
        final var result = Arrays.stream(Signal.values())
            .filter(signal -> Signal.containedIn(number, signal))
            .collect(Collectors.toList());
        if (Signal.shouldReverse(number)) {
            Collections.reverse(result);
        }
        return result;
    }

}
