// Package weather provides forecasting functions.
package weather

// CurrentCondition specifies current weather conditions.
var CurrentCondition string

// CurrentLocation specifies current location.
var CurrentLocation string

// Forecast produces a forecast based on the current conditions.
func Forecast(city, condition string) string {
	CurrentLocation, CurrentCondition = city, condition
	return CurrentLocation + " - current weather condition: " + CurrentCondition
}
