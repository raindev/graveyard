package io.raindev.pathsum;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class PathSumTest {
    @Test public void sample() {
        var targetSum = 22;
        assertEquals(List.of(List.of(5, 4, 11, 2),  List.of(5, 8, 4, 5)),
            PathSum.paths(targetSum,
                new TreeNode(5,
                    new TreeNode(4,
                        new TreeNode(11,
                            new TreeNode(7), new TreeNode(2)),
                        null),
                    new TreeNode(8,
                        new TreeNode(13),
                        new TreeNode(4,
                            new TreeNode(5), new TreeNode(1))))));
    }

    @Test public void negativeSum() {
        var targetSum = -5;
        assertEquals(List.of(List.of(-2, -3)),
            PathSum.paths(targetSum,
                new TreeNode(-2,
                    new TreeNode(-3),
                    new TreeNode(-1))));
    }

    @Test public void noPaths() {
        var targetSum = 5;
        assertEquals(List.of(),
            PathSum.paths(targetSum,
                new TreeNode(1,
                    new TreeNode(2),
                    new TreeNode(5))));
    }

    @Test public void zeroSum() {
        var targetSum = 0;
        assertEquals(List.of(),
            PathSum.paths(targetSum,
                new TreeNode(1,
                    new TreeNode(2),
                    null)));
    }
}
