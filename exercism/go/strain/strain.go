package strain

type Ints = Slice[int]
type Lists = Slice[[]int]
type Strings = Slice[string]
type Slice[V any] []V

func (l Slice[V]) Keep(filter func(V) bool) (res Slice[V]) {
	for _, v := range l {
		if filter(v) {
			res = append(res, v)
		}
	}
	return res
}

func (ints Slice[int]) Discard(filter func(int) bool) (res Slice[int]) {
	return ints.Keep(func(i int) bool { return !filter(i) })
}
