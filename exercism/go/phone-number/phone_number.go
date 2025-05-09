package phonenumber

import (
	"errors"
	"fmt"
	"regexp"
)

func Number(phoneNumber string) (string, error) {
	number, err := parse(phoneNumber)
	if err != nil {
		return "", err
	}
	return fmt.Sprintf("%s%s%s",
			number.areaCode,
			number.exchangeCode,
			number.subscriberNumber),
		nil
}

func AreaCode(phoneNumber string) (string, error) {
	number, err := parse(phoneNumber)
	if err != nil {
		return "", err
	}
	return number.areaCode, nil
}

func Format(phoneNumber string) (string, error) {
	number, err := parse(phoneNumber)
	if err != nil {
		return "", err
	}
	return fmt.Sprintf("(%s) %s-%s",
		number.areaCode,
		number.exchangeCode,
		number.subscriberNumber), nil
}

var numberRe regexp.Regexp = *regexp.MustCompile(`^(\+?1 ?)?\(?([2-9]\d\d)\)?[- \.]*([2-9]\d\d)[- \.]*(\d\d\d\d) *$`)

type number struct {
	areaCode         string
	exchangeCode     string
	subscriberNumber string
}

func parse(phoneNumber string) (number, error) {
	matches := numberRe.FindStringSubmatch(phoneNumber)
	if matches == nil {
		return number{}, errors.New("invalid number")
	}
	return number{matches[2], matches[3], matches[4]}, nil
}
