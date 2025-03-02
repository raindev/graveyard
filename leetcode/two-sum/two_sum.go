package twosum

// The solution works correctly when the complements are equal. Even though the
// index of one of the complements is overwritten in nums_indexes, it will be
// available when iterated over in the second pass.
func twoSum(nums []int, target int) []int {
	nums_indexes := make(map[int]int)
	for i, n := range nums {
		nums_indexes[n] = i
	}
	for l := range nums {
		if r, ok := nums_indexes[target-nums[l]]; ok && r != l {
			return []int{l, r}
		}
	}
	return nil
}
