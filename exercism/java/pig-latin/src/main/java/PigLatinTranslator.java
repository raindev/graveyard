import java.util.regex.Pattern;

final class PigLatinTranslator {
    private static final Pattern WORD_PATTERN = Pattern.compile(
            "(?<consonants>(?!xr|yt)y?((qu)|[\\w&&[^aeiouy]])*)?" +
            "(?<base>\\w+)");

    String translate(final String phrase) {
        return WORD_PATTERN.matcher(phrase).replaceAll("${base}${consonants}ay");
    }
}
