package smallerafterself

import (
	"reflect"
	"testing"
)

func TestCountSmaller(t *testing.T) {
	tests := []struct {
		name     string
		numbers  []int
		expected []int
	}{
		{
			name:     "decreasing",
			numbers:  []int{11, 7, 3, 1},
			expected: []int{3, 2, 1, 0},
		},
		{
			name:     "increasing",
			numbers:  []int{2, 4, 5, 12},
			expected: []int{0, 0, 0, 0},
		},
		{
			name:     "non-monotonic",
			numbers:  []int{8, 9, 1, 3},
			expected: []int{2, 2, 0, 0},
		},
		{
			name:     "repeating",
			numbers:  []int{1, 1, 1},
			expected: []int{0, 0, 0},
		},
		{
			name:     "empty",
			numbers:  []int{},
			expected: []int{},
		},
		{
			name:     "single",
			numbers:  []int{13},
			expected: []int{0},
		},
	}

	for _, tt := range tests {
		t.Run(tt.name, func(t *testing.T) {
			result := countSmaller(tt.numbers)
			if !reflect.DeepEqual(result, tt.expected) {
				t.Errorf("countSmaller() = %v, want %v", result, tt.expected)
			}
		})
	}
}
