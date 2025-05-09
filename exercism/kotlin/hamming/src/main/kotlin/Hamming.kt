object Hamming {

    fun compute(leftStrand: String, rightStrand: String): Int {
        require(leftStrand.length == rightStrand.length) { "left and right strands must be of equal length" }
        // zip Sequences instead of CharSequences to avoid an intermediate copying of the data
        return (leftStrand.asSequence() zip rightStrand.asSequence())
                .count { (left, right) -> left != right }
    }
}
