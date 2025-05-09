const std = @import("std");
const ascii = std.ascii;
const LetterBitSet = std.bit_set.IntegerBitSet(26);

pub fn isIsogram(str: []const u8) bool {
    var bs: LetterBitSet = LetterBitSet.initEmpty();
    for (str) |chr| {
        if (!ascii.isAlphabetic(chr)) continue;

        const letterIndex = ascii.toLower(chr) - 'a';
        if (bs.isSet(letterIndex)) {
            return false;
        }
        bs.set(letterIndex);
    }
    return true;
}
