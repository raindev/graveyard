const std = @import("std");

pub const UdpSocket = struct {
    handle: std.posix.socket_t,
    addr: std.net.Address,

    const Reader = std.io.Reader(UdpSocket, std.posix.RecvFromError, read);
    const Writer = std.io.Writer(UdpSocket, std.posix.SendToError, write);

    pub fn initIp6(host: []const u8, port: u16) !UdpSocket {
        const domain = std.posix.AF.INET6;
        const tpe = std.posix.SOCK.DGRAM;
        const protocol = std.posix.IPPROTO.UDP;
        const socket = try std.posix.socket(domain, tpe, protocol);
        errdefer std.posix.close(socket);

        const addr = try std.net.Address.resolveIp6(host, port);

        return .{
            .handle = socket,
            .addr = addr,
        };
    }

    pub fn bind(self: UdpSocket) !void {
        try std.posix.bind(self.handle, &self.addr.any, self.addr.getOsSockLen());
    }

    pub fn read(self: UdpSocket, buffer: []u8) std.posix.RecvFromError!usize {
        return try std.posix.recv(self.handle, buffer, 0);
    }

    pub fn reader(self: UdpSocket) Reader {
        return .{ .context = self };
    }

    pub fn write(self: UdpSocket, bytes: []const u8) std.posix.SendToError!usize {
        return try std.posix.sendto(self.handle, bytes, 0, &self.addr.any, self.addr.getOsSockLen());
    }

    pub fn writer(self: UdpSocket) Writer {
        return .{ .context = self };
    }
};
