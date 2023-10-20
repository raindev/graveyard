package paper

import (
	"bufio"
	"os"
	"strconv"
	"strings"
)

type dimensions struct {
	l, w, h uint
}

func PaperNeeded(boxes []dimensions) (res uint) {
	for _, box := range boxes {
		lw := box.l * box.w
		wh := box.w * box.h
		lh := box.l * box.h
		minSide := min(lw, wh, lh)
		res += minSide + 2*lw + 2*wh + 2*lh
	}
	return res
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
