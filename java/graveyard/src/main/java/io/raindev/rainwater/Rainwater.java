package io.raindev.rainwater;

import java.util.ArrayDeque;
import static java.lang.Math.min;

class Rainwater {
    static int trap(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        var rightBounds = new ArrayDeque<Integer>();
        rightBounds.push(heights.length - 1);
        for (int x = heights.length - 1; x >= 0; --x) {
            if (heights[x] > heights[rightBounds.peek()]) {
                rightBounds.push(x);
            }
        }
        var result = 0;
        var leftBound = heights[0];
        for (var x = 0; x < heights.length; ++x) {
            final var height = heights[x];
            final var groundLevel = min(leftBound, heights[rightBounds.peek()]);
            if (height < groundLevel) {
                result += groundLevel - height;
            }
            if (height > leftBound) {
                leftBound = height;
            }
            if (x == rightBounds.peek()) {
                rightBounds.pop();
            }
        }
        return result;
    }
}
