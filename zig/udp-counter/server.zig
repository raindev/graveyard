const std = @import("std");
const udp = @import("udp.zig");

pub fn main() !void {
    const socket = try udp.UdpSocket.initIp6("::1", 65000);
    try socket.bind();
    const reader = socket.reader();

    var counter: usize = 0;

    while (counter < 999_999) {
        const new_count = try reader.readInt(usize, std.builtin.Endian.little);
        std.debug.print("new count: {d}\n", .{new_count});
        std.debug.assert(new_count > counter);
        counter = new_count;
    }
}
