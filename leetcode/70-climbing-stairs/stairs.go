package climbing_stairs

func climbStairs(n int) int {
	prev := 1
	next := 1
	for range n-1 {
		tmp := prev
		prev = next
		next = next + tmp
	}
	return next
}
