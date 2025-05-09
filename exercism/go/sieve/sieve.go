package sieve

func Sieve(limit int) []int {
	sieve := make([]bool, limit+1) // true - divisible, false - prime
	res := make([]int, 0, limit/2)
	for n := 2; n <= limit; n++ {
		if sieve[n] {
			continue
		}
		res = append(res, n)
		for i := n * 2; i <= limit; i += n {
			sieve[i] = true
		}
	}
	return res
}
