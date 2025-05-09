const std = @import("std");
const mem = std.mem;
const ascii = std.ascii;

/// Encodes `s` using the Atbash cipher. Caller owns the returned memory.
pub fn encode(allocator: mem.Allocator, s: []const u8) mem.Allocator.Error![]u8 {
    // allocate upper boundary length for the result (if completely alphanumeric)
    var r = try std.ArrayList(u8).initCapacity(allocator, s.len + s.len / 5);
    for (s) |chr| {
        if (!ascii.isAlphanumeric(chr)) continue;

        if (r.items.len % 6 == 5) {
            r.appendAssumeCapacity(' ');
        }
        if (ascii.isDigit(chr)) {
            r.appendAssumeCapacity(chr);
        } else {
            r.appendAssumeCapacity('z' - (ascii.toLower(chr) - 'a'));
        }
    }
    return r.toOwnedSlice();
}

/// Decodes `s` using the Atbash cipher. Caller owns the returned memory.
pub fn decode(allocator: mem.Allocator, s: []const u8) mem.Allocator.Error![]u8 {
    // no reallocation or srinking, given proper spacing
    var r = try std.ArrayList(u8).initCapacity(allocator, s.len - s.len / 6);
    for (s) |chr| {
        if (chr == ' ') continue;
        if (ascii.isDigit(chr)) {
            try r.append(chr);
        } else {
            try r.append('a' + ('z' - chr));
        }
    }
    return r.toOwnedSlice();
}
