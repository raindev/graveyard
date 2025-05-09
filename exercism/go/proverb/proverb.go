package proverb

import "fmt"

func Proverb(rhyme []string) []string {
	res := make([]string, len(rhyme))
	if len(rhyme) == 0 {
		return res
	}
	for i := range rhyme[1:] {
		res[i] = fmt.Sprintf("For want of a %s the %s was lost.", rhyme[i], rhyme[i+1])
	}
	res[len(res)-1] = fmt.Sprintf("And all for the want of a %s.", rhyme[0])
	return res
}
