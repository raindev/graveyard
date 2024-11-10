const std = @import("std");

const Res = struct { isize, usize };

fn climbFloors(instructions: []const u8) Res {
    var floor: isize, var basement_step: ?usize = .{ 0, null };
    for (instructions, 1..) |direction, step| {
        switch (direction) {
            '(' => floor += 1,
            ')' => floor -= 1,
            else => unreachable,
        }
        if (floor == -1 and basement_step == null) basement_step = step;
    }
    return .{ floor, basement_step orelse 0 };
}

test climbFloors {
    const testing = @import("std").testing;

    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();
    const input = try std.fs.cwd().readFileAlloc(allocator, "../input", 1024 * 1024);
    const end_floor, const basement_step = climbFloors(input);
    try testing.expectEqual(232, end_floor);
    try testing.expectEqual(1783, basement_step);
}
