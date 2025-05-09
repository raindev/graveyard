import java.util.Arrays;
import java.util.regex.Pattern;

class Matrix {
    private static final Pattern ROW_DELIMITER = Pattern.compile("\n");
    private static final Pattern COLUMN_DELIMITER = Pattern.compile(" ");
    private final int[][] matrix;

    Matrix(final String matrixAsString) {
        matrix = ROW_DELIMITER.splitAsStream(matrixAsString)
                .map(rowStr -> COLUMN_DELIMITER
                        .splitAsStream(rowStr)
                        .mapToInt(Integer::parseInt)
                        .toArray())
                .toArray(int[][]::new);
    }

    int[] getRow(final int rowNumber) {
        if  (rowNumber < 0 || rowNumber > matrix.length) {
            throw new IllegalArgumentException("Row index out of range: " + rowNumber);
        }
        return Arrays.copyOf(matrix[rowNumber - 1], matrix[0].length);
    }

    int[] getColumn(final int columnNumber) {
        if (columnNumber < 0 || matrix.length == 0 || columnNumber > matrix[0].length) {
            throw new IllegalArgumentException("Column index out of range: " + columnNumber);
        }
        final var result = new int [matrix.length];
        for (var r = 0; r < matrix.length; ++r) {
            result[r] = matrix[r][columnNumber - 1];
        }
        return result;
    }
}
