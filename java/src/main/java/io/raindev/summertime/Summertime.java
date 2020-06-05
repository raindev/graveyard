package io.raindev.summertime;

import java.util.List;
import java.util.Optional;

final class Summertime {
    static Optional<Integer> summerStart(final List<Double> temperaturesCelsius) {
	if (temperaturesCelsius.isEmpty()) {
	    return Optional.empty();
	}
	var start = Optional.of(0);
	var threshold = temperaturesCelsius.get(0);
	var max = threshold;
	for (var i = 1; i < temperaturesCelsius.size(); i++) {
	    if (temperaturesCelsius.get(i) > max) {
		max = temperaturesCelsius.get(i);
	    }
	    if (temperaturesCelsius.get(i) >= threshold) {
		if (start.isEmpty()) {
		    start = Optional.of(i);
		}
	    } else {
		start = Optional.empty();
		threshold = max;
	    }
	}
	return start;
    }
}
