import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.stream.Collectors;

class VariableLengthQuantity {

    List<String> encode(final List<Long> numbers) {
        return numbers.stream()
            .flatMap(n -> encodeN(n).stream())
            .collect(Collectors.toList());
    }

    private List<String> encodeN(Long number) {
        final var result = new LinkedList<String>();
        var first = true;
        do {
            final var chunk = number & 0x7F | (first ? 0x0 : 0x80);
            result.addFirst("0x" + Long.toHexString(chunk));
            number >>= 7;
            first = false;
        } while (number != 0);
        return result;
    }

    List<String> decode(final List<Long> bytes) {
        final var result = new ArrayList<String>();
        var current = 0;
        for (var b : bytes) {
            current <<= 7;
            current += b & 0x7F;
            if ((b & 0x80) == 0) {
                result.add("0x" + Integer.toHexString(current));
                current = 0;
            } else {
                // leading zeros are not allowed
                if (current == 0) {
                    fail();
                }
            }
        }
        // non-terminated byte sequences are not allowed
        if (current != 0) {
            fail();
        }
        return result;
    }

    private void fail() {
        throw new IllegalArgumentException("Invalid variable-length quantity encoding");
    }
}
