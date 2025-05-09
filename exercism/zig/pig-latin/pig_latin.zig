const std = @import("std");
const mem = std.mem;
const fmt = std.fmt;

pub fn translate(allocator: mem.Allocator, phrase: []const u8) mem.Allocator.Error![]u8 {
    var word_it = mem.tokenizeScalar(u8, phrase, ' ');
    const buf = try allocator.alloc(u8, phrase.len + 2 * (mem.count(u8, phrase, " ") + 1));
    var bufPos: usize = 0;
    while (word_it.next()) |word| {
        bufPos += translateWord(word, buf[bufPos..]);
        if (bufPos < buf.len) { // avoid trailing space
            buf[bufPos] = ' ';
            bufPos += 1;
        }
    }
    return buf;
}

fn translateWord(word: []const u8, buf: []u8) usize {
    var res: []u8 = undefined;
    if (isVowel(word[0]) or mem.startsWith(u8, word, "xr") or mem.startsWith(u8, word, "yt")) {
        res = fmt.bufPrint(buf, "{s}ay", .{word}) catch unreachable;
    } else {
        var consonants = startConsonants(word);
        if (mem.startsWith(u8, word[consonants - 1 ..], "qu")) {
            consonants += 1;
        }
        res = fmt.bufPrint(buf, "{s}{s}ay", .{ word[consonants..], word[0..consonants] }) catch unreachable;
    }
    return res.len;
}

fn startConsonants(word: []const u8) usize {
    return for (0.., word) |i, c| {
        if (isVowel(c) or (c == 'y' and i != 0)) break i;
    } else word.len;
}

fn isVowel(c: u8) bool {
    return switch (c) {
        'a', 'e', 'i', 'o', 'u' => true,
        else => false,
    };
}
