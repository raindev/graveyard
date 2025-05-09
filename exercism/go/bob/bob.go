package bob

import (
	"regexp"
	"strings"
)

var yell regexp.Regexp = *regexp.MustCompile(`^[^a-z]*[A-Z]+[^a-z]*$`)

func Hey(remark string) string {
	remark = strings.TrimSpace(remark)
	isQuestion := strings.HasSuffix(remark, "?")
	isYelling := yell.MatchString(remark)
	switch {
	case isQuestion && isYelling:
		return "Calm down, I know what I'm doing!"
	case isYelling:
		return "Whoa, chill out!"
	case isQuestion:
		return "Sure."
	case remark == "":
		return "Fine. Be that way!"
	default:
		return "Whatever."
	}
}
