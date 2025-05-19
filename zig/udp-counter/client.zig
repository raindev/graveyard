const std = @import("std");
const udp = @import("udp.zig");

pub fn main() !void {
    const socket = try udp.UdpSocket.initIp6("::1", 65000);
    const writer = socket.writer();

    for (1..1_000_000) |counter| {
        std.debug.print("new count: {d}\n", .{counter});
        try writer.writeInt(usize, counter, std.builtin.Endian.little);
    }
}
