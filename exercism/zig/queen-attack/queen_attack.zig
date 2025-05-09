const std = @import("std");

pub const QueenError = error{
    InitializationFailure,
};

pub const Queen = struct {
    row: i8,
    col: i8,

    pub fn init(row: i8, col: i8) QueenError!Queen {
        if (row < 0 or row >= 8 or col < 0 or col >= 8) return QueenError.InitializationFailure;
        return Queen{ .row = row, .col = col };
    }

    pub fn canAttack(self: Queen, other: Queen) QueenError!bool {
        return self.row == other.row or
            self.col == other.col or
            (@abs(self.row - other.row) == @abs(self.col - other.col));
    }
};
