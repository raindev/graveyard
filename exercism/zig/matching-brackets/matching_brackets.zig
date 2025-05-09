const std = @import("std");

pub fn isBalanced(allocator: std.mem.Allocator, s: []const u8) !bool {
    var stack = std.ArrayList(u8).init(allocator);
    defer stack.deinit();
    for (s) |c| switch (c) {
        '(' => try stack.append(')'),
        '[' => try stack.append(']'),
        '{' => try stack.append('}'),
        ')', ']', '}' => if (stack.popOrNull() != c) return false,
        else => {},
    };
    return stack.items.len == 0;
}
