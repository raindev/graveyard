package reverse

func Reverse(input string) string {
	res := []rune(input)
	end := len(res) - 1
	for i := 0; i < len(res)/2; i++ {
		res[i], res[end-i] = res[end-i], res[i]
	}
	return string(res)
}
