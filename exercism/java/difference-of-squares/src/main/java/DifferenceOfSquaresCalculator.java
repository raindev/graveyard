import static java.lang.Math.pow;

final class DifferenceOfSquaresCalculator {

    int computeSquareOfSumTo(final int n) {
        return (int) (pow(n, 2) * pow(n + 1, 2)) / 4;
    }

    int computeSumOfSquaresTo(final int n) {
        return n * (n + 1) * (2 * n + 1) / 6;
    }

    int computeDifferenceOfSquares(final int n) {
        return computeSquareOfSumTo(n) - computeSumOfSquaresTo(n);
    }

}
