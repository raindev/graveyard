class Year {
  let year: Int
  lazy var isLeapYear = year.isMultiple(of: 4)
      && (!year.isMultiple(of: 100) || year.isMultiple(of: 400))

  init(calendarYear: Int) {
    year = calendarYear
  }

}
