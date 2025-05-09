import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public final class Gigasecond {
    private final LocalDateTime moment;

    public Gigasecond(final LocalDateTime moment) {
        this.moment = moment.plus(1_000_000_000, ChronoUnit.SECONDS);
    }

    public Gigasecond(final LocalDate moment) {
        this(moment.atStartOfDay());
    }

    public LocalDateTime getDateTime() {
        return moment;
    }
}
