package com.donalevans;

public class InjuryEffectStrings {

  public static final String YOU_HAVE = "You have";

  public static final String SPACE = " ";

  public static final String NO_EFFECT = "This injury causes no mechanical effects.";

  public static final String DISADVANTAGE_ON_CHECKS = "disadvantage on %s checks.";
  public static String disadvantageOnChecks(String checks) {
    return String.format(DISADVANTAGE_ON_CHECKS, checks);
  }

  public static final String DISADVANTAGE_ON_SAVES = "disadvantage on %s saves.";
  public static String disadvantageOnSaves(String saves) {
    return String.format(DISADVANTAGE_ON_SAVES, saves);
  }

  public static final String DISADVANTAGE_ON_CHECKS_AND_SAVES =
      "disadvantage on %s checks and saves.";
  public static String disadvantageOnCheckAndSaves(String checksAndSaves) {
    return String.format(DISADVANTAGE_ON_CHECKS_AND_SAVES, checksAndSaves);
  }

  public static final String DISADVANTAGE_ON_CHECKS_AND_SAVES_DIFFERENT =
      "disadvantage on %1$s checks and %2$s saves.";
  public static String disadvantageOnCheckAndSaves(String checks, String saves) {
    return String.format(DISADVANTAGE_ON_CHECKS_AND_SAVES_DIFFERENT, checks, saves);
  }

  public static final String ADDITIONAL_DAMAGE_TYPE_AMOUNT =
      "Anytime you suffer %1$s damage, you suffer an additional %2$s point(s) of damage.";
  public static String additionalDamage(String type, String amount) {
    return String.format(ADDITIONAL_DAMAGE_TYPE_AMOUNT, type, amount);
  }

  public static final String POISON_DAMAGE_ON_DRINKING =
      "Anytime you drink alcohol or take another drug, you take %s poison damage.";
  public static String poisonDamageOnDrinking(String amount) {
    return String.format(POISON_DAMAGE_ON_DRINKING, amount);
  }

  public static final String POISONED_ON_LONG_REST_DC =
          "When you complete a long rest, you must succeed at a DC %s Constitution saving throw or gain the poisoned condition until you complete a long rest.";
  public static String poisonedOnLongRest(String checkDC) {
    return String.format(POISONED_ON_LONG_REST_DC, checkDC);
  }

  public static final String LOSE_ACTION_DC =
      "Whenever you attempt an action in combat, you must make a DC %s Constitution saving throw. On a failed save, you lose your action and can't use reactions until the start of your next turn.";
  public static String loseAction(String checkDC) {
    return String.format(LOSE_ACTION_DC, checkDC);
  }

  public static final String GAIN_CONDITION = "You gain the %s condition.";
  public static String gainCondition(String condition) {
    return String.format(GAIN_CONDITION, condition);
  }

  public static final String STUNNED_ON_DAMAGE =
      "If you fail a saving throw against an effect that causes %s damage, you are also stunned until the end of your next turn.";
  public static String stunnedOnDamage(String damageType) {
    return String.format(STUNNED_ON_DAMAGE, damageType);
  }

  public static final String COMPOUNDING_EXHAUSTION_IF =
      "If %s, you gain another level of exhaustion that can be removed by normal means.";
  public static String gainExhaustionIf(String condition) {
    return String.format(COMPOUNDING_EXHAUSTION_IF, condition);
  }

  public static final String GAIN_EFFECT_IF = "If %s, you %s.";
  public static String ifThenYou(String condition, String effect) {
    return String.format(GAIN_EFFECT_IF, condition, effect);
  }

  public static final String EXHAUSTION_EFFECT =
      "You gain a level of exhaustion which cannot be removed by normal means.";

  public static final String ANOSMIA_EFFECT =
      "You automatically fail any ability checks that involve your sense of smell or taste.";

  public static final String BROKEN_ARM_EFFECT =
      "You can no longer hold anything with two hands, and you can hold only a single object at a time.";

  public static final String BROKEN_LEG_EFFECT =
      "Your speed on foot is halved, and you must use a cane or crutch to move. You fall prone after using the Dash action. You have disadvantage on Dexterity checks made to balance.";

  public static final String SECOND_DEGREE_BURNS_EFFECT =
      YOU_HAVE + SPACE + String.format(DISADVANTAGE_ON_CHECKS, "Strength, Dexterity, and Constitution") + SPACE
          + "If you already have second degree burns, you instead suffer third degree burns.";

