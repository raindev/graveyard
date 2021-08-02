package io.raindev.rainwater;

import static java.lang.Math.min;

class Rainwater {
    static int trap(int[] heights) {
        if (heights.length == 0) {
            return 0;
        }
        var rightBounds = new short[heights.length];
        rightBounds[rightBounds.length - 1] = (short)heights[heights.length - 1];
        for (int x = heights.length - 2; x >= 0; --x) {
            rightBounds[x] = (short) Math.max(heights[x], rightBounds[x + 1]);
        }
        var result = 0;
        var leftBound = heights[0];
        for (var x = 0; x < heights.length; ++x) {
            final var height = heights[x];
            final var groundLevel = min(leftBound, rightBounds[x]);
            if (height < groundLevel) {
                result += groundLevel - height;
            }
            if (height > leftBound) {
                leftBound = height;
            }
        }
        return result;
    }
}
