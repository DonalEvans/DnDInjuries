package com.donalevans;

import static com.donalevans.InjuryDescriptionStrings.ANOSMIA_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BLINDNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BLISTERS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BRAIN_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BROKEN_ARM_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BROKEN_LEG_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BROKEN_NOSE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.BROKEN_RIBS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.CARDIAC_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.DEAD_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.DEAFNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.DESTROYED_FOOT_OR_LEG_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.DESTROYED_HAND_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.FESTERING_WOUND_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.FIRST_DEGREE_BURNS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.FLASH_BURNS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.FOURTH_DEGREE_BURNS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.FROSTBITTEN_FOOT_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.FROSTBITTEN_HAND_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.GROIN_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.HAIR_LOSS_AND_COSMETIC_DAMAGE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.HAMSTRUNG_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.HORRIBLE_DISFIGUREMENT_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.INAPPROPRIATE_VOLUME_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.INDEFINITE_MADNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.INFLAMMATION_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.INTERNAL_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.INVALID_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.KIDNEY_FAILURE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.LARGE_SKIN_TUMOURS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.LONG_TERM_MADNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MAJOR_CONCUSSION_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MAJOR_LIVER_DAMAGE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MAJOR_NEURALGIA_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MAJOR_ORGAN_NECROSIS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_CONCUSSION_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_DISFIGUREMENT_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_HEADACHES_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_INTERNAL_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_KIDNEY_DAMAGE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_LIVER_DAMAGE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_NAUSEA_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_NEURALGIA_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MINOR_ORGAN_NECROSIS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MUSCLE_SPASMS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.MUSCULAR_BREAKDOWN_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.NAUSEA_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.NECROTIC_DISCOLOURATION_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.NECROTIC_STENCH_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.ORGAN_DAMAGE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.PARTIAL_BLINDNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.PARTIAL_DEAFNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.PHOBIA_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.PIERCED_STOMACH_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SECOND_DEGREE_BURNS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SEVERE_BRUISING_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SEVERE_HEADACHES_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SHORT_TERM_MADNESS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SMALL_SKIN_TUMOURS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SPIRITUAL_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.SYSTEMIC_DAMAGE_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.THIRD_DEGREE_BURNS_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.THROAT_INJURY_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.UNHARMED_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.VERTIGO_DESCRIPTION;
import static com.donalevans.InjuryDescriptionStrings.WEAK_PERSONA_DESCRIPTION;
import static com.donalevans.Util.formatName;

