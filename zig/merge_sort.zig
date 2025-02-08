const std = @import("std");
const assert = std.debug.assert;

fn sort(comptime T: type, items: []T, buf: []T) void {
    assert(buf.len == items.len);
    if (items.len <= 1) {
        return;
    }

    const midpoint = items.len / 2;
    var left = items[0..midpoint];
    var right = items[midpoint..];
    sort(u8, left, buf[0..left.len]);
    sort(u8, right, buf[0..right.len]);

    var l: usize, var r: usize, var i: usize = .{ 0, 0, 0 };
    while (l < left.len and r < right.len) : (i += 1) {
        if (left[l] <= right[r]) {
            buf[i] = left[l];
            l += 1;
        } else {
            buf[i] = right[r];
            r += 1;
        }
    }
    if (l < left.len) {
        @memcpy(buf[i..], left[l..]);
    } else if (r < right.len) {
        @memcpy(buf[i..], right[r..]);
    }
    @memcpy(items, buf);
}

test "empty" {
    sort(u8, &[_]u8{}, &[_]u8{});
}

test "single items" {
    var input = [_]u8{17};
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{17}, input);
}

test "already sorted" {
    var input = [_]u8{ 1, 3, 7, 11, 13 };
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{ 1, 3, 7, 11, 13 }, input);
}

test "two unsorted" {
    var input = [_]u8{ 2, 1 };
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{ 1, 2 }, input);
}

test "three unsorted" {
    var input = [_]u8{ 5, 3, 1 };
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{ 1, 3, 5 }, input);
}

test "inverse" {
    var input = [_]u8{ 7, 5, 4, 3, 1 };
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{ 1, 3, 4, 5, 7 }, input);
}

test "sample" {
    var input = [_]u8{ 9, 2, 5, 1, 5, 2 };
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{ 1, 2, 2, 5, 5, 9 }, input);
}

test "duplicates" {
    var input = [_]u8{ 2, 2, 2, 2 };
    var buf: [input.len]u8 = undefined;
    sort(u8, &input, &buf);
    try std.testing.expectEqual([_]u8{ 2, 2, 2, 2 }, input);
}
