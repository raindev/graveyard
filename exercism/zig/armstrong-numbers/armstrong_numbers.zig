const std = @import("std");

pub fn isArmstrongNumber(num: u128) bool {
    if (num == 0) return true;

    // log2(log10(2^128)) is ~5.3
    const numf64: f64 = @floatFromInt(num);
    const digits: u6 = @intFromFloat(@ceil(@log10(numf64)));
    var sum: u129 = 0;
    var n = num;
    while (n != 0) : (n /= 10) {
        sum += std.math.pow(u128, n % 10, digits);
    }
    std.debug.print("\nsum={d}\n", .{sum});
    return sum == num;
}
