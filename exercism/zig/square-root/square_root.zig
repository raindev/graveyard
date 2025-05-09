pub fn squareRoot(radicand: usize) usize {
    if (radicand < 2) return radicand;

    // use the Babylonian's aka. Heron's method
    var res = radicand / 2;
    while (true) {
        const next = (res + radicand / res) / 2;
        if (next == res) {
            return res;
        }
        res = next;
    }
}
