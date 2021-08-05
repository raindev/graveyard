package io.raindev.pathsum;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class PathSum {
    public static List<List<Integer>> paths(int targetSum, TreeNode root) {
        if (root == null) {
            return List.of();
        }
        if (root.left == null && root.right == null) {
            if (root.val == targetSum) {
                var path = new LinkedList<Integer>();
                path.add(root.val);
                return List.of(path);
            } else {
                return List.of();
            }
        }
        final var leftPaths = paths(targetSum - root.val, root.left);
        final var rightPaths = paths(targetSum - root.val, root.right);
        final var result = new ArrayList<List<Integer>>(leftPaths.size() + rightPaths.size());
        result.addAll(leftPaths);
        result.addAll(rightPaths);
        for (var path : result) {
            path.add(0, root.val);
        }
        return result;
    }
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    @Override public String toString() {
        return "[" + val + ":" + left + "," + right + "]";
    }
}
