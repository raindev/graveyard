package within_cardinality

// Find the length of the longest substring of s within k.
//
// Assumes ASCII input: counts are tracked per byte via [256]int, so results
// are undefined for multi-byte UTF-8 runes.
func longest(s string, k int) int {
	start := 0
	var counts[256]int
	cardinality := 0
	res := 0
	for end := range s {
		if counts[s[end]] == 0 {
			cardinality++
		}
		counts[s[end]]++
		for ; cardinality > k; start++ {
			counts[s[start]]--
			if counts[s[start]] == 0 {
				cardinality--
			}
		}
		res = max(res, end - start + 1)
	}
	return res
}
