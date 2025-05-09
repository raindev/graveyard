package wordcount

import (
	"regexp"
	"strings"
)

type Frequency map[string]int

func WordCount(phrase string) Frequency {
	res := Frequency{}
	split := regexp.MustCompile(`\w+('\w+)?`)
	for _, word := range split.FindAllString(strings.ToLower(phrase), -1) {
		res[word]++
	}
	return res
}
