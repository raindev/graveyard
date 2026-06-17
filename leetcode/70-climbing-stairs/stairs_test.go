package climbing_stairs

import (
	"fmt"
	"testing"
)

func TestClimbStair(t *testing.T) {
	var tests = []struct {
		in  int
		out int
	}{
		{1, 1},
		{2, 2},
		{3, 3}, // 2 + 1(1); 1 + 2(2)
		{4, 5}, // 2 + 2(2); 1 + 3(3)
		{5, 8}, // 2 + 3(3); 1 + 4(5)
	}

	for _, tt := range tests {
		testname := fmt.Sprintf("climbStair(%d)", tt.in)
		t.Run(testname, func(t *testing.T) {
			res := climbStairs(tt.in)
			if res != tt.out {
				t.Errorf("got %d, expected %d", res, tt.out)
			}
		})
	}
}
