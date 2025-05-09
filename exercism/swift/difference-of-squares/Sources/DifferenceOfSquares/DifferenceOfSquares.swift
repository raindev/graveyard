struct Squares {
  let sumOfSquares: Int, squareOfSum: Int, differenceOfSquares: Int

  init(_ n: Int) {
    squareOfSum = n * n * (n + 1) * (n + 1) / 4
    sumOfSquares = n * (n + 1) * (2 * n + 1) / 6
    differenceOfSquares = squareOfSum - sumOfSquares
  }

}
