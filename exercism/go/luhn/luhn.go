package luhn

import "strings"

func Valid(id string) bool {
	id = strings.ReplaceAll(id, " ", "")
	if len(id) <= 1 {
		return false
	}
	sum := 0
	for i := 1; i <= len(id); i++ {
		chr := id[len(id)-i]
		if chr < '0' || chr > '9' {
			return false
		}
		n := int(chr - '0')
		sum += ((2-i%2)*n-1)%9 + 1
	}
	return sum%10 == 0
}
