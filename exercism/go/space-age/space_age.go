package space

import (
	"fmt"
)

type Planet string

// assume a leap year is once in four years for simiplicity
const earthYearSeconds = 60 * 60 * 24 * (365 + 1./4)

func Age(seconds float64, planet Planet) float64 {
	orbitRatio, err := orbitRatio(planet)
	if err != nil {
		return -1
	}
	var earthAge = seconds / earthYearSeconds
	return earthAge / orbitRatio
}

func orbitRatio(planet Planet) (float64, error) {
	switch planet {
	case "Earth":
		return 1, nil
	case "Mercury":
		return 0.2408467, nil
	case "Venus":
		return 0.61519726, nil
	case "Mars":
		return 1.8808158, nil
	case "Jupiter":
		return 11.862615, nil
	case "Saturn":
		return 29.447498, nil
	case "Uranus":
		return 84.016846, nil
	case "Neptune":
		return 164.79132, nil
	default:
		return 0, fmt.Errorf("unexpected planet: %s", planet)
	}
}
