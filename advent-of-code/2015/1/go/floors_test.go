package floors

import (
	"os"
	"testing"
)

func TestFloorClimbing(t *testing.T) {
	input, err := os.ReadFile("../input")
	if err != nil {
		panic(err)
	}
	endFloor, basementStep := climbFloors(input)
	if endFloor != 232 {
		t.Errorf("Santa is expected to end up on floor 232")
	}
	if basementStep != 1783 {
		t.Errorf("Santa is expected to go into basement after 1783 steps")
	}
}
