const std = @import("std");
const ascii = std.ascii;
const mem = std.mem;

/// Returns the set of strings in `candidates` that are anagrams of `word`.
/// Caller owns the returned memory.
pub fn detectAnagrams(
    allocator: mem.Allocator,
    word: []const u8,
    candidates: []const []const u8,
) !std.BufSet {
    const target = freq(word);
    var set = std.BufSet.init(allocator);
    for (candidates) |c| if (!ascii.eqlIgnoreCase(word, c) and mem.eql(u8, &target, &freq(c))) try set.insert(c);
    return set;
}

fn freq(word: []const u8) [26]u8 {
    var counts = [_]u8{0} ** 26;
    for (word) |c| counts[ascii.toLower(c) - 'a'] += 1;
    return counts;
}
