package bottlesong

import "fmt"

const (
	introLine        = "%s green bottle%s hanging on the wall,"
	fallLine         = "And if one green bottle should accidentally fall,"
	finalLine string = "There'll be %s green bottle%s hanging on the wall."
)

var numbers = [...]string{
	"no",
	"one",
	"two",
	"three",
	"four",
	"five",
	"six",
	"seven",
	"eight",
	"nine",
	"ten",
}

func Recite(startBottles, takeDown int) []string {
	var res []string
	for i := startBottles; i > startBottles-takeDown; i-- {
		intro := fmt.Sprintf(introLine, capitalize(numbers[i]), plural(i))
		final := fmt.Sprintf(finalLine, numbers[i-1], plural(i-1))
		res = append(res, intro, intro, fallLine, final, "")
	}
	return res[:len(res)-1]
}

func capitalize(s string) string {
	return string(s[0]^0x20) + s[1:]
}

func plural(n int) string {
	if n != 1 {
		return "s"
	}
	return ""
}
