package smallerafterself

func smallerNumbersThanCurrent(nums []int) []int {
	res := make([]int, len(nums))
	for i, x := range nums {
		for j, y := range nums {
			if i == j {
				continue
			}
			if y < x {
				res[i]++
			}
		}
	}
	return res
}
