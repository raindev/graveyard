const std = @import("std");
const fs = std.fs;

fn materials(reader: fs.File.Reader) !struct { u32, u32 } {
    const fmt = std.fmt;
    const Add = std.builtin.ReduceOp.Add;
    const Min = std.builtin.ReduceOp.Min;

    var paper: u32 = 0;
    var ribbon: u32 = 0;
    // max expected value 255x255x255
    var buf: [12]u8 = undefined;
    while (try reader.readUntilDelimiterOrEof(&buf, '\n')) |read| {
        var iter = std.mem.tokenizeScalar(u8, read, 'x');
        const l: u32 = try fmt.parseInt(u8, iter.next().?, 10);
        const w: u32 = try fmt.parseInt(u8, iter.next().?, 10);
        const h: u32 = try fmt.parseInt(u8, iter.next().?, 10);
        const sides: @Vector(3, u32) = .{ l * w, w * h, h * l };
        paper += @reduce(Add, sides * @as(@Vector(3, u32), @splat(2)));
        paper += @reduce(Min, sides);
        const perimeters: @Vector(3, u32) = .{ 2 * (l + w), 2 * (w + h), 2 * (h + l) };
        ribbon += @reduce(Min, perimeters);
        ribbon += l * w * h;
        std.debug.assert(iter.next() == null);
    }
    return .{ paper, ribbon };
}

test materials {
    const expectEqual = std.testing.expectEqual;

    const file = try fs.cwd().openFile("../input", .{});
    const reader = file.reader();
    const paper, const ribbon = try materials(reader);
    try expectEqual(1598415, paper);
    try expectEqual(3812909, ribbon);
}
