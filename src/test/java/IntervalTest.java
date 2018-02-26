import com.ef.logparser.model.Interval;
import org.junit.Test;

import java.time.LocalDateTime;
import static org.junit.Assert.*;

public class IntervalTest {

	@Test
	public void shoudCreateADailyDuration() {
		LocalDateTime begin = LocalDateTime.of(2018, 1, 10, 20, 44);
		LocalDateTime end = begin.plusDays(1);
		Interval duration = Interval.daily(begin);
		assertEquals(duration.getInitialDate(), begin);
		assertEquals(duration.getEndDate(), end);
        Interval parsedDuration = Interval.chooseFormat(begin, "daily");
		assertEquals(duration, parsedDuration);
	}
	
	@Test
	public void shoudCreateAHourlyDuration() {
		LocalDateTime begin = LocalDateTime.of(2018, 1, 10, 20, 44);
		LocalDateTime end = begin.plusHours(1);
        Interval duration = Interval.hourly(begin);
		assertEquals(duration.getInitialDate(), begin);
		assertEquals(duration.getEndDate(), end);
        Interval parsedDuration = Interval.chooseFormat(begin, "hourly");
		assertEquals(duration, parsedDuration);
	}

}
