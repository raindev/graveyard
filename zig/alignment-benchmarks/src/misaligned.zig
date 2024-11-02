const std = @import("std");

const AbcStruct = extern struct {
    a: u16 align(1),
    b: u16 align(1),
    c: u8,
};

pub fn main() !u8 {
    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();

    const count = 100_000_000;
    var items: []AbcStruct = try allocator.alloc(AbcStruct, count);

    for (0..count) |i|
        items[i] = .{
            .a = @truncate(i),
            .b = @truncate(i + 1),
            .c = @truncate(i + 2),
        };

    var even_a: usize = 0;
    for (items) |*item| {
        if (item.a % 2 == 0 and item.b % 2 == 0 and item.c % 2 == 0) even_a += 1;
    }
    return @truncate(even_a);
}
