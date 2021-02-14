package com.donalevans;

import java.io.Serializable;
import java.util.Objects;

import static com.donalevans.Util.formatName;

public class Duration implements Serializable {
  private static final long serialVersionUID = -5442231928040353032L;
  public static int ROUNDS_PER_MINUTE = 10;
  public static int MINUTES_PER_HOUR = 60;
  public static int HOURS_PER_DAY = 24;
  public static int DAYS_PER_WEEK = 10;

  public enum Unit {
    ROUNDS, MINUTES, HOURS, RESTS, LONG_RESTS, DAYS, WEEKS, FOREVER
  }

  private int initialDuration;
  private Unit units;

  public Duration() {}

  public Duration(int initialDuration, Unit units) {
    this.initialDuration = initialDuration;
    this.units = units;
  }

  public int getInitialDuration() {
    return initialDuration;
  }

  public void setInitialDuration(int initialDuration) {
    this.initialDuration = initialDuration;
  }

  public Unit getUnits() {
    return units;
  }

  public void setUnits(Unit units) {
    this.units = units;
  }

  @Override
  public String toString() {
    if (units.equals(Unit.FOREVER)) {
      return formatName(units.name());
    }
    return initialDuration + " " + formatName(units.name());
  }

  public static int toRounds(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.initialDuration;
      case MINUTES:
        return input.initialDuration * ROUNDS_PER_MINUTE;
      case HOURS:
        return input.initialDuration * ROUNDS_PER_MINUTE * MINUTES_PER_HOUR;
      case DAYS:
        return input.initialDuration * ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY;
      case WEEKS:
        return input.initialDuration * ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toMinutes(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.initialDuration / ROUNDS_PER_MINUTE;
      case MINUTES:
        return input.initialDuration;
      case HOURS:
        return input.initialDuration * MINUTES_PER_HOUR;
      case DAYS:
        return input.initialDuration * MINUTES_PER_HOUR * HOURS_PER_DAY;
      case WEEKS:
        return input.initialDuration * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toHours(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.initialDuration / (ROUNDS_PER_MINUTE * MINUTES_PER_HOUR);
      case MINUTES:
        return input.initialDuration / MINUTES_PER_HOUR;
      case HOURS:
        return input.initialDuration;
      case DAYS:
        return input.initialDuration * HOURS_PER_DAY;
      case WEEKS:
        return input.initialDuration * HOURS_PER_DAY * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toDays(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.initialDuration / (ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY) ;
      case MINUTES:
        return input.initialDuration / (MINUTES_PER_HOUR * HOURS_PER_DAY);
      case HOURS:
        return input.initialDuration / HOURS_PER_DAY;
      case DAYS:
        return input.initialDuration;
      case WEEKS:
        return input.initialDuration * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toWeeks(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.initialDuration / (ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK);
      case MINUTES:
        return input.initialDuration / (MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK);
      case HOURS:
        return input.initialDuration / (HOURS_PER_DAY * DAYS_PER_WEEK);
      case DAYS:
        return input.initialDuration / DAYS_PER_WEEK;
      case WEEKS:
        return input.initialDuration;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static boolean isAbstract(Duration input) {
    switch (input.units) {
      case RESTS:
      case LONG_RESTS:
      case FOREVER:
        return true;
      default:
        return false;
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Duration duration = (Duration) o;
    return initialDuration == duration.initialDuration && units == duration.units;
  }

  @Override
  public int hashCode() {
    return Objects.hash(initialDuration, units);
  }
}
