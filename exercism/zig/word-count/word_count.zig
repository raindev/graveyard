const std = @import("std");
const mem = std.mem;
const ascii = std.ascii;

/// Returns the counts of the words in `s`.
/// Caller owns the returned memory.
pub fn countWords(allocator: mem.Allocator, s: []const u8) !std.StringHashMap(u32) {
    var counts = std.StringHashMap(u32).init(allocator);
    var it = mem.tokenizeAny(u8, s, " .,:\n!&@$%^&");
    while (it.next()) |word| {
        const trimmed = std.mem.trim(u8, word, "'");
        if (trimmed.len == 0) continue; // apostroph separated by delimiters
        const w = try std.ascii.allocLowerString(allocator, trimmed);
        const entry = try counts.getOrPut(w);
        if (entry.found_existing) {
            entry.value_ptr.* += 1;
            allocator.free(w);
        } else {
            entry.value_ptr.* = 1;
        }
    }
    return counts;
}
