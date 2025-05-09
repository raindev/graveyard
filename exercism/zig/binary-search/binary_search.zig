pub fn binarySearch(comptime T: type, target: T, items: []const T) ?usize {
    if (items.len == 0) return null;
    var l: usize = 0;
    var r: usize = items.len - 1;
    while (l != r) {
        const m = (l + r + 1) / 2;
        if (target >= items[m]) l = m else r = m - 1;
    }
    if (items[l] == target) return l;
    return null;
}
