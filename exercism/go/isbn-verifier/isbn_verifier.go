package isbn

import "strings"

func IsValidISBN(isbn string) bool {
	isbn = strings.ReplaceAll(isbn, "-", "")
	if len(isbn) != 10 {
		return false
	}
	var checksum int
	for i := 0; i < 9; i++ {
		c := isbn[i]
		if c < '0' || c > '9' {
			return false
		}
		checksum += int(c - '0') * (10 - i)
	}
	if isbn[9] >= '0' && isbn[9] <= '9' {
		checksum += int(isbn[9] - '0')
	} else if isbn[9] == 'X' {
		checksum += 10
	} else {
		return false
	}
	return checksum % 11 == 0
}
