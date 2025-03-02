package twosum

func twoSum(nums []int, target int) []int {
	indexes := make(map[int]int)
	for i, n := range nums {
		if complement_idx, ok := indexes[target-n]; ok {
			return []int{complement_idx, i}
		}
		indexes[n] = i
	}
	return nil
}
