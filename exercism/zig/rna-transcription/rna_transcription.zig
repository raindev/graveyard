const mem = @import("std").mem;

pub fn toRna(allocator: mem.Allocator, dna: []const u8) mem.Allocator.Error![]const u8 {
    const rna: []u8 = try allocator.alloc(u8, dna.len);
    for (dna, 0..) |n, i| {
        rna[i] = switch (n) {
            'G' => 'C',
            'C' => 'G',
            'T' => 'A',
            'A' => 'U',
            else => unreachable,
        };
    }
    return rna;
}
