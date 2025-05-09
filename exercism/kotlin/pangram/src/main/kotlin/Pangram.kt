import java.util.BitSet

object Pangram {

    private const val alphabetSize = 26

    fun isPangram(input: String): Boolean {
        val letters = BitSet(alphabetSize)
        input
            .map(Char::toLowerCase)
            .filter(Char::isLetter)
            .forEach { letters.set(it.toInt()) }
        return letters.cardinality() == alphabetSize
    }
}
