const std = @import("std");
const fmt = std.fmt;
const mem = std.mem;

pub fn recite(allocator: mem.Allocator, words: []const []const u8) (fmt.AllocPrintError || mem.Allocator.Error)![][]u8 {
    const len = words.len;
    if (len == 0) return &.{};
    const proverb = try allocator.alloc([]u8, len);
    var lines_allocated: usize = 0;
    errdefer {
        for (proverb[0..lines_allocated]) |line| allocator.free(line);
        allocator.free(proverb);
    }
    for (words[1..], 1..) |word, i| {
        proverb[i - 1] = try fmt.allocPrint(allocator, "For want of a {s} the {s} was lost.\n", .{ words[i - 1], word });
        lines_allocated += 1;
    }
    proverb[len - 1] = try fmt.allocPrint(allocator, "And all for the want of a {s}.\n", .{words[0]});
    return proverb;
}
