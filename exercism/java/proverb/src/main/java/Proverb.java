class Proverb {
    private final String[] words;

    Proverb(String[] words) {
        this.words = words;
    }

    String recite() {
        if (words.length == 0) {
            return "";
        }
        var result = new StringBuilder();
        for (int i = 0; i < words.length - 1; ++i) {
            result.append(subProverb(words[i], words[i + 1]));
        }
        return result + ending(words[0]);
    }

    private String subProverb(final String goal, final String loss) {
        return "For want of a " + goal + " the " + loss + " was lost.\n";
    }

    private String ending(final String goal) {
        return "And all for the want of a " + goal + ".";
    }

}
