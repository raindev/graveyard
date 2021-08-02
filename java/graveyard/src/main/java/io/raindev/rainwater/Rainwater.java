package io.raindev.rainwater;

class Rainwater {
    static int trap(int[] heights) {
        var result = 0;
        var elevation = 0;
        var start = 0;
        var end = heights.length;
        int elevationPoints;
        do {
            ++elevation;
            elevationPoints = 0;
            int leftBound = -1;
            for (var x = start; x < end; ++x) {
                if (heights[x] >= elevation) {
                    ++elevationPoints;
                    if (leftBound == -1) {
                        start = x;
                    } else {
                        if (leftBound < x - 1) {
                            result += x - leftBound - 1;
                        }
                    }
                    leftBound = x;
                }
            }
            end = leftBound + 1;
        } while (elevationPoints > 1);
        return result;
    }
}
