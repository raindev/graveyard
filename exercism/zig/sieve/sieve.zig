const std = @import("std");

pub fn primes(buffer: []u32, limit: u32) []u32 {
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();
    // the first two bits aren't used, the indexes directly correspond to marked numbers
    var sieve = std.DynamicBitSet.initEmpty(allocator, limit + 1) catch |err| @panic(@errorName(err));
    var buff_idx: usize = 0;
    for (2..limit + 1) |n| {
        if (sieve.isSet(n)) continue;
        buffer[buff_idx] = @as(u32, @intCast(n));
        buff_idx += 1;
        // start from n^n as any composite numbers below are divisible by k<n and already marked
        var i = n * n;
        while (i <= limit) : (i += n) {
            sieve.set(i);
        }
    }
    return buffer[0..buff_idx];
}
