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
		{"", 5, 0},       // empty string
		{"a", 0, 0},      // k == 0: no characters allowed
		{"aaa", 2, 3},    // all same char, k exceeds distinct count
		{"xaay", 1, 2},   // k == 1, longest run is "aa"
		{"abcbcbcd", 2, 6}, // general case, max window in the middle
		{"abc", 5, 3},    // k > len(s): entire string
		{"abc", 3, 3},    // k == len(s): entire string
		{"abc", 1, 1},    // k == 1, all distinct: single char
		{"aaaa", 1, 4},   // single char repeated, k == 1
		{"abccc", 1, 3},  // longest run at the end
		{"aabbcc", 2, 4}, // multiple windows of equal max length
	}

	for _, tt := range tests {
		testname := fmt.Sprintf("longest(%q, %d)", tt.s, tt.k)
		t.Run(testname, func(t *testing.T) {
			res := longest(tt.s, tt.k)
			if res != tt.out {
				t.Errorf("got %d, expected %d", res, tt.out)
			}
		})
	}
}
