package series

func All(n int, s string) []string {
	if n > len(s) {
		return nil
	}
	res := make([]string, len(s)-n+1)
	for i := 0; i <= len(s)-n; i++ {
		res[i] = s[i : i+n]
	}
	return res
}

func UnsafeFirst(n int, s string) string {
	return s[:n]
}

func First(n int, s string) (first string, ok bool) {
	if n > len(s) {
		return "", false
	}
	return s[:n], true
}
