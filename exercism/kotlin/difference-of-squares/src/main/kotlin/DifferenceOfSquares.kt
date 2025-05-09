class Squares(private val n: Int) {

    fun sumOfSquares(): Int = n * (n + 1) * (2 * n + 1) / 6

    fun squareOfSum(): Int = (n * (n + 1) / 2).let { it * it }

    fun difference(): Int = squareOfSum() - sumOfSquares()
}
