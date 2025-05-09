package romannumerals

import (
	"fmt"
	"strings"
)

func ToRomanNumeral(input int) (string, error) {
	if input <= 0 || input > 3999 {
		return "", fmt.Errorf("number of of range %d", input)
	}
	return toRoman(input), nil
}

func toRoman(n int) string {
	var base, five string
	var digit, factor int
	switch {
	case n < 10:
		base, five = "I", "V"
		digit = n
		factor = 1
	case n < 100:
		base, five = "X", "L"
		digit = n / 10
		factor = 10
	case n < 1000:
		base, five = "C", "D"
		digit = n / 100
		factor = 100
	default:
		base = "M"
		digit = n / 1000
		factor = 1000
	}
	var digitStr string
	switch {
	case digit <= 3:
		digitStr = strings.Repeat(base, digit)
	case digit == 4:
		digitStr = base + five
	case digit == 5:
		digitStr = five
	case digit <= 8:
		digitStr = five + toRoman((digit-5)*factor)
	case digit == 9:
		digitStr = base + toRoman(10*factor)
	}
	if n <= 10 {
		return digitStr
	} else {
		return digitStr + toRoman(n%factor)
	}
}
