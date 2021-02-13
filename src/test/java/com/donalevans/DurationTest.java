package com.donalevans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@RunWith(Enclosed.class)
public class DurationTest {

  @RunWith(Parameterized.class)
  public static class ParameterizedTests {
    @Parameterized.Parameters(name = "Duration unit: {0}")
    public static Iterable<Object[]> abstractDurations() {
      return Arrays.asList(new Object[][] {
              { new Duration(1, Duration.Unit.RESTS), "Invalid duration unit specified: RESTS" },
              { new Duration(1, Duration.Unit.LONG_RESTS), "Invalid duration unit specified: LONG_RESTS" },
              { new Duration(1, Duration.Unit.FOREVER), "Invalid duration unit specified: FOREVER" }
      });
    }

    @Parameterized.Parameter
    public Duration abstractDuration;

    @Parameterized.Parameter(1)
    public String exceptionString;

    @Test
    public void conversionMethodsThrowWhenPassedAbstractDuration() {
      Exception ex = assertThrows(IllegalArgumentException.class, () -> Duration.toRounds(abstractDuration));
      assertThat(ex.getMessage(), equalTo(exceptionString));

      ex = assertThrows(IllegalArgumentException.class, () -> Duration.toMinutes(abstractDuration));
      assertThat(ex.getMessage(), equalTo(exceptionString));

      ex = assertThrows(IllegalArgumentException.class, () -> Duration.toHours(abstractDuration));
      assertThat(ex.getMessage(), equalTo(exceptionString));

      ex = assertThrows(IllegalArgumentException.class, () -> Duration.toDays(abstractDuration));
      assertThat(ex.getMessage(), equalTo(exceptionString));

      ex = assertThrows(IllegalArgumentException.class, () -> Duration.toWeeks(abstractDuration));
      assertThat(ex.getMessage(), equalTo(exceptionString));
    }
  }

  public static class NonParameterizedTests {
    public static List<Duration> durations = new ArrayList<>();
    public static int oneWeek = 0;
    public static int oneDay = 1;
    public static int oneHour = 2;
    public static int oneMinute = 3;
    public static int oneRound = 4;

    @BeforeClass
    public static void setUp() {
      durations.add(oneWeek, new Duration(1, Duration.Unit.WEEKS));
      durations.add(oneDay, new Duration(1, Duration.Unit.DAYS));
      durations.add(oneHour, new Duration(1, Duration.Unit.HOURS));
      durations.add(oneMinute, new Duration(1, Duration.Unit.MINUTES));
      durations.add(oneRound, new Duration(1, Duration.Unit.ROUNDS));
    }

    @Test
    public void toRoundsReturnsCorrectly() {
      assertThat(Duration.toRounds(durations.get(oneWeek)), equalTo(144000));
      assertThat(Duration.toRounds(durations.get(oneDay)), equalTo(14400));
      assertThat(Duration.toRounds(durations.get(oneHour)), equalTo(600));
      assertThat(Duration.toRounds(durations.get(oneMinute)), equalTo(10));
      assertThat(Duration.toRounds(durations.get(oneRound)), equalTo(1));
    }

    @Test
    public void toMinutesReturnsCorrectly() {
      assertThat(Duration.toMinutes(durations.get(oneWeek)), equalTo(14400));
      assertThat(Duration.toMinutes(durations.get(oneDay)), equalTo(1440));
      assertThat(Duration.toMinutes(durations.get(oneHour)), equalTo(60));
      assertThat(Duration.toMinutes(durations.get(oneMinute)), equalTo(1));
      assertThat(Duration.toMinutes(durations.get(oneRound)), equalTo(0));
    }

    @Test
    public void toHoursReturnsCorrectly() {
      assertThat(Duration.toHours(durations.get(oneWeek)), equalTo(240));
      assertThat(Duration.toHours(durations.get(oneDay)), equalTo(24));
      assertThat(Duration.toHours(durations.get(oneHour)), equalTo(1));
      assertThat(Duration.toHours(durations.get(oneMinute)), equalTo(0));
      assertThat(Duration.toHours(durations.get(oneRound)), equalTo(0));
    }

    @Test
    public void toDaysReturnsCorrectly() {
      assertThat(Duration.toDays(durations.get(oneWeek)), equalTo(10));
      assertThat(Duration.toDays(durations.get(oneDay)), equalTo(1));
      assertThat(Duration.toDays(durations.get(oneHour)), equalTo(0));
      assertThat(Duration.toDays(durations.get(oneMinute)), equalTo(0));
      assertThat(Duration.toDays(durations.get(oneRound)), equalTo(0));
    }

    @Test
    public void toWeeksReturnsCorrectly() {
      assertThat(Duration.toWeeks(durations.get(oneWeek)), equalTo(1));
      assertThat(Duration.toWeeks(durations.get(oneDay)), equalTo(0));
      assertThat(Duration.toWeeks(durations.get(oneHour)), equalTo(0));
      assertThat(Duration.toWeeks(durations.get(oneMinute)), equalTo(0));
      assertThat(Duration.toWeeks(durations.get(oneRound)), equalTo(0));
    }

    @Test
    public void isAbstractReturnsCorrectly() {
      assertFalse(Duration.isAbstract(durations.get(oneWeek)));
      assertFalse(Duration.isAbstract(durations.get(oneDay)));
      assertFalse(Duration.isAbstract(durations.get(oneHour)));
      assertFalse(Duration.isAbstract(durations.get(oneMinute)));
      assertFalse(Duration.isAbstract(durations.get(oneRound)));
      assertTrue(Duration.isAbstract(new Duration(1, Duration.Unit.RESTS)));
      assertTrue(Duration.isAbstract(new Duration(1, Duration.Unit.LONG_RESTS)));
      assertTrue(Duration.isAbstract(new Duration(1, Duration.Unit.FOREVER)));
    }
  }
}