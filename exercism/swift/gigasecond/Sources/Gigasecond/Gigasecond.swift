import Foundation

struct Gigasecond {
  private static let formatter = {
    () -> ISO8601DateFormatter in
    let formatter = ISO8601DateFormatter()
    formatter.formatOptions = [
      .withFullDate,
      .withDashSeparatorInDate,
      .withTime,
      .withColonSeparatorInTime
    ]
    return formatter
  }()
  let moment: Date
  var description: String {
    Gigasecond.formatter.string(from: moment)
  }

  init?(from: String) {
    guard let fromDate = Gigasecond.formatter.date(from: from) else {
      return nil
    }
    self.moment = fromDate.addingTimeInterval(1_000_000_000)
  }

}
