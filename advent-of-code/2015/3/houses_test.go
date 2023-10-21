package houses

import "testing"

func TestPresentDelivery(t *testing.T) {
	houseCount := AtLeastOnePresent(readInput())
	if houseCount != 2572 {
		t.Errorf("The number of houses with at least one present is expected to be 2572, got %d", houseCount)
	}
}
