package meetup

import "time"

type WeekSchedule int

const (
	First WeekSchedule = iota
	Second
	Third
	Fourth
	Last
	Teenth
)

func Day(wSched WeekSchedule, wDay time.Weekday, month time.Month, year int) int {
	if wSched == Teenth {
		first := time.Date(year, month, 1, 0, 0, 0, 0, time.UTC)
		thirteenthDay := (first.Weekday() + 12) % 7
		return 13 + int((wDay-thirteenthDay+7)%7)
	}
	if wSched == Last {
		// zeroeth day of the next month is normalized to the last day of the current month
		last := time.Date(year, month+1, 0, 0, 0, 0, 0, time.UTC)
		return last.Day() - int((last.Weekday()-wDay+7)%7)
	}
	first := time.Date(year, month, 1, 0, 0, 0, 0, time.UTC)
	return first.Day() + int((wDay-first.Weekday()+7)%7) + int(wSched)*7
}
