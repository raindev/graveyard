const std = @import("std");
const mem = std.mem;

pub fn abbreviate(allocator: mem.Allocator, words: []const u8) mem.Allocator.Error![]u8 {
    var it = mem.tokenizeAny(u8, words, " -_");
    const buff = try allocator.alloc(u8, wordCount(it));
    var i: usize = 0;
    while (it.next()) |word| : (i += 1) buff[i] = std.ascii.toUpper(word[0]);
    return buff;
}

fn wordCount(it: mem.TokenIterator(u8, .any)) usize {
    var iterator = it;
    var count: usize = 0;
    while (iterator.next()) |_| count += 1;
    return count;
}
