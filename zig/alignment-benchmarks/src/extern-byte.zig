const std = @import("std");
const math = std.math;

const ExternStruct = extern struct {
    a: u16 align(1),
    b: u16 align(1),
    c: u8,
};

pub fn main() !void {
    std.debug.print("Size of ExternStruct: {}\n", .{@sizeOf(ExternStruct)});
    std.debug.print("Bit size of ExternStruct: {}\n", .{@bitSizeOf(ExternStruct)});
    std.debug.print("Alignment of ExternStruct: {}\n", .{@alignOf(ExternStruct)});
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();

    const count = 100_000_000;
    var items: []ExternStruct = try allocator.alloc(ExternStruct, count);
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
