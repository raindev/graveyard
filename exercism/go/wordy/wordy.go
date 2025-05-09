package wordy

import (
	"regexp"
	"strconv"
)

type Operation int

const (
	Unrecognized Operation = iota
	Add
	Substract
	Multiply
	Divide
)

func operation(s string) Operation {
	switch s {
	case "plus", "increased by":
		return Add
	case "minus", "decreased by":
		return Substract
	case "multiplied by":
		return Multiply
	case "divided by":
		return Divide
	default:
		return Unrecognized
	}
}

var questionRe = regexp.MustCompile(`^What is (?P<operand>-?\d+)(?P<operations>.*)\?$`)
var operationRe = regexp.MustCompile(` (?P<operation>(?:\w| )+?) (?P<operand>-?\d+)`)

func Answer(question string) (int, bool) {
	questionMatch := questionRe.FindStringSubmatch(question)
	if questionMatch == nil {
		// invalid question structure
		return 0, false
	}
	operand := questionMatch[questionRe.SubexpIndex("operand")]
	n, err := strconv.Atoi(operand)
	if err != nil {
		// first operand not a number
		return 0, false
	}
	operations := questionMatch[questionRe.SubexpIndex("operations")]
	var matchedOpsLen int
	for _, operationMatch := range operationRe.FindAllStringSubmatch(operations, -1) {
		matchedOpsLen += len(operationMatch[0])
		operand := operationMatch[operationRe.SubexpIndex("operand")]
		operandN, err := strconv.Atoi(operand)
		if err != nil {
			// operand not a number
			return 0, false
		}
		op := operationMatch[operationRe.SubexpIndex("operation")]
		result, ok := apply(n, operation(op), operandN)
		if !ok {
			// invalid operation
			return 0, false
		}
		n = result
	}
	if matchedOpsLen != len(operations) {
		// not all operations matched
		return 0, false
	}
	return n, true
}

func apply(n int, operation Operation, operand int) (int, bool) {
	switch operation {
	case Add:
		return n + operand, true
	case Substract:
		return n - operand, true
	case Multiply:
		return n * operand, true
	case Divide:
		return n / operand, true
	default:
		return 0, false
	}
}
