package prime

func Factors(n int64) (res []int64) {
	for p := int64(2); n > 1; n /= p {
		for ; n%p != 0; p++ {
		}
		res = append(res, p)
	}
	return
}
