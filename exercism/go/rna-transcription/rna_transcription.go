package strand

func ToRNA(dna string) string {
	rna := make([]byte, len(dna))
	for i := range dna {
		switch dna[i] {
		case 'G':
			rna[i] = 'C'
		case 'C':
			rna[i] = 'G'
		case 'T':
			rna[i] = 'A'
		case 'A':
			rna[i] = 'U'
		}
	}
	return string(rna)
}
