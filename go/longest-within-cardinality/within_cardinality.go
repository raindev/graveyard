package within_cardinality

// Find the length of the longest stubstring of s within k.
func longest(s string, k int) int {
	start := 0
	counts := make(map[byte]int) // todo use a counter array
	res := 0
	for end := range s {
		count := counts[s[end]]
		counts[s[end]] = count + 1
		for len(counts) > k {
			// todo is it possible to update a map value in one lookup?
			count = counts[s[start]] - 1
			// todo does go have increments?
			if count == 0 {
				delete(counts, s[start])
			} else {
				counts[s[start]] = count
			}
			start += 1
		}
		// todo does it need to be updated every time?
		res = max(res, end - start + 1)
	}
	return res
}
