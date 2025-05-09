package twelve

import (
	"fmt"
	"strings"
)

var gifts = []string{
	"twelve Drummers Drumming,",
	"eleven Pipers Piping,",
	"ten Lords-a-Leaping,",
	"nine Ladies Dancing,",
	"eight Maids-a-Milking,",
	"seven Swans-a-Swimming,",
	"six Geese-a-Laying,",
	"five Gold Rings,",
	"four Calling Birds,",
	"three French Hens,",
	"two Turtle Doves, and",
	"a Partridge in a Pear Tree.",
}

var days = []string{
	"first",
	"second",
	"third",
	"fourth",
	"fifth",
	"sixth",
	"seventh",
	"eighth",
	"ninth",
	"tenth",
	"eleventh",
	"twelfth",
}

const verse = "On the %s day of Christmas my true love gave to me: %s"

func Verse(i int) string {
	return fmt.Sprintf(verse, days[i-1], strings.Join(gifts[12-i:], " "))
}

func Song() string {
	res := make([]string, 0, len(days))
	for i := range days {
		res = append(res, Verse(i+1))
	}
	return strings.Join(res, "\n")
}
