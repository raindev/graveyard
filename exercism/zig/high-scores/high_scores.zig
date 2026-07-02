const std = @import("std");

pub const HighScores = struct {
    scores: []const i32,
    top3: [3]i32,

    pub fn init(scores: []const i32) HighScores {
        var high_scores = HighScores{
            .scores = scores,
            .top3 = undefined,
        };
        top(scores, &high_scores.top3);
        return high_scores;
    }

    pub fn latest(self: *const HighScores) ?i32 {
        if (self.scores.len == 0) return null;
        return self.scores[self.scores.len - 1];
    }

    pub fn personalBest(self: *const HighScores) ?i32 {
        if (self.scores.len == 0) return null;
        return self.top3[0];
    }

    pub fn personalTopThree(self: *const HighScores) []const i32 {
        const k = @min(3, self.scores.len);
        return self.top3[0..k];
    }

    // TODO is it possible to avoid defining a function?
    fn lt(context: i32, a: i32, b: i32) bool {
        _ = context;
        return b < a;
    }

    fn top(scores: []const i32, top_k: []i32) void {
        if (scores.len == 0) return;
        if (scores.len == 1) {
            top_k[0] = scores[0];
            return;
        }
        if (scores.len == 2) {
            top_k[0] = @max(scores[0], scores[1]);
            top_k[1] = @min(scores[0], scores[1]);
            return;
        }

        const k = top_k.len;
        if (scores.len >= 3) {
            @memcpy(top_k, scores[0..k]);
            // TODO: is there a simpler way?
            const ctx: i32 = 0;
            std.mem.sort(i32, top_k, ctx, lt);
        }

        for (scores[3..]) |score| {
            if (score < top_k[k - 1]) continue;
            top_k[k - 1] = score;
            var i = k - 1;
            while (i >= 1 and top_k[i] > top_k[i-1]) : (i -= 1) {
                std.mem.swap(i32, &top_k[i], &top_k[i-1]);
            }
        }
    }
};
