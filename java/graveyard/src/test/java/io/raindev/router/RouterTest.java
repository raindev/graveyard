package io.raindev.router;

import java.util.List;
import java.util.Optional;

import io.raindev.router.Router.PathNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RouterTest {
    List<String> paths = List.of(
            "/",
            "/users/",
            "/users/{userId}",
            "/users/{userId}/books",
            "/books",
            "/books/{bookId}",
            "/static/images");
    Router router = new Router(paths);

    @Test public void constructPathTree() {
        var expectedTree = new PathNode(true,
            new PathNode("users", true,
                new PathNode("{userId}", true,
                    new PathNode("books", true))),
            new PathNode("books", true,
                new PathNode("{bookId}", true)),
            new PathNode("static", false,
                new PathNode("images", true))
        );
        var actualTree = Router.constructTree(paths);
        assertEquals(expectedTree, actualTree);
    }

    @Test public void matchingRoot() {
        assertEquals(Optional.of("/"), router.match("/"));
    }

    @Test public void matchingFullPath() {
        assertEquals(
                Optional.of("/static/images"),
                router.match("/static/images"));
    }

    @Test public void matchingSubPath() {
        assertEquals(
                Optional.of("/books"),
                router.match("/books"));
    }

    @Test public void matchingWildcard() {
        assertEquals(
                Optional.of("/users/{userId}/books"),
                router.match("/users/5/books"));
    }

    @Test public void mismatchingPath() {
        assertEquals(
                Optional.empty(),
                router.match("/books/3/author"));
    }

    @Test public void pathTooShort() {
        assertEquals(
                Optional.empty(),
                router.match("/static"));
    }

    @Test public void pathTooLong() {
        assertEquals(
                Optional.empty(),
                router.match("/static/images/books"));
    }

}
