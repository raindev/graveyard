package armstrong

func IsNumber(n int) bool {
	var digitCount int
	for tmp := n; tmp > 0; tmp /= 10 {
		digitCount++
	}
	var sum int
	for tmp := n; tmp > 0; tmp /= 10 {
		digit := tmp%10
		exp := 1
		for i := 0; i < digitCount; i++ {
			exp *= digit
		}
		sum += exp
	}
	return sum == n
}
