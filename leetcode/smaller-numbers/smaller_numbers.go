package smallerafterself

func smallerNumbersThanCurrent(nums []int) []int {
	counts := make([]int, 101)
	// count each number first
	for _, n := range nums {
		counts[n]++
	}
	// tally up counts in ascending order
	total_count := 0
	for i := range counts {
		counts[i], total_count = total_count, total_count+counts[i]
	}
	// order resulting counts in accordance with the input
	res := make([]int, len(nums))
	for i, n := range nums {
		res[i] = counts[n]
	}
	return res
}
