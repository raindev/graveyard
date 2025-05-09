final class Scrabble {
    private final int score;

    Scrabble(final String word) {
        score = word.chars()
            .map(this::letterScore)
            .sum();
    }

    private int letterScore(final int letter) {
        return switch (Character.toLowerCase(letter)) {
            case 'a', 'e', 'i', 'o', 'u', 'l', 'n', 'r', 's', 't' -> 1;
            case 'd', 'g' -> 2;
            case 'b', 'c', 'm', 'p' -> 3;
            case 'f', 'h', 'v', 'w', 'y' -> 4;
            case 'k' -> 5;
            case 'j', 'x' -> 8;
            case 'q', 'z' -> 10;
            default -> 0;
        };
    }

    int getScore() {
        return score;
    }

}
