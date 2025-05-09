package listops

// IntList is an abstraction of a list of integers which we can define methods on
type IntList []int

func (s IntList) Foldl(fn func(int, int) int, acc int) int {
	for _, v := range s {
		acc = fn(acc, v)
	}
	return acc
}

func (s IntList) Foldr(fn func(int, int) int, acc int) int {
	for i := range s {
		acc = fn(s[len(s)-1-i], acc)
	}
	return acc
}

func (s IntList) Filter(fn func(int) bool) IntList {
	res := []int{}
	for _, n := range s {
		if fn(n) {
			res = append(res, n)
		}
	}
	return res
}

func (s IntList) Length() int {
	return len(s)
}

func (s IntList) Map(fn func(int) int) IntList {
	res := make([]int, len(s))
	for i, n := range s {
		res[i] = fn(n)
	}
	return res
}

func (s IntList) Reverse() IntList {
	res := make([]int, len(s))
	for i := range s {
		res[i] = s[len(s)-1-i]
	}
	return res
}

func (s IntList) Append(lst IntList) IntList {
	res := make([]int, len(s)+len(lst))
	copy(res, s)
	copy(res[len(s):], lst)
	return res
}

func (s IntList) Concat(lists []IntList) IntList {
	res := append([]int{}, s...)
	for _, l := range lists {
		res = append(res, l...)
	}
	return res
}
