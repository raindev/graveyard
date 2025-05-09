#[derive(Debug)]
pub struct HighScores<'a> {
    scores: &'a [u32],
}

fn ensure_sorted_from_end(slice: &mut [u32]) {
    for i in (1..slice.len()).rev() {
        if slice[i] > slice[i - 1] {
            slice.swap(i, i - 1);
        } else {
            break;
        }
    }
}

impl<'a> HighScores<'a> {
    pub fn new(scores: &'a [u32]) -> Self {
        Self { scores }
    }

    pub fn scores(&self) -> &[u32] {
        self.scores
    }

    pub fn latest(&self) -> Option<u32> {
        self.scores.last().copied()
    }

    pub fn personal_best(&self) -> Option<u32> {
        self.scores.iter().max().copied()
    }

    pub fn personal_top_three(&self) -> Vec<u32> {
        let mut top = Vec::with_capacity(3);
        for &score in self.scores {
            if top.len() < 3 {
                top.push(score);
                ensure_sorted_from_end(&mut top);
            } else if score > top[2] {
                top[2] = score;
                ensure_sorted_from_end(&mut top);
            }
        }
        top
    }
}
