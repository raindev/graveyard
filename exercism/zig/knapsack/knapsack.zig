const mem = @import("std").mem;

pub const Item = struct {
    weight: usize,
    value: usize,

    pub fn init(weight: usize, value: usize) Item {
        return .{ .weight = weight, .value = value };
    }
};

const MaxValueForLimits = struct {
    const Self = @This();

    max_values: []?usize, // [item_count x max_weight]
    max_weight: usize, // row size
    allocator: mem.Allocator,

    fn init(allocator: mem.Allocator, item_count: usize, max_weight: usize) !Self {
        const matrix = try allocator.alloc(?usize, item_count * max_weight);
        @memset(matrix, null);
        return Self{
            .max_values = matrix,
            .max_weight = max_weight,
            .allocator = allocator,
        };
    }

    fn deinit(self: Self) void {
        self.allocator.free(self.max_values);
    }

    fn get(self: Self, item_limit: usize, weight_limit: usize) *?usize {
        return &self.max_values[item_limit * self.max_weight + weight_limit];
    }
};

pub fn maximumValue(allocator: mem.Allocator, weight_limit: usize, items: []const Item) !usize {
    const state = try MaxValueForLimits.init(allocator, items.len, weight_limit);
    defer state.deinit();
    return max(state, weight_limit, items);
}

// keep already computed intermediate values in a table array
pub fn max(state: MaxValueForLimits, weight_limit: usize, items: []const Item) !usize {
    if (weight_limit == 0 or items.len == 0) return 0;

    const item_index = items.len - 1;
    const item = items[item_index];
    const other_items = items[0..item_index];

    const val = state.get(item_index, weight_limit - 1);
    if (val.* != null) return val.*.?;

    const res = if (item.weight > weight_limit)
        // the item doesn't fit and is not considered
        try max(state, weight_limit, other_items)
    else
        // the item fits and is either taken or not depending which yields the better outcome
        @max(try max(state, weight_limit, other_items), item.value + try max(state, weight_limit - item.weight, other_items));

    val.* = res;
    return res;
}
