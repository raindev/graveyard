const std = @import("std");

pub fn response(s: []const u8) []const u8 {
    const phrase = std.mem.trim(u8, s, &std.ascii.whitespace);
    const silence = phrase.len == 0;
    if (silence) return "Fine. Be that way!";

    const yell = is_uppercase(phrase);
    const question = phrase[phrase.len - 1] == '?';
    if (yell and question) return "Calm down, I know what I'm doing!";
    if (yell) return "Whoa, chill out!";
    if (question) return "Sure.";

    return "Whatever.";
}

fn is_uppercase(s: []const u8) bool {
    var res = false;
    for (s) |c| {
        if (std.ascii.isLower(c)) return false;
        if (std.ascii.isUpper(c)) res = true;
    }
    return res;
}
