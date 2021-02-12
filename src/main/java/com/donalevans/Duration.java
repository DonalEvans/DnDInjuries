package com.donalevans;

public class Duration {
  public static int ROUNDS_PER_HOUR = 10 * 60;
  public static int HOURS_PER_DAY = 24;
  public static int DAYS_PER_WEEK = 10;

  public enum Unit {
    ROUNDS, HOURS, RESTS, LONG_RESTS, DAYS, WEEKS, FOREVER;
  }

  private int duration;
  private Unit units;

  public Duration(int duration, Unit units) {
    this.duration = duration;
    this.units = units;
  }

  public int getDuration() {
    return duration;
  }

  public void setDuration(int duration) {
    this.duration = duration;
  }

  public Unit getUnits() {
    return units;
  }

  public void setUnits(Unit units) {
    this.units = units;
  }

  public static int toRounds(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.duration;
      case HOURS:
        return input.duration / ROUNDS_PER_HOUR;
      case DAYS:
        return input.duration / (ROUNDS_PER_HOUR * HOURS_PER_DAY);
      case WEEKS:
        return input.duration / (ROUNDS_PER_HOUR * HOURS_PER_DAY * DAYS_PER_WEEK);
      default:
        return -1;
    }
  }

  public static int toHours(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.duration * ROUNDS_PER_HOUR;
      case HOURS:
        return input.duration;
      case DAYS:
        return input.duration / HOURS_PER_DAY;
      case WEEKS:
        return input.duration / (HOURS_PER_DAY * DAYS_PER_WEEK);
      default:
        return -1;
    }
  }

  public static int toDays(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.duration * HOURS_PER_DAY * ROUNDS_PER_HOUR;
      case HOURS:
        return input.duration * HOURS_PER_DAY;
      case DAYS:
        return input.duration;
      case WEEKS:
        return input.duration / DAYS_PER_WEEK;
      default:
        return -1;
    }
  }

  public static int toWeeks(Duration input) {
    switch (input.units) {
      case ROUNDS:
        return input.duration * DAYS_PER_WEEK * HOURS_PER_DAY * ROUNDS_PER_HOUR;
      case HOURS:
        return input.duration * DAYS_PER_WEEK * HOURS_PER_DAY;
      case DAYS:
        return input.duration * DAYS_PER_WEEK;
      case WEEKS:
        return input.duration;
      default:
        return -1;
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
