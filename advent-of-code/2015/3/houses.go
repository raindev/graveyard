package houses

import (
	"bufio"
	"os"
)

type direction int

const (
	west direction = iota
	east
	north
	south
)

type point struct {
	x, y int
}

func AtLeastOnePresent(way []direction) int {
	var santaX, santaY int
	var roboSantaX, roboSantaY int
	visited := map[point]bool{{santaX, santaY}: true}
	santasTurn := true
	for _, d := range way {
		if santasTurn {
			santaX, santaY = move(santaX, santaY, d)
			visited[point{santaX, santaY}] = true
		} else {
			roboSantaX, roboSantaY = move(roboSantaX, roboSantaY, d)
			visited[point{roboSantaX, roboSantaY}] = true
		}
		santasTurn = !santasTurn
	}
	return len(visited)
}

func move(x, y int, d direction) (int, int) {
	switch d {
	case west:
		return x - 1, y
	case east:
		return x + 1, y
	case north:
		return x, y + 1
	case south:
		return x, y - 1
	default:
		panic("unknown direction")
	}
}

func readInput() (res []direction) {
	file, err := os.Open("input")
	defer file.Close()
	if err != nil {
		panic(err)
	}
	reader := bufio.NewReader(file)
	for {
		b, err := reader.ReadByte()
		if err != nil {
			break
		}
		switch b {
		case '<':
			res = append(res, west)
		case '>':
			res = append(res, east)
		case '^':
			res = append(res, north)
		case 'v':
			res = append(res, south)
		}
	}
	return res
}
