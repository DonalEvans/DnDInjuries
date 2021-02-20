package com.donalevans.dnd.constants;

public class InjuryRecoveryStrings {
  public static final String HEALS_NATURALLY_IN = "The injury heals naturally in %s.";
  public static String healsNaturallyIn(String duration) {
    return String.format(HEALS_NATURALLY_IN, duration);
  }

  public static final String HEALS_NATURALLY_WITH_REST_IN = "If you spend %s doing nothing but resting, the injury will heal naturally.";
  public static String healsWithRest(String duration) {
    return String.format(HEALS_NATURALLY_WITH_REST_IN, duration);
  }

  public static final String MEDICINE_CHECK_DC_DURATION = "Alternately, the injury can be treated with a successful DC %s Wisdom (Medicine) check, in which case it will heal naturally in %s.";
  public static String healsWithCheckAndRest(String checkDC, String duration) {
    return String.format(MEDICINE_CHECK_DC_DURATION, checkDC, duration);
  }

  public static final String MEDICINE_CHECK_DC_FREQUENCY_SUCCESSES = "Alternatively, someone can tend to the injury and make a DC %s Wisdom (Medicine) check once every %s. After %s successes, the injury is healed.";
  public static String healsWithChecks(String checkDC, String frequency, String successes) {
    return String.format(MEDICINE_CHECK_DC_FREQUENCY_SUCCESSES, checkDC, frequency, successes);
  }

  public static final String ANY_MAGIC_HEALING = "The injury heals if you receive any magical healing.";

  public static final String REGENERATE = "The injury can be healed by magic such as the regenerate spell.";

  public final static String SIXTH_LEVEL_HEALING = "The injury can be healed by magical healing of 6th level or higher, such as heal and regenerate.";

  public static final String MAY_HEAL_CROOKED = "The wound may heal crooked if it is crooked when healing is applied.";

  public static final String EFFECT_REMAINS_IF_NOT_SPLINTED = "If the wound is not splinted with a successful DC 15 Wisdom (Medicine) check before healing, the effects remain until it is rebroken and splinted.";

  public static final String MUST_BE_DIVING_MAGIC_HEALING = "Magic such as the heal or regenerate spell can resolve your injury, but such spells must be cast by a cleric, druid, or other class that uses divine magic.";
}
