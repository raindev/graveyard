package protein

import "errors"

var (
	ErrStop        = errors.New("stop")
	ErrInvalidBase = errors.New("invalid")
)

func FromRNA(rna string) ([]string, error) {
	var res  = make([]string, 0, len(rna) / 3)
	for len(rna) >= 3 {
		protein, err := FromCodon(rna[:3])
		if err == ErrStop {
			break
		} else if err != nil {
			return []string{}, err
		}
		res = append(res, protein)
		rna = rna[3:]
	}
	return res, nil
}

func FromCodon(codon string) (string, error) {
	switch codon {
	case "AUG":
		return "Methionine", nil
	case "UUU", "UUC":
		return "Phenylalanine", nil
	case "UUA", "UUG":
		return "Leucine", nil
	case "UCU", "UCC", "UCA", "UCG":
		return "Serine", nil
	case "UAU", "UAC":
		return "Tyrosine", nil
	case "UGU", "UGC":
		return "Cysteine", nil
	case "UGG":
		return "Tryptophan", nil
	case "UAA", "UAG", "UGA":
		return "", ErrStop
	default:
		return "", ErrInvalidBase
	}
}
