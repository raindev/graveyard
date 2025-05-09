package lsproduct

import (
	"errors"
)

func LargestSeriesProduct(digits string, span int) (int64, error) {
	if span < 0 || span > len(digits) {
		return 0, errors.New("span must not between zero and len(digits)")
	}
	var max int64
	for i := 0; i <= len(digits)-span; i++ {
		var product int64 = 1
		for j := 0; j < span; j++ {
			chr := digits[i+j]
			if chr < '0' || chr > '9' {
				return 0, errors.New("is not a digit")
			}
			product *= int64(chr - '0')
		}
		if product > max {
			max = product
		}
	}
	return max, nil
}
