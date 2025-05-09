package sublist

func Sublist(l1, l2 []int) Relation {
	switch {
	case len(l1) == len(l2) && contains(l1, l2):
		return RelationEqual
	case len(l1) > len(l2) && contains(l1, l2):
		return RelationSuperlist
	case contains(l2, l1):
		return RelationSublist
	default:
		return RelationUnequal
	}
}

func contains(list, sublist []int) bool {
	for start := 0; start <= len(list)-len(sublist); start++ {
		if startsWith(list[start:], sublist) {
			return true
		}
	}
	return false
}

func startsWith(list, prefix []int) bool {
	for i := 0; i < len(prefix); i++ {
		if list[i] != prefix[i] {
			return false
		}
	}
	return true
}
