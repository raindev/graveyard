const std = @import("std");
const mem = std.mem;

pub fn rotate(allocator: mem.Allocator, text: []const u8, shiftKey: u5) mem.Allocator.Error![]u8 {
    const res = try allocator.alloc(u8, text.len);
    for (text, res) |c, *r| {
        if (std.ascii.isAlphabetic(c)) {
            const a: u8 = if (std.ascii.isUpper(c)) 'A' else 'a';
            r.* = (a + (c - a + shiftKey) % 26);
        } else {
            r.* = c;
        }
    }
    return res;
}
