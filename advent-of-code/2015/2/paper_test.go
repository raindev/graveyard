package gifts

import "testing"

func TestWrappingMaterials(t *testing.T) {
	paper, ribbon := Materials(readInput())
	if paper != 1598415 {
		t.Errorf("Paper needed is expected to be 1598415 sqft, got %d", paper)
	}
	if ribbon != 3812909 {
		t.Errorf("Ribbon needed is expected to be 3812909 ft, got %d", ribbon)
	}
}
