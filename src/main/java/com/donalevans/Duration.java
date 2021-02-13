package com.donalevans;

import static com.donalevans.Util.formatName;

public class Duration {
  public static int ROUNDS_PER_MINUTE = 10;
  public static int MINUTES_PER_HOUR = 60;
  public static int HOURS_PER_DAY = 24;
  public static int DAYS_PER_WEEK = 10;

  public enum Unit {
    ROUNDS, MINUTES, HOURS, RESTS, LONG_RESTS, DAYS, WEEKS, FOREVER
  }

  private int initialDuration;
  private int remainingDuration;
  private Unit units;

  public Duration(int initialDuration, Unit units) {
    this.initialDuration = initialDuration;
    this.remainingDuration = initialDuration;
    this.units = units;
  }

  public int getInitialDuration() {
    return initialDuration;
  }

  public void setInitialDuration(int initialDuration) {
    this.initialDuration = initialDuration;
  }

  public int getRemainingDuration() {
    return remainingDuration;
  }

  public void setRemainingDuration(int remainingDuration) {
    this.remainingDuration = remainingDuration;
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
    return remainingDuration + " " + formatName(units.name());
  }

  public static int toRounds(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.remainingDuration;
      case MINUTES:
        return input.remainingDuration * ROUNDS_PER_MINUTE;
      case HOURS:
        return input.remainingDuration * ROUNDS_PER_MINUTE * MINUTES_PER_HOUR;
      case DAYS:
        return input.remainingDuration * ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY;
      case WEEKS:
        return input.remainingDuration * ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toMinutes(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.remainingDuration / ROUNDS_PER_MINUTE;
      case MINUTES:
        return input.remainingDuration;
      case HOURS:
        return input.remainingDuration * MINUTES_PER_HOUR;
      case DAYS:
        return input.remainingDuration * MINUTES_PER_HOUR * HOURS_PER_DAY;
      case WEEKS:
        return input.remainingDuration * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toHours(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.remainingDuration / (ROUNDS_PER_MINUTE * MINUTES_PER_HOUR);
      case MINUTES:
        return input.remainingDuration / MINUTES_PER_HOUR;
      case HOURS:
        return input.remainingDuration;
      case DAYS:
        return input.remainingDuration * HOURS_PER_DAY;
      case WEEKS:
        return input.remainingDuration * HOURS_PER_DAY * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toDays(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.remainingDuration / (ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY) ;
      case MINUTES:
        return input.remainingDuration / (MINUTES_PER_HOUR * HOURS_PER_DAY);
      case HOURS:
        return input.remainingDuration / HOURS_PER_DAY;
      case DAYS:
        return input.remainingDuration;
      case WEEKS:
        return input.remainingDuration * DAYS_PER_WEEK;
      default:
       throw new IllegalArgumentException("Invalid duration unit specified: " + input.units.name());
    }
  }

  public static int toWeeks(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.remainingDuration / (ROUNDS_PER_MINUTE * MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK);
      case MINUTES:
        return input.remainingDuration / (MINUTES_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK);
      case HOURS:
        return input.remainingDuration / (HOURS_PER_DAY * DAYS_PER_WEEK);
      case DAYS:
        return input.remainingDuration / DAYS_PER_WEEK;
      case WEEKS:
        return input.remainingDuration;
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
}
