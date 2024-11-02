const std = @import("std");

const AbcStruct = extern struct {
    a: u16,
    b: u16,
    c: u32,
};

pub fn main() !u8 {
    var timer = try std.time.Timer.start();

    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();
    const alloc_init_ns = timer.lap();

    const count = 100_000_000;
    var items: []AbcStruct = try allocator.alloc(AbcStruct, count);
    const array_alloc_ns = timer.lap();

    for (0..count) |i|
        items[i] = .{
            .a = @truncate(i),
            .b = @truncate(i + 1),
            .c = @truncate(i + 2),
        };
    const array_init_ns = timer.lap();

    var even_a: usize = 0;
    for (items) |*item| {
        if (item.a % 2 == 0 and item.b % 2 == 0 and item.c % 2 == 0) even_a += 1;
    }
    const array_iter_ns = timer.lap();

    std.debug.print("{d:15}ns allocator initialization\n", .{alloc_init_ns});
    std.debug.print("{d:15}ns array allocation\n", .{array_alloc_ns});
    std.debug.print("{d:15}ns array initialization\n", .{array_init_ns});
    std.debug.print("{d:15}ns array iteration\n\n", .{array_iter_ns});
    return @truncate(even_a);
}
