package com.donalevans;

import static com.donalevans.InjuryType.*;
import static com.donalevans.InjuryTypeMaps.*;
import static com.donalevans.Util.firstCharToUppercase;

public class Injury {

  private Duration duration;
  private BodyPart bodyPart;
  private Severity severity;
  private String description;

  public enum DamageType {
    ACID,
    BLUDGEONING,
    COLD,
    FIRE,
    FORCE,
    LIGHTNING,
    NECROTIC,
    PIERCING,
    POISON,
    PSYCHIC,
    RADIANT,
    SLASHING,
    THUNDER;

    @Override
    public String toString() {
      return firstCharToUppercase(name().toLowerCase());
    }
  }

  public enum Direction {
    ABOVE,
    BELOW,
    FRONT_LEFT,
    FRONT_RIGHT,
    BEHIND_LEFT,
    BEHIND_RIGHT,
    NONE
  }

  public enum Severity {
    NONE,
    COSMETIC,
    IMPAIRED,
    DESTROYED
  }

  public Injury() {}

  public Injury(int severity, DamageType type, Direction direction) {
    bodyPart = calculateBodyPart(direction);
    this.severity = calculateSeverity(severity, type);
    duration = calculateDuration();
    description = generateInjuryType(severity, type).getDescriptionFormatted();
  }

  private BodyPart calculateBodyPart(Direction direction) {
    return null;
  }

  private Severity calculateSeverity(int spilloverPercent, DamageType type) {
    return null;
  }

  private Duration calculateDuration() {
    return null;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public BodyPart getBodyPart() {
    return bodyPart;
  }

  public void setBodyPart(BodyPart bodyPart) {
    this.bodyPart = bodyPart;
  }

  public Severity getSeverity() {
    return severity;
  }

  public void setSeverity(Severity severity) {
    this.severity = severity;
  }

  protected static InjuryType generateInjuryType(int severity, DamageType type) {
    switch (type) {
      case ACID:
        return acidInjuries.floorEntry(severity).getValue();
      case BLUDGEONING:
      case FORCE:
        return bludgeoningInjuries.floorEntry(severity).getValue();
      case COLD:
        return coldInjuries.floorEntry(severity).getValue();
      case FIRE:
        return fireInjuries.floorEntry(severity).getValue();
      case LIGHTNING:
        return lightningInjuries.floorEntry(severity).getValue();
      case NECROTIC:
        return necroticInjuries.floorEntry(severity).getValue();
      case PIERCING:
        return piercingInjuries.floorEntry(severity).getValue();
      case POISON:
        return poisonInjuries.floorEntry(severity).getValue();
      case PSYCHIC:
        return psychicInjuries.floorEntry(severity).getValue();
      case RADIANT:
        return radiantInjuries.floorEntry(severity).getValue();
      case SLASHING:
        return slashingInjuries.floorEntry(severity).getValue();
      case THUNDER:
        return thunderInjuries.floorEntry(severity).getValue();
      default:
        return INVALID_INJURY;
    }
  }

  public static class Dead extends Injury {
    @Override
    public String getDescription() {
      return DEAD.getDescriptionFormatted();
    }
  }
}
