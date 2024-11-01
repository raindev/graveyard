const zbench = @import("zbench");
const std = @import("std");
const math = std.math;

const AlignedStruct = packed struct {
    a: u16,
    b: u16,
    c: u8,
};

pub fn main() !void {
    std.debug.print("Size of AlignedStruct: {}\n", .{@sizeOf(AlignedStruct)});
    std.debug.print("Bit size of AlignedStruct: {}\n", .{@bitSizeOf(AlignedStruct)});
    std.debug.print("Alignment of AlignedStruct: {}\n", .{@alignOf(AlignedStruct)});

    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();
    var bench = zbench.Benchmark.init(allocator, .{});
    defer bench.deinit();
    try bench.add("Aligned struct", run, .{});
    try bench.run(std.io.getStdOut().writer());
}

fn run(allocator: std.mem.Allocator) void {
    const count = 100_000_000;
    var items: []AlignedStruct = allocator.alloc(AlignedStruct, count) catch unreachable;
    defer allocator.free(items);
    for (0..count) |i| {
        items[i] = .{
            .a = @intCast(i % math.maxInt(u16)),
            .b = @intCast(i % math.maxInt(u16)),
            .c = @intCast(i % math.maxInt(u8)),
        };
    }

    var even_a: usize = 0;
    for (items) |*item| {
        if (item.a % 2 == 0) even_a += 1;
    }
    if (even_a % 2 == 0) std.debug.print("very even\n", .{});
}
