package thefarm

import (
	"errors"
	"fmt"
)

func DivideFood(calc FodderCalculator, cows int) (float64, error) {
	baseAmount, err := calc.FodderAmount(cows)
	if err != nil {
		return 0, err
	}
	factor, err := calc.FatteningFactor()
	if err != nil {
		return 0, err
	}
	return (baseAmount * factor) / float64(cows), nil
}

func ValidateInputAndDivideFood(calc FodderCalculator, cows int) (float64, error) {
	if cows <= 0 {
		return 0, errors.New("invalid number of cows")
	}
	return DivideFood(calc, cows)
}

type InvalidCowsError struct {
	cows    int
	message string
}

func (e *InvalidCowsError) Error() string {
	return fmt.Sprintf("%d cows are invalid: %s", e.cows, e.message)
}

func ValidateNumberOfCows(cows int) error {
	if cows > 0 {
		return nil
	}

	var message string
	if cows < 0 {
		message = "there are no negative cows"
	} else {
		message = "no cows don't need food"
	}
	return &InvalidCowsError{cows, message}
}