  public static final String THIRD_DEGREE_BURNS_EFFECT =
      YOU_HAVE + SPACE + String.format(DISADVANTAGE_ON_CHECKS_AND_SAVES_DIFFERENT, "all", "Constitution") + SPACE
          + String.format(STUNNED_ON_DAMAGE, "fire") + SPACE
          + "If you already have third degree burns, you instead suffer fourth degree burns.";

  public static final String FOURTH_DEGREE_BURNS_EFFECT =
      YOU_HAVE + SPACE + String.format(DISADVANTAGE_ON_CHECKS_AND_SAVES_DIFFERENT, "all", "Strength, Dexterity, and Constitution") +  SPACE
          + String.format(STUNNED_ON_DAMAGE, "fire") + SPACE
          + "If you already have fourth degree burns, you must succeed at a DC 15 Constitution saving throw or die.";

  public static final String DAMAGED_FOOT_EFFECT =
      "Your speed on foot is reduced by 5 feet. You must succeed at a DC 10 Dexterity saving throw after using the Dash action or fall prone.";

  public static final String DAMAGED_HAND_EFFECT =
      "In order to grasp or manipulate an object with your hand, you must succeed at a DC 15 Dexterity check.";

  public static final String FESTERING_WOUND_EFFECT =
      "Your hit point maximum is reduced by 1 every 24 hours the wound persists. If your hit point maximum drops to 0, you die.";

  public static final String GROIN_INJURY_EFFECT =
      "Your speed on foot is halved, and you must use a cane or crutch to move. You cannot take the Dash action. You are also sterile.";

  public static final String HAIR_LOSS_AND_COSMETIC_DAMAGE_EFFECT =
      "Visible hair on your body burns away but will grow back as normal. If you have any exposed tattoos, they fade as if exposed to 10 years of sunlight.";

  public static final String HORRIBLE_DISFIGUREMENT_EFFECT =
      YOU_HAVE + SPACE + String.format(DISADVANTAGE_ON_CHECKS, "Charisma (Persuasion)") + SPACE
          + "You have advantage on Charisma (Intimidation) checks.";

  public static final String INAPPROPRIATE_VOLUME_EFFECT =
      "You can’t regulate your volume. You shout when you intend to whisper, and whisper when you intend to shout.";

  public static final String LARGE_SKIN_TUMOURS_EFFECT =
      YOU_HAVE + SPACE + String.format(DISADVANTAGE_ON_CHECKS, "Charisma and Wisdom") + SPACE
          + "If not cured within six months, you also get "
          + String.format(DISADVANTAGE_ON_CHECKS_AND_SAVES, "Strength, Dexterity, and Constitution");

  public static final String SHORT_TERM_MADNESS_EFFECT =
      "Roll on the Short-term Madness table in the Dungeon Masters Guide. Your madness lasts twice as long.";

  public static final String LONG_TERM_MADNESS_EFFECT =
      "Roll on the Long-term Madness table in the Dungeon Masters Guide. Your madness lasts twice as long.";

  public static final String INDEFINITE_MADNESS_EFFECT =
      "Roll on the Indefinite Madness table in the Dungeon Masters Guide.";

  public static final String MINOR_CONCUSSION_EFFECT =
      YOU_HAVE + SPACE + String.format(DISADVANTAGE_ON_CHECKS, "Intelligence") + SPACE
          + "If you already have a minor concussion, you have "
          + String.format(DISADVANTAGE_ON_CHECKS_AND_SAVES_DIFFERENT, "Intelligence, Wisdom and Charisma", "concentration");

  public static final String MINOR_NAUSEA_EFFECT =
      "You must succeed at a DC 10 Constitution saving throw before you can consume food.";

  public static final String PARTIAL_BLINDNESS_EFFECT =
      YOU_HAVE + SPACE + "disadvantage on Wisdom (Perception) checks that rely on sight and on ranged attack rolls.";

  public static final String PARTIAL_DEAFNESS_EFFECT =
      YOU_HAVE + SPACE + "disadvantage on any ability check that requires hearing.";

  public static final String PHOBIA_EFFECT =
      "You develop a debilitating fear of something in the situation from which you gained your injury. For example, if you were damaged by a mind flayer, you might have a fear of octopuses. The DM will decide. When you are confronted with your phobia, you have disadvantage on all ability checks and saving throws.";
}
