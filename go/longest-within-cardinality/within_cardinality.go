package within_cardinality

// Find the length of the longest stubstring of s within k.
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
