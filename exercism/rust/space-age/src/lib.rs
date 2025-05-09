#[derive(Debug)]
pub struct Duration(u64);

impl From<u64> for Duration {
    fn from(s: u64) -> Self {
        Duration(s)
    }
}

pub trait Planet {
    const SECONDS_IN_YEAR: u64;

    fn years_during(d: &Duration) -> f64 {
        let Duration(seconds) = d;
        *seconds as f64 / Self::SECONDS_IN_YEAR as f64
    }
}

const SECONDS_IN_EARTH_YEAR: u64 = 31_557_600;

macro_rules! define_planet {
    ($planet:ident, $earth_years:expr) => {
        pub struct $planet;

        impl Planet for $planet {
            const SECONDS_IN_YEAR: u64 =
                (SECONDS_IN_EARTH_YEAR as f64 * $earth_years) as u64;
        }
    }
}

define_planet!(Mercury, 0.2408467);
define_planet!(Venus, 0.61519726);
define_planet!(Earth, 1.0);
define_planet!(Mars, 1.8808158);
define_planet!(Jupiter, 11.862615);
define_planet!(Saturn, 29.447498);
define_planet!(Uranus, 84.016846);
define_planet!(Neptune, 164.79132);
