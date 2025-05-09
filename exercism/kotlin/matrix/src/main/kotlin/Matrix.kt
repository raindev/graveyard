class Matrix(matrixAsString: String) {
    private val matrix: List<List<Int>> = matrixAsString
        .lineSequence()
        .map {
            it.trim()
                .split(Regex("\\s+"))
                .map { it.toInt() }
        }
        .toList()

    fun column(colNr: Int): List<Int> {
        return matrix.map { it[colNr - 1] }
    }

    fun row(rowNr: Int): List<Int> {
        return matrix[rowNr - 1]
    }
}
