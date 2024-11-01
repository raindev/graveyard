const std = @import("std");

pub fn build(b: *std.Build) void {
    const target = b.standardTargetOptions(.{});
    const opts = .{ .target = target, .optimize = .ReleaseFast };
    const zbench_module = b.dependency("zbench", opts).module("zbench");

    const binaries = [_][]const u8{
        "aligned",
        "packed",
        "extern",
        "extern-byte",
    };
    inline for (binaries) |binary| {
        const exe = b.addExecutable(.{
            .name = binary,
            .root_source_file = b.path("src/" ++ binary ++ ".zig"),
            .target = target,
            .optimize = .ReleaseFast,
        });
        exe.root_module.addImport("zbench", zbench_module);
        b.installArtifact(exe);
    }
}
