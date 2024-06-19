const t = @import("std").testing;

// Calculate optimal robbery plan that doesn't rob two neighbours.
fn cash_out(houses: []const u8) u32 {
    if (houses.len == 0) return 0;
    if (houses.len == 1) return houses[0];
    const pick_first = houses[0] + if (houses.len < 3) 0 else cash_out(houses[2..]);
    const pick_second = houses[1] + if (houses.len < 4) 0 else cash_out(houses[3..]);
    return @max(pick_first, pick_second);
}

test "sample" {
    try t.expectEqual(19, cash_out(&[_]u8{ 7, 9, 3, 5, 6, 5 }));
}

test "outlier" {
    try t.expectEqual(67, cash_out(&[_]u8{ 9, 7, 5, 53, 6, 5 }));
}

test "no houses" {
    try t.expectEqual(0, cash_out(&[_]u8{}));
}

test "one house" {
    try t.expectEqual(3, cash_out(&[_]u8{3}));
}

test "two houses" {
    try t.expectEqual(5, cash_out(&[_]u8{ 1, 5 }));
}

test "middle of three houses" {
    try t.expectEqual(9, cash_out(&[_]u8{ 1, 9, 2 }));
}

test "two of three" {
    try t.expectEqual(9, cash_out(&[_]u8{ 5, 6, 4 }));
}

test "skip two of four" {
    try t.expectEqual(18, cash_out(&[_]u8{ 6, 5, 4, 12 }));
}
