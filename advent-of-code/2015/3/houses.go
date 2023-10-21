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
	var x, y int
	visited := map[point]bool{{x, y}: true}
	for _, d := range way {
		switch direction(d) {
		case west:
			x--
		case east:
			x++
		case north:
			y++
		case south:
			y--
		}
		visited[point{x, y}] = true
	}
	return len(visited)
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
