pub fn annotate(minefield: &[&str]) -> Vec<String> {
    let mut result = minefield.iter().map(ToString::to_string).collect();

    for (r, row) in minefield.iter().enumerate() {
        for (c, chr) in row.bytes().enumerate() {
            if chr == b'*' {
                update_mine_counts(r, c, &mut result);
            }
        }
    }
    result
}

fn update_mine_counts(row: usize, column: usize, field: &mut Vec<String>) {
    let field_rows = field.len() as isize;
    let field_columns = field[0].len() as isize;
    (-1isize..=1)
        .flat_map(|r| (-1isize..=1).map(move |c| (r, c)))
        .map(|(row_offset, col_offset)| (row as isize + row_offset, column as isize + col_offset))
        .filter(|(row, col)| *row >= 0 && *col >= 0)
        .filter(|(row, col)| *row < field_rows && *col < field_columns)
        .for_each(|(row, col)| increase_count(row as usize, col as usize, field));
}

fn increase_count(row: usize, column: usize, field: &mut Vec<String>) {
    // Safe to manipulate the String as bytes since it holds only ASCII.
    let chr = unsafe { &mut field[row].as_bytes_mut()[column] };
    if *chr == b' ' {
        *chr = b'1';
    } else if *chr != b'*' {
        *chr = b'0' + (*chr - b'0') + 1;
    }
}
