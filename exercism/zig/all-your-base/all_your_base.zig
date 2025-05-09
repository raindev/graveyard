const std = @import("std");
const mem = std.mem;
const math = std.math;

pub const ConversionError = error{
    InvalidInputBase,
    InvalidOutputBase,
    InvalidDigit,
};

/// Converts `digits` from `input_base` to `output_base`, returning a slice of digits.
/// Caller owns the returned memory.
pub fn convert(
    allocator: mem.Allocator,
    digits: []const u32,
    in_base: u32,
    out_base: u32,
) (mem.Allocator.Error || ConversionError)![]u32 {
    if (in_base <= 1) return ConversionError.InvalidInputBase;
    if (out_base <= 1) return ConversionError.InvalidOutputBase;

    var n: u32 = 0;
    for (digits) |d| {
        if (d >= in_base) return ConversionError.InvalidDigit;
        n = n * in_base + d;
    }
    if (n == 0) return allocator.dupe(u32, &.{0});
    const out_digits = try allocator.alloc(u32, math.log_int(u32, out_base, n) + 1);
    for (0..out_digits.len) |i| {
        out_digits[out_digits.len - 1 - i] = n % out_base;
        n /= out_base;
    }
    return out_digits;
}
