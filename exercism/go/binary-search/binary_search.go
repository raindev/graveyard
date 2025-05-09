package binarysearch

func SearchInts(list []int, key int) int {
	if len(list) == 0 {
		return -1
	}
	l, r := 0, len(list)-1
	for l != r {
		m := (l + r) / 2
		if key > list[m] {
			l = m + 1
		} else {
			r = m
		}
	}
	if list[l] == key {
		return l
	}
	return -1
}
