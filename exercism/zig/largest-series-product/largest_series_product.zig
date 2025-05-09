const std = @import("std");

pub const SeriesError = error{
    InvalidCharacter,
    NegativeSpan,
    InsufficientDigits,
};

pub fn largestProduct(digits: []const u8, span: i32) SeriesError!u64 {
    if (span < 0) return SeriesError.NegativeSpan;
    if (digits.len < span) return SeriesError.InsufficientDigits;
    const spanSize: usize = @intCast(span);
    if (spanSize == 0) return 1;

    var prod: u64 = try product(digits[0..spanSize]);
    var max: u64 = prod;
    for (digits[spanSize..], spanSize..) |chr, i| {
        if (digits[i - spanSize] != '0') {
            prod = prod * try toDigit(chr) / (try toDigit(digits[i - spanSize]));
        } else {
            prod = try product(digits[i + 1 - spanSize .. i + 1]);
        }
        if (prod > max) max = prod;
    }
    return max;
}

fn product(digits: []const u8) !u64 {
    var prod: u64 = 1;
    for (digits) |d| prod *= try toDigit(d);
    return prod;
}

fn toDigit(c: u8) !u8 {
    return std.fmt.charToDigit(c, 10);
}
