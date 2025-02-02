const std = @import("std");
const t = std.testing;

// Count the number of smaller numbers to the right of each number.
fn count_smaller_right(comptime len: usize, numbers: [len]u8) [len]u8 {
    var counts: [len]u8 = [_]u8{0} ** len;
    for (numbers, 0..) |n, i| for (numbers[i + 1 ..]) |right_n|
        if (n > right_n) {
            counts[i] += 1;
        };
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
