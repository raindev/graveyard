package paper

import "testing"

func TestPaperNeeds(t *testing.T) {
	totalPaper := PaperNeeded(readInput())
	if totalPaper != 1598415 {
		t.Errorf("Total paper needed is expected to be 1598415, got %d", totalPaper)
	}
}
