package io.raindev.rainwater;

class Rainwater {
    static int trap(int[] heights) {
        var result = 0;
        var elevation = 0;
        int elevationPoints;
        do {
            ++elevation;
            elevationPoints = 0;
            int leftBound = -1;
            for (var x = 0; x < heights.length; ++x) {
                if (heights[x] >= elevation) {
                    ++elevationPoints;
                    if (leftBound != -1) {
                        if (leftBound < x - 1) {
                            result += x - leftBound - 1;
                        }
                    }
                    leftBound = x;
                }
            }
        } while (elevationPoints > 1);
        return result;
    }
}
