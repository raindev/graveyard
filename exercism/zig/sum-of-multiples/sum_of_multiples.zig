const std = @import("std");
const mem = std.mem;

pub fn sum(allocator: mem.Allocator, factors: []const u32, limit: u32) !u64 {
    var fs = std.hash_map.AutoHashMap(u32, void).init(allocator);
    defer fs.deinit();
    for (factors) |factor| {
        var f = factor;
        while (factor > 0 and f < limit) : (f += factor) {
            try fs.put(f, {});
        }
    }
    var s: u64 = 0;
    var it = fs.keyIterator();
    while (it.next()) |k| s += k.*;
    return s;
}
