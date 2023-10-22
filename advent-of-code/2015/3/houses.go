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
	var santa, roboSanta point
	visited := map[point]bool{{0, 0}: true}
	santasTurn := true
	for _, d := range way {
		if santasTurn {
			visited[santa.move(d)] = true
		} else {
			visited[roboSanta.move(d)] = true
		}
		santasTurn = !santasTurn
	}
	return len(visited)
}

func (s *point) move(d direction) point {
	switch d {
	case west:
		s.x--
	case east:
		s.x++
	case south:
		s.y--
	case north:
		s.y++
	}
	return *s
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
