package resistorcolorduo

import "fmt"

func Value(colors []string) int {
	return colorDigit(colors[0])*10 + colorDigit(colors[1])
}

func colorDigit(color string) int {
	switch color {
	case "black":
		return 0
	case "brown":
		return 1
	case "red":
		return 2
	case "orange":
		return 3
	case "yellow":
		return 4
	case "green":
		return 5
	case "blue":
		return 6
	case "violet":
		return 7
	case "grey":
		return 8
	case "white":
		return 9
	}
	panic(fmt.Sprintf("unrecognized color: %s", color))
}
