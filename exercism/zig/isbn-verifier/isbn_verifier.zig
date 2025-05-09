pub fn isValidIsbn10(s: []const u8) bool {
    var digit: u4 = 10; // right to left starting 1
    var sum: u9 = 0; // sum i:10..1 9*i = 495
    for (s) |chr| {
        if (digit == 0) return false; // too long
        switch (chr) {
            '-' => continue,
            '0'...'9' => sum += digit * (chr - '0'),
            'X' => if (digit != 1) {
                return false; // X not in the last place
            } else {
                sum += 10;
            },
            else => return false, // unexpected character
        }
        digit -= 1;
    }
    return digit == 0 and sum % 11 == 0;
}
