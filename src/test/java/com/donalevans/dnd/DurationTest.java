package com.donalevans.dnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(Enclosed.class)
public class DurationTest {

  @RunWith(Parameterized.class)
  public static class ParameterizedTests {
    @Parameterized.Parameters(name = "Duration unit: {0}")
    public static Iterable<Object[]> abstractDurations() {
      return Arrays.asList(
          new Object[][] {
            {new Duration(1, Duration.Unit.RESTS),
                    "Invalid duration unit specified: RESTS"},
            {new Duration(1, Duration.Unit.LONG_RESTS),
              "Invalid duration unit specified: LONG_RESTS"},
            {new Duration(1, Duration.Unit.FOREVER),
                    "Invalid duration unit specified: FOREVER"}
          });
    }

    @Parameterized.Parameter public Duration abstractDuration;

    @Parameterized.Parameter(1)
    public String exceptionString;

    @Test
    public void conversionMethodsThrowWhenPassedAbstractDuration() {
      assertThatThrownBy(() -> Duration.toRounds(abstractDuration))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage(exceptionString);

      assertThatThrownBy(() -> Duration.toMinutes(abstractDuration))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage(exceptionString);

      assertThatThrownBy(() -> Duration.toHours(abstractDuration))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage(exceptionString);

      assertThatThrownBy(() -> Duration.toDays(abstractDuration))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage(exceptionString);

      assertThatThrownBy(() -> Duration.toWeeks(abstractDuration))
          .isInstanceOf(IllegalArgumentException.class)
          .hasMessage(exceptionString);
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
      assertThat(Duration.toRounds(durations.get(oneWeek))).isEqualTo(144000);
      assertThat(Duration.toRounds(durations.get(oneDay))).isEqualTo(14400);
      assertThat(Duration.toRounds(durations.get(oneHour))).isEqualTo(600);
      assertThat(Duration.toRounds(durations.get(oneMinute))).isEqualTo(10);
      assertThat(Duration.toRounds(durations.get(oneRound))).isEqualTo(1);
    }

    @Test
    public void toMinutesReturnsCorrectly() {
      assertThat(Duration.toMinutes(durations.get(oneWeek))).isEqualTo(14400);
      assertThat(Duration.toMinutes(durations.get(oneDay))).isEqualTo(1440);
      assertThat(Duration.toMinutes(durations.get(oneHour))).isEqualTo(60);
      assertThat(Duration.toMinutes(durations.get(oneMinute))).isEqualTo(1);
      assertThat(Duration.toMinutes(durations.get(oneRound))).isEqualTo(0);
    }

    @Test
    public void toHoursReturnsCorrectly() {
      assertThat(Duration.toHours(durations.get(oneWeek))).isEqualTo(240);
      assertThat(Duration.toHours(durations.get(oneDay))).isEqualTo(24);
      assertThat(Duration.toHours(durations.get(oneHour))).isEqualTo(1);
      assertThat(Duration.toHours(durations.get(oneMinute))).isEqualTo(0);
      assertThat(Duration.toHours(durations.get(oneRound))).isEqualTo(0);
    }

    @Test
    public void toDaysReturnsCorrectly() {
      assertThat(Duration.toDays(durations.get(oneWeek))).isEqualTo(10);
      assertThat(Duration.toDays(durations.get(oneDay))).isEqualTo(1);
      assertThat(Duration.toDays(durations.get(oneHour))).isEqualTo(0);
      assertThat(Duration.toDays(durations.get(oneMinute))).isEqualTo(0);
      assertThat(Duration.toDays(durations.get(oneRound))).isEqualTo(0);
    }

    @Test
    public void toWeeksReturnsCorrectly() {
      assertThat(Duration.toWeeks(durations.get(oneWeek))).isEqualTo(1);
      assertThat(Duration.toWeeks(durations.get(oneDay))).isEqualTo(0);
      assertThat(Duration.toWeeks(durations.get(oneHour))).isEqualTo(0);
      assertThat(Duration.toWeeks(durations.get(oneMinute))).isEqualTo(0);
      assertThat(Duration.toWeeks(durations.get(oneRound))).isEqualTo(0);
    }

    @Test
    public void isAbstractReturnsCorrectly() {
      assertThat(Duration.isAbstract(durations.get(oneWeek))).isFalse();
      assertThat(Duration.isAbstract(durations.get(oneDay))).isFalse();
      assertThat(Duration.isAbstract(durations.get(oneHour))).isFalse();
      assertThat(Duration.isAbstract(durations.get(oneMinute))).isFalse();
      assertThat(Duration.isAbstract(durations.get(oneRound))).isFalse();
      assertThat(Duration.isAbstract(new Duration(1, Duration.Unit.RESTS))).isTrue();
      assertThat(Duration.isAbstract(new Duration(1, Duration.Unit.LONG_RESTS))).isTrue();
      assertThat(Duration.isAbstract(new Duration(1, Duration.Unit.FOREVER))).isTrue();
    }
  }
}
