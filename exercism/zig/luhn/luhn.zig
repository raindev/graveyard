pub fn isValid(s: []const u8) bool {
    if (s.len <= 1) return false;
    var sum: usize = 0;
    var d: u8 = 1;
    for (0..s.len) |i| {
        const chr = s[s.len - i - 1];
        if (chr == ' ') continue;
        if (chr < '0' or chr > '9') return false;
        const n: u8 = chr - '0';
        if (n != 0) { // avoid underflow
            sum += ((2 - d % 2) * n - 1) % 9 + 1;
        }
        d += 1;
    }
    return d > 2 and sum % 10 == 0;
}
