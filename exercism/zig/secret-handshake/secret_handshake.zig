const std = @import("std");
const enums = std.enums;
const mem = std.mem;

pub const Signal = enum { wink, double_blink, close_your_eyes, jump };

pub fn calculateHandshake(allocator: mem.Allocator, number: u5) mem.Allocator.Error![]const Signal {
    const reverse = number & 0x10 != 0;
    const bits_set = @popCount(number);
    const actions = try allocator.alloc(Signal, if (reverse) bits_set - 1 else bits_set);
    var i: usize = 0;
    for (enums.values(Signal)) |signal| {
        if (number & @as(u5, 1) << @intCast(@intFromEnum(signal)) != 0) {
            actions[if (reverse) actions.len - 1 - i else i] = signal;
            i += 1;
        }
    }
    return actions;
}
