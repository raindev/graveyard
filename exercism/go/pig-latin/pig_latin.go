package piglatin

import (
	"bufio"
	"strings"
)

func Sentence(sentence string) string {
	scanner := bufio.NewScanner(strings.NewReader(sentence))
	scanner.Split(bufio.ScanWords)
	var b strings.Builder
	words := strings.Count(sentence, " ") + 1
	b.Grow(len(sentence) + words*2) // two letter suffix "ay" added to each word
	if !scanner.Scan() {
		return ""
	}
	translate(&b, scanner.Text())
	for scanner.Scan() {
		b.WriteByte(' ')
		translate(&b, scanner.Text())
	}
	return b.String()
}

func translate(out *strings.Builder, word string) {
	if isVowel(rune(word[0])) || word[:2] == "xr" || word[:2] == "yt" {
		out.WriteString(word)
		out.WriteString("ay")
		return
	}
	consonants := startConsonants(word)
	if word[consonants-1:consonants+1] == "qu" {
		consonants += 1
	}
	out.WriteString(word[consonants:])
	out.WriteString(word[:consonants])
	out.WriteString("ay")
}

func startConsonants(word string) int {
	for i, c := range word {
		if isVowel(c) || c == 'y' && i != 0 {
			return i
		}
	}
	return len(word)
}

func isVowel(r rune) bool {
	switch r {
	case 'a', 'e', 'i', 'o', 'u':
		return true
	default:
		return false
	}
}
