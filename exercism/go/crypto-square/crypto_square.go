package cryptosquare

import (
	"math"
	"strings"
	"unicode"
)

func Encode(pt string) string {
	if len(pt) == 0 {
		return ""
	}
	pt = strings.Map(func(r rune) rune {
		if unicode.IsLetter(r) {
			return unicode.ToLower(r)
		}
		if unicode.IsDigit(r) {
			return r
		}
		return -1
	}, pt)
	sqrt := math.Sqrt(float64(len(pt)))
	columns := int(math.Ceil(sqrt))
	rows := int(math.Ceil(float64(len(pt)) / float64(columns)))

	res := make([]byte, rows*columns+columns-1)
	for i := range res {
		res[i] = ' '
	}
done:
	for row := 0; row < rows; row++ {
		for col := 0; col < columns; col++ {
			i := row*columns + col
			if i == len(pt) {
				break done
			}
			res[col*(rows+1)+row] = pt[i]
		}
	}
	return string(res)
}
