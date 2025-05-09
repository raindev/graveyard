pub fn find_saddle_points(matrix: &[Vec<u64>]) -> Vec<(usize, usize)> {
    if matrix.is_empty() || matrix[0].is_empty() {
        return vec![];
    }

    let row_maxs: Vec<u64> = matrix
        .iter()
        .map(|row| *row.iter().max().expect("row to be non-empty"))
        .collect();
    let is_row_max = |row_index, element| element == row_maxs[row_index];

    let column_count = matrix[0].len();
    let mut col_mins: Vec<Option<u64>> = vec![None; column_count];
    let mut is_col_min = |col_index: usize, element| {
        element
            == *col_mins[col_index]
                .get_or_insert_with(|| matrix.iter().map(|row| row[col_index]).min().unwrap())
    };

    matrix
        .iter()
        .enumerate()
        .flat_map(|(row_index, row)| {
            row.iter()
                .enumerate()
                .map(move |(col_index, element)| ((row_index, col_index), *element))
        })
        .filter(|((row_index, col_index), element)| {
            is_row_max(*row_index, *element) && is_col_min(*col_index, *element)
        })
        .map(|((row_index, col_index), _element)| (row_index, col_index))
        .collect()
}
