const std = @import("std");
const t = std.testing;

fn reverse_order(a: anytype, b: anytype) std.math.Order {
    if (a < b) {
        return .gt;
    } else if (a > b) {
        return .lt;
    } else if (a == b) {
        return .eq;
    }
    unreachable;
}
const Treap = std.Treap(u8, reverse_order);

// Count the number of smaller numbers to the right of each number.
fn count_smaller_right(comptime len: usize, numbers: [len]u8) [len]u8 {
    var treap_nodes: [len]Treap.Node = undefined;
    var next_node: usize = 0;

    var counts: [len]u8 = [_]u8{0} ** len;
    if (numbers.len == 0) return counts;
    var right_numbers: Treap = .{};
    // iterate from the end, populating the treap incrementally
    var i = numbers.len;
    while (i > 0) {
        i -= 1;
        const element = numbers[i];

        var entry = right_numbers.getEntryFor(element);
        entry.set(&treap_nodes[next_node]);
        next_node += 1;

        var iter = Treap.InorderIterator{ .current = entry.node };
        // skip the number itself
        if (iter.next() == null) continue;
        var count: u8 = 0;
        while (iter.next()) |_| : (count += 1) {}
        counts[i] = count;
    }
    return counts;
}

test "decreasing" {
    const numbers = [_]u8{ 11, 7, 3, 1 };
    try t.expectEqualSlices(u8, &[_]u8{ 3, 2, 1, 0 }, &count_smaller_right(numbers.len, numbers));
}

test "increasing" {
    const numbers = [_]u8{ 2, 4, 5, 12 };
    try t.expectEqualSlices(u8, &[_]u8{ 0, 0, 0, 0 }, &count_smaller_right(numbers.len, numbers));
}

test "non-monotonic" {
    const numbers = [_]u8{ 8, 9, 1, 3 };
    try t.expectEqualSlices(u8, &[_]u8{ 2, 2, 0, 0 }, &count_smaller_right(numbers.len, numbers));
}

test "repeating" {
    const numbers = [_]u8{ 1, 1, 1 };
    try t.expectEqualSlices(u8, &[_]u8{ 0, 0, 0 }, &count_smaller_right(numbers.len, numbers));
}

test "empty" {
    const numbers = [_]u8{};
    try t.expectEqualSlices(u8, &[_]u8{}, &count_smaller_right(numbers.len, numbers));
}

test "single" {
    const numbers = [_]u8{13};
    try t.expectEqualSlices(u8, &[_]u8{0}, &count_smaller_right(numbers.len, numbers));
}
