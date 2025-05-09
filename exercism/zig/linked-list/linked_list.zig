const std = @import("std");

pub fn LinkedList(comptime T: type) type {
    return struct {
        const Self = @This();

        pub const Node = struct {
            prev: ?*Node = null,
            next: ?*Node = null,
            data: T,
        };

        first: ?*Node = null,
        last: ?*Node = null,
        len: usize = 0,

        pub fn push(self: *Self, n: *Node) void {
            n.prev = self.last;
            if (self.last) |last| {
                last.next = n;
            } else {
                self.first = n;
            }
            self.last = n;
            self.len += 1;
        }

        pub fn pop(self: *Self) ?Node {
            if (self.last) |last| {
                const n = last;
                self.last = last.prev;
                if (self.first == last) {
                    self.first = null;
                } else {
                    last.prev.?.next = null;
                    n.prev = null;
                    n.next = null;
                }
                self.len -= 1;
                return n.*;
            }
            return null;
        }

        pub fn shift(self: *Self) ?Node {
            if (self.first) |first| {
                const n = first;
                self.first = first.next;
                if (self.last == first) {
                    self.last = null;
                } else {
                    first.next.?.prev = null;
                    n.prev = null;
                    n.next = null;
                }
                self.len -= 1;
                return n.*;
            }
            return null;
        }

        pub fn unshift(self: *Self, n: *Node) void {
            n.next = self.first;
            if (self.first) |first| {
                first.prev = n;
            } else {
                self.last = n;
            }
            self.first = n;
            self.len += 1;
        }

        pub fn delete(self: *Self, target: *Node) void {
            var next = self.first;
            while (next) |n| : (next = n.next) {
                if (n.data == target.data) {
                    if (n == self.first) {
                        _ = shift(self);
                    } else if (n == self.last) {
                        _ = pop(self);
                    } else {
                        n.prev.?.next = n.next.?;
                        n.next.?.prev = n.prev;
                        self.len -= 1;
                    }
                    return;
                }
            }
        }
    };
}
