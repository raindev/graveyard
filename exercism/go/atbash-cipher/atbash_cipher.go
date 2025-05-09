package atbash

import "strings"

func Atbash(s string) string {
	res := make([]byte, 0, len(s)+len(s)/5)
	for i := range s {
		chr := s[i]
		switch {
		case chr >= 'a' && chr <= 'z':
			res = append(res, 'z'-(chr-'a'))
		case chr >= 'A' && chr <= 'Z':
			res = append(res, 'z'-(chr-'A'))
		case chr >= '0' && chr <= '9':
			res = append(res, chr)
		default:
			continue
		}
		if len(res)%6 == 5 {
			res = append(res, ' ')
		}
	}
	return strings.TrimSpace(string(res))
}
