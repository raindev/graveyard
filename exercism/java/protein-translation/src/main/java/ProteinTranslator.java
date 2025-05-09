import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class ProteinTranslator {

    List<String> translate(final String rnaSequence) {
        assert rnaSequence.length() % 3 == 0:
            "Not divisible into codons";

        return chunk(rnaSequence, 3)
            .takeWhile(codon -> !isStop(codon))
            .map(this::toProtein)
            .collect(Collectors.toList());
    }

    private String toProtein(final String codon) {
        switch (codon) {
            case "AUG":
                return "Methionine";
            case "UUU": case "UUC":
                return "Phenylalanine";
            case "UUA": case "UUG":
                return "Leucine";
            case "UCU": case "UCC": case "UCA": case "UCG":
                return "Serine";
            case "UAU": case "UAC":
                return "Tyrosine";
            case "UGU": case "UGC":
                return "Cysteine";
            case "UGG":
                return "Tryptophan";
            default:
                throw new IllegalArgumentException(
                        "Protein coding codon expected: " + codon);
        }
    }

    private boolean isStop(final String codon) {
        switch (codon) {
            case "UAA": case "UAG": case "UGA": return true;
            default: return false;
        }
    }

    private Stream<String> chunk(final String s, final int size) {
        final var chunkIterator = new Iterator<String>() {
            private int position = 0;

            @Override public boolean hasNext() {
                return position < s.length();
            }

            @Override public String next() {
                final var result = s.substring(position,
                        Math.min(position + size, s.length()));
                position += size;
                return result;
            }
        };

        return StreamSupport.stream(Spliterators.spliterator(
                    chunkIterator,
                    (int) Math.ceil(s.length() / (double) size),
                    Spliterator.ORDERED),
                false);
    }
}
