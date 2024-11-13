package gifts

import (
	"bufio"
	"os"
	"strconv"
	"strings"
)

type dimensions struct {
	l, w, h uint
}

func Materials(boxes []dimensions) (paper, ribbon uint) {
	for _, box := range boxes {
		paper += boxPaper(box)
		ribbon += boxRibbon(box)
	}
	return paper, ribbon
}

func boxPaper(box dimensions) uint {
	lw := box.l * box.w
	wh := box.w * box.h
	lh := box.l * box.h
	slackPaper := min(lw, wh, lh)
	area := 2 * (lw + wh + lh)
	return slackPaper + area
}

func boxRibbon(box dimensions) uint {
	shortestPerimeter := 2 * min(box.l+box.w, box.w+box.h, box.l+box.h)
	bowLength := box.l * box.w * box.h
	return shortestPerimeter + bowLength
}

func readInput() (res []dimensions) {
	file, err := os.Open("input")
	if err != nil {
		panic(err)
	}
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		res = append(res, parseDimensions(scanner.Text()))
	}
	return res
}

func parseDimensions(s string) dimensions {
	ds := strings.Split(s, "x")
	return dimensions{l: parseUint(ds[0]), w: parseUint(ds[1]), h: parseUint(ds[2])}
}

func parseUint(s string) uint {
	res, err := strconv.ParseUint(s, 10, 32)
	if err != nil {
		panic(err)
	}
	return uint(res)
}
