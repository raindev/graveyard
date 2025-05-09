const std = @import("std");

pub const ChessboardError = error{IndexOutOfBounds};

pub fn square(index: usize) ChessboardError!u64 {
    if (index == 0 or index > 64) return ChessboardError.IndexOutOfBounds;
    return @as(usize, 1) << @intCast(index - 1);
}

pub fn total() u64 {
    return std.math.maxInt(u64);
}
