package summultiples

func SumMultiples(level int, items ...int) (energy int) {
	for i := 1; i < level; i++ {
		for _, item := range items {
			if item != 0 && i%item == 0 {
				energy += i
				break
			}
		}
	}
	return
}
