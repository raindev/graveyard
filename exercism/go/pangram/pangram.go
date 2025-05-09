package pangram

func IsPangram(input string) bool {
	var lettersBitset int
	for i := range input {
		chr := input[i]
		if chr >= 'a' && chr <= 'z' {
			lettersBitset |= 1 << (chr - 'a')
		} else if chr >= 'A' && chr <= 'Z' {
			lettersBitset |= 1 << (chr - 'A')
		}
	}
	return lettersBitset == 0x3ffffff
}
