package etl

func Transform(in map[int][]string) (res map[string]int) {
	for score, letters := range in {
		for letter := range letters {
			res[letter] = score
		}
	}
	return res
}
