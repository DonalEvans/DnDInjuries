package com.donalevans;

import static com.donalevans.InjuryEffectStrings.*;
import static com.donalevans.InjuryFlavourStrings.*;
import static com.donalevans.InjuryRecoveryStrings.*;

public class InjuryDescriptionStrings {
  public static final String ANOSMIA_DESCRIPTION = String.join(SPACE,LOSE_SMELL_TASTE, ANOSMIA_EFFECT, ANY_MAGIC_HEALING);

  public static final String BLINDNESS_DESCRIPTION = String.join(SPACE, destroyedBodyPart("eyes"), gainCondition("blinded"), REGENERATE);

  public static final String BLISTERS_DESCRIPTION = String.join(SPACE, BLISTERS, YOU_HAVE, disadvantageOnChecks("Dexterity"), ANY_MAGIC_HEALING, String.format(MEDICINE_CHECK_DC_FREQUENCY_SUCCESSES, "15", "24 hours", "seven"));

  public static final String BRAIN_INJURY_DESCRIPTION = String.join(SPACE, BRAIN_INJURY, YOU_HAVE, disadvantageOnCheckAndSaves("Intelligence, Wisdom and Charisma"), stunnedOnDamage("bludgeoning, force or psychic"), REGENERATE);

  public static final String BROKEN_ARM_DESCRIPTION = String.join(SPACE,brokenBodyPart("arm"), BROKEN_ARM_EFFECT, SIXTH_LEVEL_HEALING, healsNaturallyIn("8 weeks"), EFFECT_REMAINS_IF_NOT_SPLINTED);

  public static final String BROKEN_LEG_DESCRIPTION = String.join(SPACE, brokenBodyPart("leg"), BROKEN_LEG_EFFECT, SIXTH_LEVEL_HEALING, healsNaturallyIn("8 weeks"), EFFECT_REMAINS_IF_NOT_SPLINTED);

  public static final String BROKEN_NOSE_DESCRIPTION = String.join(SPACE, brokenBodyPart("nose"), NO_EFFECT, ANY_MAGIC_HEALING, MAY_HEAL_CROOKED);

  public static final String BROKEN_RIBS_DESCRIPTION = String.join(SPACE, loseAction("10"), SIXTH_LEVEL_HEALING, healsNaturallyIn("ten days"));

  public static final String CARDIAC_INJURY_DESCRIPTION = String.join(SPACE, EXHAUSTION_EFFECT, gainExhaustionIf("you fail a saving throw against fear or fear effects"), REGENERATE);

  public static final String DEAFNESS_DESCRIPTION = String.join(SPACE, destroyedBodyPart("eardrums"), gainCondition("deafened"), REGENERATE);

  public static final String DESTROYED_FOOT_OR_LEG_DESCRIPTION = String.join(SPACE, BROKEN_LEG_EFFECT, REGENERATE);

  public static final String DESTROYED_HAND_DESCRIPTION = String.join(SPACE, BROKEN_ARM_EFFECT, REGENERATE);

  public static final String FIRST_DEGREE_BURNS_DESCRIPTION = String.join(SPACE, SUPERFICIAL_PAINFUL_BURNS, additionalDamage("fire", "1"), ifThenYou("you already have first degree burns", "suffer second degree burns"), ANY_MAGIC_HEALING, healsNaturallyIn("2 weeks"));

  public static final String FLASH_BURNS_DESCRIPTION = String.join(SPACE, SUPERFICIAL_BURNS, NO_EFFECT, ANY_MAGIC_HEALING, healsNaturallyIn("2 weeks"));

