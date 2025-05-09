package kindergarten

import (
	"errors"
	"fmt"
	"slices"
	"strings"
)

type Garden map[string][]string

func NewGarden(diagram string, children []string) (*Garden, error) {
	rows := strings.Split(diagram, "\n")
	if len(rows) != 3 {
		return nil, fmt.Errorf("2 rows expected, got %d", len(rows)-1)
	}
	if len(rows[1]) != len(rows[2]) {
		return nil, fmt.Errorf("both rows must have equal length: %d != %d",
			len(rows[1]), len(rows[2]))
	}
	if len(rows[1])%2 != 0 || len(rows[2])%2 != 0 {
		return nil, errors.New("both rows must have even number of items")
	}
	if len(children) != len(rows[1])/2 {
		return nil, errors.New("mismatching number of plants and children")
	}
	row1 := rows[1]
	row2 := rows[2]
	g := make(Garden)
	sortedChildren := make([]string, len(children))
	copy(sortedChildren, children)
	slices.Sort(sortedChildren)
	for i, child := range sortedChildren {
		_, ok := g[child]
		if ok {
			return nil, fmt.Errorf("duplicate child: %s", child)
		}
		var err error
		g[child], err = decode([4]byte{
			row1[i*2],
			row1[i*2+1],
			row2[i*2],
			row2[i*2+1],
		})
		if err != nil {
			return nil, err
		}
	}
	return &g, nil
}

func decode(letters [4]byte) ([]string, error) {
	res := [4]string{}
	for i, letter := range letters {
		switch letter {
		case 'G':
			res[i] = "grass"
		case 'C':
			res[i] = "clover"
		case 'R':
			res[i] = "radishes"
		case 'V':
			res[i] = "violets"
		default:
			return nil, errors.New("unknown plant letter: " + string(letter))
		}
	}
	return res[:], nil
}

func (g *Garden) Plants(child string) (plants []string, ok bool) {
	plants, ok = (*g)[child]
	return
}
