package com.donalevans.dnd;

import com.donalevans.dnd.constants.InjuryType;
import java.io.Serializable;
import java.util.Objects;

import static com.donalevans.dnd.constants.InjuryType.*;
import static com.donalevans.dnd.constants.InjuryTypeMaps.*;
import static com.donalevans.dnd.Util.firstCharToUppercase;

public class Injury implements Serializable {

  private static final long serialVersionUID = -1203363020670015231L;
  private BodyPart bodyPart;
  private Duration duration;
  private InjuryType injuryType;
  private Severity severity;

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

  public Injury(InjuryType injuryType) {
    this.injuryType = injuryType;
  }

  public Injury(int severity, DamageType type, Direction direction) {
    bodyPart = calculateBodyPart(direction);
    duration = calculateDuration();
    injuryType = generateInjuryType(severity, type);
    this.severity = calculateSeverity(severity, type);
  }

  private BodyPart calculateBodyPart(Direction direction) {
    return new BodyPart(direction);
  }

  //TODO: Implement this properly
  private Severity calculateSeverity(int spilloverPercent, DamageType type) {
    return Severity.NONE;
  }

  //TODO: Implement this properly
  private Duration calculateDuration() {
    return new Duration(1, Duration.Unit.FOREVER);
  }

  public String getDescription() {
    return injuryType.getDescriptionFormatted();
  }

  public BodyPart getBodyPart() {
    return bodyPart;
  }

  public void setBodyPart(BodyPart bodyPart) {
    this.bodyPart = bodyPart;
  }

  public Duration getDuration() {
    return duration;
  }

  public void setDuration(Duration duration) {
    this.duration = duration;
  }

  public InjuryType getInjuryType() {
    return injuryType;
  }

  public void setInjuryType(InjuryType injuryType) {
    this.injuryType = injuryType;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Injury injury = (Injury) o;
    return Objects.equals(bodyPart, injury.bodyPart) && Objects.equals(duration, injury.duration) && Objects.equals(injuryType, injury.injuryType) && severity == injury.severity;
  }

  @Override
  public int hashCode() {
    return Objects.hash(bodyPart, duration, injuryType, severity);
  }

  @Override
  public String toString() {
    return injuryType.getNameFormatted();
  }

  public static class Dead extends Injury {
    private static final long serialVersionUID = 557558176305102151L;

    @Override
    public String getDescription() {
      return DEAD.getDescriptionFormatted();
    }

    @Override
    public String toString() {
      return getDescription();
    }
  }
}
