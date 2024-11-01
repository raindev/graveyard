const std = @import("std");

pub fn build(b: *std.Build) void {
    const target = b.standardTargetOptions(.{});

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
        b.installArtifact(exe);
    }
}
