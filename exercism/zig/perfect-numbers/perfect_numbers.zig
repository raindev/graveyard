const std = @import("std");

pub const Classification = enum {
    deficient,
    perfect,
    abundant,
};

pub fn classify(n: u64) Classification {
    std.debug.assert(n != 0);
    const sum = aliquot_sum(n);
    if (n == sum) return .perfect;
    return if (n < sum) return .abundant else .deficient;
}

fn aliquot_sum(n: u64) u64 {
    if (n == 1) return 0;
    var sum: u64 = 1;
    var d: u64 = 2;
    while (d * d < n) : (d += 1) {
        if (n % d == 0) {
            sum += d;
            sum += n / d;
        }
    }
    if (d * d == n) sum += d;
    return sum;
}
