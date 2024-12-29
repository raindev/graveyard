const std = @import("std");
const fs = std.fs;
const io = std.io;
const hash_map = std.hash_map;
const mem = std.mem;

const Position = struct { i32, i32 };
const Map = hash_map.AutoHashMap(Position, bool);

fn visited(allocator: mem.Allocator, reader: fs.File.Reader) !u32 {
    var buf_reader = io.bufferedReader(reader);
    var r = buf_reader.reader();
    var map = Map.init(allocator);
    defer map.deinit();

    try map.put(.{ 0, 0 }, true);
    var santa = Position{ 0, 0 };
    while (r.readByte()) |direction| {
        try move(direction, &santa, &map);
    } else |err| {
        switch (err) {
            error.EndOfStream => {},
            else => return err,
        }
    }
    return map.count();
}

fn visited_together(allocator: mem.Allocator, reader: fs.File.Reader) !u32 {
    var buf_reader = io.bufferedReader(reader);
    var r = buf_reader.reader();
    var map = Map.init(allocator);
    defer map.deinit();

    try map.put(.{ 0, 0 }, true);
    var santa = Position{ 0, 0 };
    var robo_santa = Position{ 0, 0 };
    var santas_turn = true;
    while (r.readByte()) |direction| {
        try move(direction, if (santas_turn) &santa else &robo_santa, &map);
        santas_turn = !santas_turn;
    } else |err| {
        switch (err) {
            error.EndOfStream => {},
            else => return err,
        }
    }
    return map.count();
}

fn move(direction: u8, pos: *Position, map: *Map) !void {
    switch (direction) {
        '<' => pos.@"0" -= 1,
        '>' => pos.@"0" += 1,
        '^' => pos.@"1" += 1,
        'v' => pos.@"1" -= 1,
        else => std.debug.assert(direction == '\x0A'), // LF
    }
    try map.put(pos.*, true);
}

test "houses visited also with Robo-Santa" {
    const expectEqual = std.testing.expectEqual;

    var gpa = std.heap.GeneralPurposeAllocator(.{}){};
    const allocator = gpa.allocator();
    const file = try fs.cwd().openFile("input", .{});
    const reader = file.reader();

    const houses_visited = try visited(allocator, reader);
    try expectEqual(2572, houses_visited);

    try file.seekTo(0);
    const houses_visited_together = try visited_together(allocator, reader);
    try expectEqual(2631, houses_visited_together);
}
