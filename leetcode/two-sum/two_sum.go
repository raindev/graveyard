package twosum

func twoSum(nums []int, target int) []int {
	for l := range nums {
		t := target - nums[l]
		for r := l + 1; r < len(nums); r++ {
			if nums[r] == t {
				return []int{l, r}
			}
		}
	}
	return nil
}
