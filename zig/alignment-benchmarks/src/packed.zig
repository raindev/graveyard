const std = @import("std");
const math = std.math;

const PackedStruct = packed struct {
    a: u16,
    b: u16,
    c: u8,
};

pub fn main() !void {
    std.debug.print("Size of PackedStruct: {}\n", .{@sizeOf(PackedStruct)});
    std.debug.print("Bit size of PackedStruct: {}\n", .{@bitSizeOf(PackedStruct)});
    std.debug.print("Alignment of PackedStruct: {}\n", .{@alignOf(PackedStruct)});
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();

    const count = 100_000_000;
    var items: []PackedStruct = try allocator.alloc(PackedStruct, count);
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
