const earth_year_seconds = 31_557_600.0;

pub const Planet = enum {
    mercury,
    venus,
    earth,
    mars,
    jupiter,
    saturn,
    uranus,
    neptune,

    pub fn age(self: Planet, seconds: usize) f64 {
        return @as(f64, @floatFromInt(seconds)) / earth_year_seconds / self.orbit_earth_years();
    }

    fn orbit_earth_years(self: Planet) f64 {
        switch (self) {
            .mercury => return 0.2408467,
            .venus => return 0.61519726,
            .earth => return 1.0,
            .mars => return 1.8808158,
            .jupiter => return 11.862615,
            .saturn => return 29.447498,
            .uranus => return 84.016846,
            .neptune => return 164.79132,
        }
    }
};