  public static final String FOURTH_DEGREE_BURNS_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnCheckAndSaves("all", "Strength, Dexterity, and Constitution"), stunnedOnDamage("fire"), ifThenYou("you already have fourth degree burns", "must succeed at a DC 15 Constitution saving throw or die"), REGENERATE);

  public static final String FROSTBITTEN_FOOT_DESCRIPTION = String.join(SPACE, DAMAGED_FOOT_EFFECT, ANY_MAGIC_HEALING, healsWithCheckAndRest("15", "2 weeks"));

  public static final String FROSTBITTEN_HAND_DESCRIPTION = String.join(SPACE, DAMAGED_HAND_EFFECT, ANY_MAGIC_HEALING, healsWithCheckAndRest("15", "2 weeks"));

  public static final String GROIN_INJURY_DESCRIPTION = String.join(SPACE, GROIN_INJURY_EFFECT, REGENERATE);

  public static final String HAIR_LOSS_AND_COSMETIC_DAMAGE_DESCRIPTION = HAIR_LOSS_AND_COSMETIC_DAMAGE_EFFECT;

  public static final String HAMSTRUNG_DESCRIPTION = String.join(SPACE, DAMAGED_FOOT_EFFECT, REGENERATE);

  public static final String HORRIBLE_DISFIGUREMENT_DESCRIPTION = String.join(SPACE, BADLY_SCARRED, HORRIBLE_DISFIGUREMENT_EFFECT, SIXTH_LEVEL_HEALING);

  public static final String INAPPROPRIATE_VOLUME_DESCRIPTION = String.join(SPACE, INAPPROPRIATE_VOLUME_EFFECT, ANY_MAGIC_HEALING);

  public static final String INDEFINITE_MADNESS_DESCRIPTION = INDEFINITE_MADNESS_EFFECT;

  public static final String INFLAMMATION_DESCRIPTION = String.join(SPACE, MUSCLE_INFLAMMATION, YOU_HAVE, disadvantageOnChecks("Strength"), ANY_MAGIC_HEALING, healsNaturallyIn("two weeks"));

  public static final String INTERNAL_INJURY_DESCRIPTION = String.join(SPACE, loseAction("15"), SIXTH_LEVEL_HEALING, healsNaturallyIn("ten days"));

  public static final String KIDNEY_FAILURE_DESCRIPTION = String.join(SPACE, poisonedOnLongRest("15"), REGENERATE, healsWithChecks("15", "week", "ten"));

  public static final String LARGE_SKIN_TUMOURS_DESCRIPTION = String.join(SPACE, LARGE_SKIN_TUMOURS, LARGE_SKIN_TUMOURS_EFFECT, REGENERATE);

  public static final String LONG_TERM_MADNESS_DESCRIPTION = LONG_TERM_MADNESS_EFFECT;

  public static final String MAJOR_CONCUSSION_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnCheckAndSaves("Intelligence, Wisdom and Charisma", "concentration"), SIXTH_LEVEL_HEALING, healsNaturallyIn("four weeks"));

  public static final String MAJOR_LIVER_DAMAGE_DESCRIPTION = String.join(SPACE, poisonedOnLongRest("15"), additionalDamage("poison", "3 (1d6)"), poisonDamageOnDrinking("3 (1d6)"));

  public static final String MAJOR_NEURALGIA_DESCRIPTION = String.join(SPACE, NERVE_DAMAGE, loseAction("15"), SIXTH_LEVEL_HEALING, healsWithRest("twenty days"));

  public static final String MAJOR_ORGAN_NECROSIS_DESCRIPTION = String.join(SPACE, loseAction("15"), SIXTH_LEVEL_HEALING);

  public static final String MINOR_CONCUSSION_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnChecks("Intelligence"), ANY_MAGIC_HEALING, healsNaturallyIn("two weeks"), ifThenYou("you already have minor concussion", "suffer a major concussion"));

  public static final String MINOR_DISFIGUREMENT_DESCRIPTION = String.join(SPACE, LIGHTLY_SCARRED, NO_EFFECT, SIXTH_LEVEL_HEALING);

  public static final String MINOR_HEADACHES_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnChecks("Wisdom"), ANY_MAGIC_HEALING, healsNaturallyIn("two weeks"));

  public static final String MINOR_INTERNAL_INJURY_DESCRIPTION = String.join(SPACE, loseAction("10"), SIXTH_LEVEL_HEALING, healsWithRest("ten days"));

  public static final String MINOR_KIDNEY_DAMAGE_DESCRIPTION = String.join(SPACE, poisonedOnLongRest("10"), REGENERATE, healsWithChecks("15", "week", "six"));

  public static final String MINOR_LIVER_DAMAGE_DESCRIPTION = String.join(SPACE, poisonedOnLongRest("10"), additionalDamage("poison", "2 (1d4)"), poisonDamageOnDrinking("2 (1d4)"), REGENERATE);

  public static final String MINOR_NAUSEA_DESCRIPTION = String.join(SPACE, MINOR_NAUSEA_EFFECT, ANY_MAGIC_HEALING, healsNaturallyIn("one week"));

  public static final String MINOR_NEURALGIA_DESCRIPTION = String.join(SPACE, NERVE_DAMAGE, loseAction("10"), SIXTH_LEVEL_HEALING, healsWithRest("ten days"));

  public static final String MINOR_ORGAN_NECROSIS_DESCRIPTION = String.join(SPACE, loseAction("10"), SIXTH_LEVEL_HEALING);

  public static final String MUSCLE_SPASMS_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnChecks("Dexterity"), ANY_MAGIC_HEALING, healsNaturallyIn("two weeks"));

  public static final String MUSCULAR_BREAKDOWN_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnCheckAndSaves("Strength"), REGENERATE, healsNaturallyIn("six weeks"));

  public static final String NAUSEA_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnChecks("Constitution"), ANY_MAGIC_HEALING, healsNaturallyIn("four weeks"));

  public static final String NECROTIC_DISCOLOURATION_DESCRIPTION = String.join(SPACE, NECROTIC_DISCOLORATION, NO_EFFECT, SIXTH_LEVEL_HEALING);

  public static final String NECROTIC_STENCH_DESCRIPTION = String.join(SPACE, NECROTIC_STENCH, YOU_HAVE, disadvantageOnChecks("Charisma (Persuasion)"), SIXTH_LEVEL_HEALING);

  public static final String FESTERING_WOUND_DESCRIPTION = String.join(SPACE, FESTERING_WOUND_EFFECT, ANY_MAGIC_HEALING, healsWithChecks("15", "day", "ten"));

  public static final String ORGAN_DAMAGE_DESCRIPTION = String.join(SPACE, loseAction("15"), REGENERATE, healsWithChecks("15", "day", "ten"));

  public static final String PARTIAL_BLINDNESS_DESCRIPTION = String.join(SPACE, damagedBodyPart("eyes"), PARTIAL_BLINDNESS_EFFECT, REGENERATE, ifThenYou("you have already suffered partial blindness", "you are blinded"));

  public static final String PARTIAL_DEAFNESS_DESCRIPTION = String.join(SPACE, damagedBodyPart("eardrums"), PARTIAL_DEAFNESS_EFFECT, REGENERATE);

  public static final String PHOBIA_DESCRIPTION = String.join(SPACE, PHOBIA_EFFECT, REGENERATE);

  public static final String PIERCED_STOMACH_DESCRIPTION = String.join(SPACE, poisonedOnLongRest("10"), SIXTH_LEVEL_HEALING, healsWithRest("ten days"));

  public static final String SECOND_DEGREE_BURNS_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnChecks("Strength, Dexterity, and Constitution"), REGENERATE, ifThenYou("you already have second degree burns", "instead suffer third degree burns"));

  public static final String SEVERE_BRUISING_DESCRIPTION = String.join(SPACE, SEVERE_BRUISING, additionalDamage("bludgeoning or force", "1"), ANY_MAGIC_HEALING, healsNaturallyIn("two weeks"));

  public static final String SEVERE_HEADACHES_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnCheckAndSaves("Wisdom"), stunnedOnDamage("bludgeoning, force or psychic"), REGENERATE);

  public static final String SHORT_TERM_MADNESS_DESCRIPTION = SHORT_TERM_MADNESS_EFFECT;

  public static final String SMALL_SKIN_TUMOURS_DESCRIPTION = String.join(SPACE, SMALL_SKIN_TUMOURS, YOU_HAVE, disadvantageOnChecks("Charisma"), REGENERATE, ifThenYou("your small skin tumors are not cured within one year", "develop Large Skin Tumors"));

  public static final String SPIRITUAL_INJURY_DESCRIPTION = String.join(SPACE, DEPRESSION, YOU_HAVE, disadvantageOnCheckAndSaves("Intelligence, Wisdom, and Charisma"), MUST_BE_DIVING_MAGIC_HEALING);

  public static final String SYSTEMIC_DAMAGE_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnCheckAndSaves("Strength, Dexterity, and Constitution"), REGENERATE);

  public static final String THIRD_DEGREE_BURNS_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnCheckAndSaves("Constitution"), stunnedOnDamage("fire"), REGENERATE, healsWithChecks("15", "week", "ten"), ifThenYou("you already have third degree burns", "instead suffer fourth degree burns"));

  public static final String THROAT_INJURY_DESCRIPTION = String.join(SPACE, EXHAUSTION_EFFECT, disadvantageOnChecks("Constitution"), REGENERATE);

  public static final String VERTIGO_DESCRIPTION = String.join(SPACE, YOU_HAVE, disadvantageOnChecks("Dexterity"), REGENERATE, healsNaturallyIn("eight weeks"));

  public static final String WEAK_PERSONA_DESCRIPTION = String.join(SPACE, SENSE_OF_SELF, YOU_HAVE, disadvantageOnChecks("Charisma"), REGENERATE, healsNaturallyIn("four weeks"));

  public static final String UNHARMED_DESCRIPTION = UNHARMED;

  public static final String DEAD_DESCRIPTION = DEAD;

  public static final String INVALID_INJURY_DESCRIPTION = "An invalid damage type was specified.";
}
