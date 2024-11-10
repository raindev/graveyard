package floors

import "fmt"

func climbFloors(directions []byte) (floor, basementStep int) {
	for i, pointer := range directions {
		switch pointer {
		case '(':
			floor++
		case ')':
			floor--
		default:
			panic(fmt.Sprintf("unexpected character %d at %d", pointer, i))
		}
		if floor == -1 && basementStep == 0 {
			basementStep = i + 1
		}
	}
	return floor, basementStep
}
