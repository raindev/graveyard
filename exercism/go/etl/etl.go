package etl

import "strings"

func Transform(in map[int][]string) map[string]int {
	res := make(map[string]int)
	for score, letters := range in {
		for _, letter := range letters {
			res[strings.ToLower(letter)] = score
		}
	}
	return res
}
