package parsinglogfiles

import (
	"fmt"
	"regexp"
)

var validLineRe = regexp.MustCompile(`^\[(TRC|DBG|INF|WRN|ERR|FTL)\]`)

func IsValidLine(text string) bool {
	return validLineRe.MatchString(text)
}

var separatorRe = regexp.MustCompile(`<[~\*=-]*>`)

func SplitLogLine(text string) []string {
	return separatorRe.Split(text, -1)
}

var passwordRe = regexp.MustCompile(`"(?i).*password.*"`)

func CountQuotedPasswords(lines []string) int {
	count := 0
	for _, line := range lines {
		count += len(passwordRe.FindAllString(line, -1))
	}
	return count
}

var endOfLineRe = regexp.MustCompile(`end-of-line\d+`)

func RemoveEndOfLineText(text string) string {
	return endOfLineRe.ReplaceAllString(text, "")
}

var userRe = regexp.MustCompile(`User\s+([[:alnum:]]+)`)

func TagWithUserName(lines []string) []string {
	result := make([]string, 0, len(lines))
	for _, line := range lines {
		if match := userRe.FindStringSubmatch(line); match != nil {
			newLine := fmt.Sprintf("[USR] %s %s", match[1], line)
			result = append(result, newLine)
		} else {
			result = append(result, line)
		}
	}
	return result
}
