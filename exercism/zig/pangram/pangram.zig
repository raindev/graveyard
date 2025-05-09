const std = @import("std");

pub fn isPangram(str: []const u8) bool {
    var flags: usize = 0b00000000000000000000000000;
    for (str) |char| {
        if (std.ascii.isAlphabetic(char)) {
            const shift: u5 = @intCast((std.ascii.toLower(char)) - 'a');
            flags |= @as(u26, 1) << shift;
        }
    }
    return flags == 0b11111111111111111111111111;
}