public enum InjuryType {
  ANOSMIA(ANOSMIA_DESCRIPTION),
  BLINDNESS(BLINDNESS_DESCRIPTION),
  BLISTERS(BLISTERS_DESCRIPTION),
  BRAIN_INJURY(BRAIN_INJURY_DESCRIPTION),
  BROKEN_ARM(BROKEN_ARM_DESCRIPTION),
  BROKEN_LEG(BROKEN_LEG_DESCRIPTION),
  BROKEN_NOSE(BROKEN_NOSE_DESCRIPTION),
  BROKEN_RIBS(BROKEN_RIBS_DESCRIPTION),
  CARDIAC_INJURY(CARDIAC_INJURY_DESCRIPTION),
  DEAFNESS(DEAFNESS_DESCRIPTION),
  DESTROYED_FOOT_OR_LEG(DESTROYED_FOOT_OR_LEG_DESCRIPTION),
  DESTROYED_HAND(DESTROYED_HAND_DESCRIPTION),
  FIRST_DEGREE_BURNS(FIRST_DEGREE_BURNS_DESCRIPTION),
  FLASH_BURNS(FLASH_BURNS_DESCRIPTION),
  FOURTH_DEGREE_BURNS(FOURTH_DEGREE_BURNS_DESCRIPTION),
  FROSTBITTEN_FOOT(FROSTBITTEN_FOOT_DESCRIPTION),
  FROSTBITTEN_HAND(FROSTBITTEN_HAND_DESCRIPTION),
  GROIN_INJURY(GROIN_INJURY_DESCRIPTION),
  HAIR_LOSS_AND_COSMETIC_DAMAGE(HAIR_LOSS_AND_COSMETIC_DAMAGE_DESCRIPTION),
  HAMSTRUNG(HAMSTRUNG_DESCRIPTION),
  HORRIBLE_DISFIGUREMENT(HORRIBLE_DISFIGUREMENT_DESCRIPTION),
  INAPPROPRIATE_VOLUME(INAPPROPRIATE_VOLUME_DESCRIPTION),
  INDEFINITE_MADNESS(INDEFINITE_MADNESS_DESCRIPTION),
  INFLAMMATION(INFLAMMATION_DESCRIPTION),
  INTERNAL_INJURY(INTERNAL_INJURY_DESCRIPTION),
  KIDNEY_FAILURE(KIDNEY_FAILURE_DESCRIPTION),
  LARGE_SKIN_TUMOURS(LARGE_SKIN_TUMOURS_DESCRIPTION),
  LONG_TERM_MADNESS(LONG_TERM_MADNESS_DESCRIPTION),
  MAJOR_CONCUSSION(MAJOR_CONCUSSION_DESCRIPTION),
  MAJOR_LIVER_DAMAGE(MAJOR_LIVER_DAMAGE_DESCRIPTION),
  MAJOR_NEURALGIA(MAJOR_NEURALGIA_DESCRIPTION),
  MAJOR_ORGAN_NECROSIS(MAJOR_ORGAN_NECROSIS_DESCRIPTION),
  MINOR_CONCUSSION(MINOR_CONCUSSION_DESCRIPTION),
  MINOR_KIDNEY_DAMAGE(MINOR_KIDNEY_DAMAGE_DESCRIPTION),
  MINOR_DISFIGUREMENT(MINOR_DISFIGUREMENT_DESCRIPTION),
  MINOR_HEADACHES(MINOR_HEADACHES_DESCRIPTION),
  MINOR_INTERNAL_INJURY(MINOR_INTERNAL_INJURY_DESCRIPTION),
  MINOR_LIVER_DAMAGE(MINOR_LIVER_DAMAGE_DESCRIPTION),
  MINOR_NAUSEA(MINOR_NAUSEA_DESCRIPTION),
  MINOR_NEURALGIA(MINOR_NEURALGIA_DESCRIPTION),
  MINOR_ORGAN_NECROSIS(MINOR_ORGAN_NECROSIS_DESCRIPTION),
  MUSCLE_SPASMS(MUSCLE_SPASMS_DESCRIPTION),
  MUSCULAR_BREAKDOWN(MUSCULAR_BREAKDOWN_DESCRIPTION),
  NAUSEA(NAUSEA_DESCRIPTION),
  NECROTIC_STENCH(NECROTIC_STENCH_DESCRIPTION),
  NECROTIC_DISCOLOURATION(NECROTIC_DISCOLOURATION_DESCRIPTION),
  FESTERING_WOUND(FESTERING_WOUND_DESCRIPTION),
  ORGAN_DAMAGE(ORGAN_DAMAGE_DESCRIPTION),
  PARTIAL_BLINDNESS(PARTIAL_BLINDNESS_DESCRIPTION),
  PARTIAL_DEAFNESS(PARTIAL_DEAFNESS_DESCRIPTION),
  PHOBIA(PHOBIA_DESCRIPTION),
  PIERCED_STOMACH(PIERCED_STOMACH_DESCRIPTION),
  SECOND_DEGREE_BURNS(SECOND_DEGREE_BURNS_DESCRIPTION),
  SEVERE_BRUISING(SEVERE_BRUISING_DESCRIPTION),
  SEVERE_HEADACHES(SEVERE_HEADACHES_DESCRIPTION),
  SHORT_TERM_MADNESS(SHORT_TERM_MADNESS_DESCRIPTION),
  SMALL_SKIN_TUMOURS(SMALL_SKIN_TUMOURS_DESCRIPTION),
  SPIRITUAL_INJURY(SPIRITUAL_INJURY_DESCRIPTION),
  SYSTEMIC_DAMAGE(SYSTEMIC_DAMAGE_DESCRIPTION),
  THIRD_DEGREE_BURNS(THIRD_DEGREE_BURNS_DESCRIPTION),
  THROAT_INJURY(THROAT_INJURY_DESCRIPTION),
  VERTIGO(VERTIGO_DESCRIPTION),
  WEAK_PERSONA(WEAK_PERSONA_DESCRIPTION),
  WITHERED_HAND(DESTROYED_HAND_DESCRIPTION),
  WITHERED_FOOT(DESTROYED_FOOT_OR_LEG_DESCRIPTION),
  UNHARMED(UNHARMED_DESCRIPTION),
  DEAD(DEAD_DESCRIPTION),
  INVALID_INJURY(INVALID_INJURY_DESCRIPTION)
  ;

  private final String description;

  InjuryType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getDescriptionFormatted() {
    return formatName(name()) + ": " + description;
  }

  @Override
  public String toString() {
    return formatName(name());
  }
}


