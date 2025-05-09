package scrabble

import "unicode"

func Score(word string) (res int) {
	for _, c := range word {
		switch unicode.ToLower(c) {
		case 'a', 'e', 'i', 'o', 'u', 'l', 'n', 'r', 's', 't':
			res += 1
		case 'd', 'g':
			res += 2
		case 'b', 'c', 'm', 'p':
			res += 3
		case 'f', 'h', 'v', 'w', 'y':
			res += 4
		case 'k':
			res += 5
		case 'j', 'x':
			res += 8
		case 'q', 'z':
			res += 10
		default:
			panic("unrecognized letter")
		}
	}
	return res
}
