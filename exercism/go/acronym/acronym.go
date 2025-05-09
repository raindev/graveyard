// Package acronym provides utility functions for abbreviation
package acronym

import (
	"strings"
	"unicode"
)

// Abbreviate return an acronym of a phrase
func Abbreviate(s string) string {
	var res strings.Builder
	var prev rune
	for _, c := range s {
		if prev != '\'' && !unicode.IsLetter(prev) && unicode.IsLetter(c) {
			res.WriteRune(unicode.ToUpper(c))
		}
		prev = c
	}
	return res.String()
}
