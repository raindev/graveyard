package main

import (
	"fmt"
	"os"
)

func finalFloor(directions []byte) (floor int) {
	for i, pointer := range directions {
		switch pointer {
		case '(':
			floor++
		case ')':
			floor--
		default:
			panic(fmt.Sprintf("unexpected character %d at %d", pointer, i))
		}
	}
	return floor
}

func basementStep(directions []byte) int {
	var floor int
	for i, pointer := range directions {
		switch pointer {
		case '(':
			floor++
		case ')':
			floor--
		default:
			panic(fmt.Sprintf("unexpected character %d at %d", pointer, i))
		}
		if floor == -1 {
			return i + 1
		}
	}
	return floor
}

func main() {
	input, err := os.ReadFile("input")
	if err != nil {
		panic(err)
	}
	println("Santa will end up on floor", finalFloor(input))
	println("Santa will go into basement after", basementStep(input), "steps")
}
