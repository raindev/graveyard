package running_sum_of_1d_array

func runningSum(nums []int) []int {
	sums := make([]int, len(nums))
	sums[0] = nums[0]
	for i, n := range nums[1:] {
		sums[i+1] = sums[i] + n
	}
	return sums
}
