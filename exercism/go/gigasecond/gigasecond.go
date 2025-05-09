// Package gigasecond allows to jump by a billion seconds into the future.
package gigasecond

import "time"

// AddGigasecond returns the time after 10^9 seconds from t.
func AddGigasecond(t time.Time) time.Time {
	return t.Add(1e9 * time.Second)
}
