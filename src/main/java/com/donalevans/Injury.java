package com.donalevans;

import java.io.Serializable;

import static com.donalevans.InjuryType.*;

public class Injury implements Serializable {
  private static final long serialVersionUID = 7010783129193852701L;

  //  private Duration duration = new Duration(0, Duration.Unit.ROUNDS);
  //  private BodyPart bodyPart = new BodyPart();
  //  private Severity severity = Severity.NONE;
  private String description = InjuryType.UNHARMED.getDescription();

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
      return this.name().substring(0, 1) + this.name().substring(1).toLowerCase();
    }
  }

  public enum Direction {
    ABOVE,
    BELOW,
    FRONT_LEFT,
    FRONT_RIGHT,
    BEHIND_LEFT,
    BEHIND_RIGHT
  }

  public enum Severity {
    NONE,
    COSMETIC,
    IMPAIRED,
    DESTROYED
  }

  public Injury() {}

  public Injury(int severity, DamageType type) {
    //    this.bodyPart = calculateBodyPart();
    //    this.severity = calculateSeverity(severity, type);
    //    this.duration = calculateDuration();
    //    this.description = generateDescription();
    this.description = generateDescription(severity, type).getDescriptionFormatted();
  }

  public Injury(int severity, DamageType type, Direction direction) {
    //    this.bodyPart = calculateBodyPart(direction);
    //    this.severity = calculateSeverity(severity, type);
    //    this.duration = calculateDuration();
    //    this.description = generateDescription();
  }

  private BodyPart calculateBodyPart() {
    return null;
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

  private String generateDescription() {
    return null;
  }

  private InjuryType generateDescription(int severity, DamageType type) {
    if (severity <= 0) {
      return InjuryType.UNHARMED;
    }
    switch (type) {
      case ACID:
        return acidInjury(severity);
      case BLUDGEONING:
      case FORCE:
        return bludgeoningInjury(severity);
      case COLD:
        return coldInjury(severity);
      case FIRE:
        return fireInjury(severity);
      case LIGHTNING:
        return lightningInjury(severity);
      case NECROTIC:
        return necroticInjury(severity);
      case PIERCING:
        return piercingInjury(severity);
      case POISON:
        return poisonInjury(severity);
      case PSYCHIC:
        return psychicInjury(severity);
      case RADIANT:
        return radiantInjury(severity);
      case SLASHING:
        return slashingInjury(severity);
      case THUNDER:
        return thunderInjury(severity);
      default:
        return InjuryType.UNHARMED;
    }
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  //  public Duration getDuration() {
  //    return duration;
  //  }

  //  public void setDuration(Duration duration) {
  //    this.duration = duration;
  //  }

  //  public BodyPart getBodyPart() {
  //    return bodyPart;
  //  }

  //  public void setBodyPart(BodyPart bodyPart) {
  //    this.bodyPart = bodyPart;
  //  }

  //  public Severity getSeverity() {
  //    return severity;
  //  }

  //  public void setSeverity(Severity severity) {
  //    this.severity = severity;
  //  }

  private InjuryType acidInjury(int severity) {
    if (severity >= 95) {
      return BLINDNESS;
    } else if (severity >= 90) {
      return PARTIAL_BLINDNESS;
    } else if (severity >= 85) {
      return DESTROYED_HAND;
    } else if (severity >= 80) {
      return DESTROYED_FOOT_OR_LEG;
    } else if (severity >= 65) {
      return MAJOR_NEURALGIA;
    } else if (severity >= 50) {
      return MINOR_NEURALGIA;
    } else if (severity >= 35) {
      return HORRIBLE_DISFIGUREMENT;
    } else if (severity >= 20) {
      return BLISTERS;
    } else if (severity > 0) {
      return MINOR_DISFIGUREMENT;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType bludgeoningInjury(int severity) {
    if (severity >= 95) {
      return BRAIN_INJURY;
    } else if (severity >= 90) {
      return BROKEN_LEG;
    } else if (severity >= 85) {
      return BROKEN_ARM;
    } else if (severity >= 80) {
      return INTERNAL_INJURY;
    } else if (severity >= 65) {
      return BROKEN_RIBS;
    } else if (severity >= 50) {
      return MAJOR_CONCUSSION;
    } else if (severity >= 35) {
      return MINOR_CONCUSSION;
    } else if (severity >= 20) {
      return SEVERE_BRUISING;
    } else if (severity > 0) {
      return BROKEN_NOSE;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType coldInjury(int severity) {
    if (severity >= 95) {
      return PARTIAL_BLINDNESS;
    } else if (severity >= 90) {
      return SYSTEMIC_DAMAGE;
    } else if (severity >= 85) {
      return DESTROYED_HAND;
    } else if (severity >= 80) {
      return DESTROYED_FOOT_OR_LEG;
    } else if (severity >= 65) {
      return MAJOR_NEURALGIA;
    } else if (severity >= 50) {
      return FROSTBITTEN_FOOT;
    } else if (severity >= 35) {
      return FROSTBITTEN_HAND;
    } else if (severity >= 20) {
      return MINOR_NEURALGIA;
    } else if (severity > 0) {
      return ANOSMIA;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType fireInjury(int severity) {
    if (severity >= 95) {
      return PARTIAL_BLINDNESS;
    } else if (severity >= 90) {
      return FOURTH_DEGREE_BURNS;
    } else if (severity >= 85) {
      return THIRD_DEGREE_BURNS;
    } else if (severity >= 80) {
      return SECOND_DEGREE_BURNS;
    } else if (severity >= 65) {
      return MAJOR_NEURALGIA;
    } else if (severity >= 50) {
      return MINOR_NEURALGIA;
    } else if (severity >= 35) {
      return HORRIBLE_DISFIGUREMENT;
    } else if (severity >= 20) {
      return BLISTERS;
    } else if (severity > 0) {
      return FIRST_DEGREE_BURNS;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType lightningInjury(int severity) {
    if (severity >= 95) {
      return BRAIN_INJURY;
    } else if (severity >= 90) {
      return DESTROYED_HAND;
    } else if (severity >= 85) {
      return DESTROYED_FOOT_OR_LEG;
    } else if (severity >= 80) {
      return KIDNEY_FAILURE;
    } else if (severity >= 65) {
      return MAJOR_NEURALGIA;
    } else if (severity >= 50) {
      return CARDIAC_INJURY;
    } else if (severity >= 35) {
      return MUSCULAR_BREAKDOWN;
    } else if (severity >= 20) {
      return MUSCLE_SPASMS;
    } else if (severity > 0) {
      return FLASH_BURNS;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType necroticInjury(int severity) {
    if (severity >= 95) {
      return SPIRITUAL_INJURY;
    } else if (severity >= 90) {
      return WITHERED_HAND;
    } else if (severity >= 85) {
      return WITHERED_FOOT;
    } else if (severity >= 80) {
      return MAJOR_ORGAN_NECROSIS;
    } else if (severity >= 65) {
      return MINOR_ORGAN_NECROSIS;
    } else if (severity >= 50) {
      return NECROTIC_STENCH;
    } else if (severity >= 35) {
      return FESTERING_WOUND;
    } else if (severity >= 20) {
      return INFLAMMATION;
    } else if (severity > 0) {
      return NECROTIC_DISCOLOURATION;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType piercingInjury(int severity) {
    if (severity >= 95) {
      return PARTIAL_BLINDNESS;
    } else if (severity >= 90) {
      return THROAT_INJURY;
    } else if (severity >= 85) {
      return GROIN_INJURY;
    } else if (severity >= 80) {
      return CARDIAC_INJURY;
    } else if (severity >= 65) {
      return ORGAN_DAMAGE;
    } else if (severity >= 50) {
      return PIERCED_STOMACH;
    } else if (severity >= 35) {
      return HORRIBLE_DISFIGUREMENT;
    } else if (severity >= 20) {
      return FESTERING_WOUND;
    } else if (severity > 0) {
      return MINOR_DISFIGUREMENT;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType poisonInjury(int severity) {
    if (severity >= 95) {
      return SYSTEMIC_DAMAGE;
    } else if (severity >= 90) {
      return MAJOR_LIVER_DAMAGE;
    } else if (severity >= 85) {
      return MINOR_LIVER_DAMAGE;
    } else if (severity >= 80) {
      return KIDNEY_FAILURE;
    } else if (severity >= 65) {
      return MINOR_KIDNEY_DAMAGE;
    } else if (severity >= 50) {
      return CARDIAC_INJURY;
    } else if (severity >= 35) {
      return VERTIGO;
    } else if (severity >= 20) {
      return NAUSEA;
    } else if (severity > 0) {
      return MINOR_NAUSEA;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType psychicInjury(int severity) {
    if (severity >= 95) {
      return BRAIN_INJURY;
    } else if (severity >= 90) {
      return INDEFINITE_MADNESS;
    } else if (severity >= 85) {
      return SEVERE_HEADACHES;
    } else if (severity >= 80) {
      return PHOBIA;
    } else if (severity >= 65) {
      return LONG_TERM_MADNESS;
    } else if (severity >= 50) {
      return WEAK_PERSONA;
    } else if (severity >= 35) {
      return MINOR_HEADACHES;
    } else if (severity >= 20) {
      return INAPPROPRIATE_VOLUME;
    } else if (severity > 0) {
      return SHORT_TERM_MADNESS;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType radiantInjury(int severity) {
    if (severity >= 95) {
      return BLINDNESS;
    } else if (severity >= 90) {
      return PARTIAL_BLINDNESS;
    } else if (severity >= 85) {
      return THIRD_DEGREE_BURNS;
    } else if (severity >= 80) {
      return SECOND_DEGREE_BURNS;
    } else if (severity >= 65) {
      return LARGE_SKIN_TUMOURS;
    } else if (severity >= 50) {
      return SMALL_SKIN_TUMOURS;
    } else if (severity >= 35) {
      return BLISTERS;
    } else if (severity >= 20) {
      return FIRST_DEGREE_BURNS;
    } else if (severity > 0) {
      return HAIR_LOSS_AND_COSMETIC_DAMAGE;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType slashingInjury(int severity) {
    if (severity >= 95) {
      return PARTIAL_BLINDNESS;
    } else if (severity >= 90) {
      return DESTROYED_HAND;
    } else if (severity >= 85) {
      return DESTROYED_FOOT_OR_LEG;
    } else if (severity >= 80) {
      return HAMSTRUNG;
    } else if (severity >= 65) {
      return INTERNAL_INJURY;
    } else if (severity >= 50) {
      return MINOR_INTERNAL_INJURY;
    } else if (severity >= 35) {
      return HORRIBLE_DISFIGUREMENT;
    } else if (severity >= 20) {
      return FESTERING_WOUND;
    } else if (severity > 0) {
      return MINOR_DISFIGUREMENT;
    } else {
      // Shouldn't be possible
      return null;
    }
  }

  private InjuryType thunderInjury(int severity) {
    if (severity >= 95) {
      return BRAIN_INJURY;
    } else if (severity >= 90) {
      return DEAFNESS;
    } else if (severity >= 85) {
      return PARTIAL_DEAFNESS;
    } else if (severity >= 80) {
      return SEVERE_HEADACHES;
    } else if (severity >= 65) {
      return INTERNAL_INJURY;
    } else if (severity >= 50) {
      return MAJOR_CONCUSSION;
    } else if (severity >= 35) {
      return MINOR_CONCUSSION;
    } else if (severity >= 20) {
      return MINOR_HEADACHES;
    } else if (severity > 0) {
      return SEVERE_BRUISING;
    } else {
      // Shouldn't be possible
      return null;
    }
  }
}
