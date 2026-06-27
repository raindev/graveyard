package within_cardinality

import (
	"fmt"
	"testing"
)

func TestLongest(t *testing.T) {
	var tests = []struct {
		s  string
		k int
		out int
	}{
		{"", 5, 0},
		{"a", 0, 0},
		{"aaa", 2, 3},
		{"xaay", 1, 2},
		{"abcbcbcd", 2, 6},
	}

	for _, tt := range tests {
		testname := fmt.Sprintf("longest(%s, %d)", tt.s, tt.k)
		t.Run(testname, func(t *testing.T) {
			res := longest(tt.s, tt.k)
			if res != tt.out {
				t.Errorf("got %d, expected %d", res, tt.out)
			}
		})
	}
}
