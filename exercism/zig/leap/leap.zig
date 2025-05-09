pub fn isLeapYear(year: u32) bool {
    var year_state = false;
    if (year % 400 == 0) {
        year_state = true;
    } else if (year % 100 == 0) {
        year_state = false;
    } else if (year % 4 == 0) {
        year_state = true;
    } else {
        year_state = false;
    }
    return year_state;
}
