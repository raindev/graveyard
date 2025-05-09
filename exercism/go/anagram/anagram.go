package anagram

import (
	"strings"
	"unicode"
)

func Detect(subject string, candidates []string) (res []string) {
	lowerSubject := strings.ToLower(subject)
	subjectFrequencies := frequencies(lowerSubject)
	nextCandidate:
	for _, c := range candidates {
		if len(subject) != len(c) {
			continue
		}
		lowerCandidate := strings.ToLower(c)
		if lowerSubject == lowerCandidate {
			continue
		}
		for i, v := range frequencies(lowerCandidate) {
			if v != subjectFrequencies[i] {
				continue nextCandidate
			}
		}
		res = append(res, c)
	}
	return res
}

func frequencies(s string) map[rune]int {
	res := map[rune]int{}
	for _, r := range s {
		if unicode.IsLetter(r) {
			res[r] += 1
		}
	}
	return res
}
