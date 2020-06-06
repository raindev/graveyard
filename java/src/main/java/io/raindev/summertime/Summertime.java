package io.raindev.summertime;

import java.util.List;
import java.util.Optional;

final class Summertime {

    static Optional<Integer> summerStart(final List<Double> temperatures) {
	if (temperatures.isEmpty()) {
	    return Optional.empty();
	}
	var start = Optional.of(0);
	// all summer temperatures are above it, all winter temperatures - below
	var threshold = temperatures.get(0);
	var max = threshold;
	for (var i = 1; i < temperatures.size(); i++) {
	    if (temperatures.get(i) > max) {
		max = temperatures.get(i);
	    }
	    if (temperatures.get(i) >= threshold) {
		if (start.isEmpty()) {
		    // threshold crossed - potentially the first day of summer
		    start = Optional.of(i);
		}
	    } else {
		// temperature is below threshold - still winter
		start = Optional.empty();
		threshold = max;
	    }
	}
	return start;
    }

}
