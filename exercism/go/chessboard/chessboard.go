package chessboard

// File tracks which chess squares are occupied by a piece.
type File []bool

// Chessboard contains all files for a chess board.
type Chessboard map[string]File

// CountInFile returns how many squares are occupied in the chessboard,
// within the given file.
func CountInFile(cb Chessboard, file string) int {
	fileSquares, exists := cb[file]
	if !exists {
		return 0
	}

	count := 0
	for _, s := range fileSquares {
		if s {
			count++
		}
	}
	return count
}

// CountInRank returns how many squares are occupied in the chessboard,
// within the given rank.
func CountInRank(cb Chessboard, rank int) int {
	if rank < 1 || rank > 8 {
		return 0
	}

	count := 0
	for _, file := range cb {
		if file[rank-1] {
			count++
		}
	}
	return count
}

// CountAll should count how many squares are present in the chessboard.
func CountAll(cb Chessboard) int {
	return 64
}

// CountOccupied returns how many squares are occupied in the chessboard.
func CountOccupied(cb Chessboard) int {
	count := 0
	for file := 'A'; file <= 'H'; file++ {
		count += CountInFile(cb, string(file))
	}
	return count
}
