const std = @import("std");

const numbers = [_][]const u8{
	"no",
	"one",
	"two",
	"three",
	"four",
	"five",
	"six",
	"seven",
	"eight",
	"nine",
	"ten",
};
const line_is = "{c}{s} green bottle{s} hanging on the wall,\n";
const line_if = "And if one green bottle should accidentally fall,\n";
const line_will = "There'll be {s} green bottle{s} hanging on the wall.";

pub fn recite(buffer: []u8, start_bottles: u32, take_down: u32) []const u8 {
	var fbs = std.io.fixedBufferStream(buffer);
	const writer = fbs.writer();

	for (0..take_down) |downed| {
		const before = numbers[start_bottles - downed];
		const before_s = if (downed + 1 == start_bottles) "" else "s";
		writer.print(line_is, .{std.ascii.toUpper(before[0]), before[1..], before_s}) catch unreachable;
		writer.print(line_is, .{std.ascii.toUpper(before[0]), before[1..], before_s}) catch unreachable;
		writer.print(line_if, .{}) catch unreachable;
		const after = numbers[start_bottles - downed - 1];
		const after_s = if (downed + 2 == start_bottles) "" else "s";
		writer.print(line_will, .{after, after_s}) catch unreachable;

		if (downed != take_down - 1) writer.writeAll("\n\n") catch unreachable;
	}

	return fbs.getWritten();
}
