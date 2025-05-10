const std = @import("std");

const days = [_][]const u8{
    "first",
    "second",
    "third",
    "fourth",
    "fifth",
    "sixth",
    "seventh",
    "eighth",
    "ninth",
    "tenth",
    "eleventh",
    "twelfth",
};
const presents = [_][]const u8{
    "a Partridge in a Pear Tree",
    "two Turtle Doves",
    "three French Hens",
    "four Calling Birds",
    "five Gold Rings",
    "six Geese-a-Laying",
    "seven Swans-a-Swimming",
    "eight Maids-a-Milking",
    "nine Ladies Dancing",
    "ten Lords-a-Leaping",
    "eleven Pipers Piping",
    "twelve Drummers Drumming",
};

pub fn recite(buffer: []u8, start_verse: u32, end_verse: u32) []const u8 {
    var fbs = std.io.fixedBufferStream(buffer);
    const writer = fbs.writer();
    for (start_verse..end_verse + 1) |verse| {
        writer.print("On the {s} day of Christmas my true love gave to me: ", .{days[verse - 1]}) catch unreachable;
        for (0..verse) |i| {
            const maybe_and = if (i < verse - 1 or verse == 1) "" else "and ";
			const present = presents[verse - 1 - i];
            const punctuation = if (i < verse - 1) ", " else ".";
            writer.print("{s}{s}{s}", .{ maybe_and, present, punctuation }) catch unreachable;
        }
        if (verse != end_verse) writer.writeByte('\n') catch unreachable;
    }
    return fbs.getWritten();
}
