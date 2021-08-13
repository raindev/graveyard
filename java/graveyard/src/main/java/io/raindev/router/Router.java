package io.raindev.router;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

class Router {
    static class PathNode {
        // "" for the root node
        private final String component;
        private boolean isLeaf = false;
        private final List<PathNode> children = new ArrayList<>();

        private PathNode() {
            this("");
        }

        PathNode(String component) {
            this.component = component;
        }

        // test constructor, can break invariants
        PathNode(boolean isLeaf, PathNode... children) {
            this("", isLeaf, children);
        }

        // test constructor, can break invariants
        PathNode(String component, boolean isLeaf, PathNode... children) {
            this(component);
            this.isLeaf = isLeaf;
            for (var child : children) {
                this.children.add(child);
            }
        }

        private void add(String path) {
            add(components(path));
        }

        private void add(List<String> pathComponents) {
            if (pathComponents.isEmpty()) {
                this.isLeaf = true;
                return;
            }
            final var nextComponent = pathComponents.get(0);
            this.children.stream()
                .filter(c -> c.component.equals(nextComponent))
                .findFirst()
                .orElseGet(() -> {
                    final var newChild = new PathNode(nextComponent);
                    this.children.add(newChild);
                    return newChild;
                })
                .add(pathComponents.subList(1, pathComponents.size()));
        }

        @Override public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!getClass().equals(other.getClass())) {
                return false;
            }
            final var otherPath = (PathNode) other;
            return Objects.equals(component, otherPath.component)
                && Objects.equals(isLeaf, otherPath.isLeaf)
                && Objects.equals(children, otherPath.children);
        }

        @Override public String toString() {
            return this.component + (isLeaf ? "*" : "") +"=> "
                + this.children.stream()
                    .map(Object::toString)
                    .collect(Collectors.joining(",", "[", "]"));
        }
    }

    private static List<String> components(String path) {
        if (path.equals("/")) {
            return List.of();
        }
        final var components = path.split("/");
        return new ArrayList<>(Arrays.asList(Arrays.copyOfRange(components,
                    1, components.length)));
    }


    private final PathNode root;

    Router(List<String> paths) {
        root = constructTree(paths);
    }

    static PathNode constructTree(List<String> paths) {
        final var root = new PathNode();
        paths.forEach(root::add);
        return root;
    }

    Optional<String> match(String path) {
        final var components = components(path);
        var currNode = root;
        var result = new StringBuilder();
        while (!components.isEmpty() && !currNode.children.isEmpty()) {
            var currComponent = components.remove(0);
            var childMatch = false;
            for (var child : currNode.children) {
                if (componentMatches(child.component, currComponent)) {
                    childMatch = true;
                    currNode = child;
                    result.append("/" + child.component);
                }
            }
            if (!childMatch) {
                return Optional.empty();
            }
        }
        if (!components.isEmpty() || !currNode.isLeaf) {
            return Optional.empty();
        }
        return Optional.of(result.length() == 0 ? "/" : result.toString());
    }

    boolean componentMatches(String pattern, String component) {
        return isWildcard(pattern) || pattern.equals(component);
    }

    boolean isWildcard(String pattern) {
        return pattern.startsWith("{") && pattern.endsWith("}");
    }

}
