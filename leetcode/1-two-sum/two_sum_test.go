package twosum

import (
	"slices"
	"testing"
)

func TestExamples(t *testing.T) {
	var tests = []struct {
		name    string
		numbers []int
		target  int
		want    []int
	}{
		{"example1", []int{2, 7, 11, 15}, 9, []int{0, 1}},
		{"example2", []int{3, 2, 4}, 6, []int{1, 2}},
		{"example3", []int{3, 3}, 6, []int{0, 1}},
	}
	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			res := twoSum(tt.numbers, tt.target)
			if !slices.Equal(res, tt.want) {
				t.Errorf("twoSum(%v, %d) = %v, want %v", tt.numbers, tt.target, res, tt.want)
			}
		})
	}
}
