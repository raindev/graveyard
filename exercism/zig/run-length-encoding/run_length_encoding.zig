const std = @import("std");

pub fn encode(buff: []u8, string: []const u8) []u8 {
    var stream = std.io.fixedBufferStream(buff);
    const writer = stream.writer();
    var count: usize = 1;
    for (string, 0..) |char, i| {
        const nextChar = if (i + 1 < string.len) string[i + 1] else null;
        if (char != nextChar) {
            if (count != 1) writer.print("{d}", .{count}) catch unreachable;
            writer.writeByte(char) catch unreachable;
            count = 1;
        } else {
            count += 1;
        }
    }
    return stream.getWritten();
}

pub fn decode(buff: []u8, string: []const u8) []u8 {
    var buffIndex: usize = 0;
    var count: ?usize = null;
    for (string) |c| {
        if (!std.ascii.isDigit(c)) {
            const runLength = count orelse 1;
            @memset(buff[buffIndex .. buffIndex + runLength], c);
            buffIndex += runLength;
            count = null;
        } else {
            count = (count orelse 0) * 10 + (c - '0');
        }
    }
    return buff[0..buffIndex];
}
