const std = @import("std");
const mem = std.mem;

pub const Category = enum(u4) {
    ones = 1,
    twos,
    threes,
    fours,
    fives,
    sixes,
    full_house,
    four_of_a_kind,
    little_straight, // 9
    big_straight,
    choice,
    yacht,
};

pub fn score(dice: [5]u3, cat: Category) u32 {
    const cat_ord = @intFromEnum(cat);
    return switch (cat) {
        .ones, .twos, .threes, .fours, .fives, .sixes => counts(dice)[cat_ord - 1] * cat_ord,
        .full_house => for (counts(dice)) |c| {
            if (c > 3 or c == 1) break 0;
        } else total(dice),
        .four_of_a_kind => for (counts(dice), 1..) |c, n| {
            if (c >= 4) break @intCast(4 * n);
        } else 0,
        .little_straight, .big_straight => for (counts(dice)[cat_ord - 9 .. cat_ord - 4]) |c| {
            if (c != 1) break 0;
        } else 30,
        .choice => total(dice),
        .yacht => if (total(dice) == @as(u32, 5) * dice[0]) 50 else 0,
    };
}

fn counts(dice: [5]u3) [6]u3 {
    var res = [_]u3{0} ** 6;
    for (dice) |d| res[d - 1] += 1;
    return res;
}

fn total(dice: [5]u3) u32 {
    return @reduce(.Add, @as(@Vector(5, u8), dice));
}
