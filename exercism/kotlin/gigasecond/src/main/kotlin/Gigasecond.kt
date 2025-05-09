import java.time.LocalDate
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class Gigasecond(date: LocalDateTime) {
    val date: LocalDateTime = date.plus(1_000_000_000, ChronoUnit.SECONDS)

    constructor(date: LocalDate) : this(date.atStartOfDay())
}
