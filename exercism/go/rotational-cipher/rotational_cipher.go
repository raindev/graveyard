package rotationalcipher

func RotationalCipher(plain string, shiftKey int) string {
	res := make([]byte, len(plain))
	for i := range plain {
		chr := plain[i]
		var a byte
		if chr >= 'a' && chr <= 'z' {
			a = 'a'
		} else if chr >= 'A' && chr <= 'Z' {
			a = 'A'
		} else {
			res[i] = chr
			continue
		}
		res[i] = (chr-a+byte(shiftKey))%26 + a
	}
	return string(res)
}
