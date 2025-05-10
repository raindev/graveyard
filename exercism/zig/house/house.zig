const prefix: []const u8 = "This is";
const lines = [_][]const u8{
    " the horse and the hound and the horn that belonged to",
    " the farmer sowing his corn that kept",
    " the rooster that crowed in the morn that woke",
    " the priest all shaven and shorn that married",
    " the man all tattered and torn that kissed",
    " the maiden all forlorn that milked",
    " the cow with the crumpled horn that tossed",
    " the dog that worried",
    " the cat that killed",
    " the rat that ate",
    " the malt that lay in",
};
const suffix: []const u8 = " the house that Jack built.";

pub fn recite(buffer: []u8, start_verse: u32, end_verse: u32) []const u8 {
    var pos: usize = 0;
    for (start_verse..end_verse + 1) |lines_included| {
        @memcpy(buffer[pos .. pos + prefix.len], prefix);
        pos += prefix.len;
        for (0..lines_included - 1) |i| {
            const line = lines[lines.len + 1 - lines_included + i];
            @memcpy(buffer[pos .. pos + line.len], line);
            pos += line.len;
        }
        @memcpy(buffer[pos .. pos + suffix.len], suffix);
        pos += suffix.len;
        if (lines_included < end_verse) {
            buffer[pos] = '\n';
            pos += 1;
        }
    }
    return buffer[0..pos];
}
