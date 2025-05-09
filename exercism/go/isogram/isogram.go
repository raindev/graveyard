package isogram

func IsIsogram(word string) bool {
	letterMask := 0
	for i := range word {
		letter := word[i]
		if letter == ' ' || letter == '-' {
			continue
		}
		if letter >= 'A' && letter <= 'Z' {
			letter += 'a' - 'A' // lowercase
		}
		letter -= 'a' // normalize
		if (letterMask & (1 << letter)) != 0 {
			return false
		}
		letterMask |= (1 << letter)
	}
	return true
}
