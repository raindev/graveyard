pub const ComputationError = error{IllegalArgument};

pub fn steps(number: usize) ComputationError!usize {
    if (number == 0) {
        return ComputationError.IllegalArgument;
    }
    var i: usize = 0;
    var n = number;
    while (n > 1) : (i += 1) {
        n = if (n % 2 == 0) n / 2 else 3 * n + 1;
    }
    return i;
}

pub fn main() void {}
