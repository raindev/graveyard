package smallerafterself

func countSmaller(nums []int) []int {
	res := make([]int, len(nums))
	for i, n := range nums {
		for _, n_after := range nums[i:] {
			if n_after < n {
				res[i]++
			}
		}
	}
	return res
}
