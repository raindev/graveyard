package prime

import "fmt"

func Nth(n int) (int, error) {
	if n <= 0 {
		return 0, fmt.Errorf("prime number must be positive: %d", n)
	}
	candidate := 3
	for count := 1; count < n; candidate++ {
		if isPrime(candidate) {
			count++
		}
	}
	return candidate - 1, nil
}

func isPrime(n int) bool {
	for i := 2; i*i <= n; i++ {
		if n%i == 0 {
			return false
		}
	}
	return true
}
