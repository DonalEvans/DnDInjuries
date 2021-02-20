package com.donalevans.dnd.constants;

public class InjuryFlavourStrings {
  public static final String BROKEN_BODY_PART = "You have a broken %s.";
  public static String brokenBodyPart(String part) {
    return String.format(BROKEN_BODY_PART, part);
  }

  public static final String DESTROYED_BODY_PART = "Your %s is/are destroyed.";
  public static String destroyedBodyPart(String part) {
    return String.format(DESTROYED_BODY_PART, part);
  }

  public static final String DAMAGED_BODY_PART = "Your %s is/are damaged.";
  public static String damagedBodyPart(String part) {
    return String.format(DAMAGED_BODY_PART, part);
  }

  public static final String LOSE_SMELL_TASTE = "You lose your sense of smell and taste.";

  public static final String BLISTERS = "You have severe blisters.";

  public static final String BRAIN_INJURY = "You have suffered a brain injury.";

  public static final String SUPERFICIAL_BURNS = "You have superficial burns amd turn red as a lobster.";

  public static final String SUPERFICIAL_PAINFUL_BURNS = "You have superficial but painful burns.";

  public static final String BADLY_SCARRED = "You are disfigured to the extent that the scars can't be easily concealed.";

  public static final String LIGHTLY_SCARRED = "You are lightly scarred.";

  public static final String MUSCLE_INFLAMMATION = "Your muscles are irritated and inflamed.";

  public static final String LARGE_SKIN_TUMOURS = "You develop several large, painful skin tumours.";

  public static final String NERVE_DAMAGE = "You are in constant pain from nerve damage.";

  public static final String NECROTIC_DISCOLORATION = "You get white and gray spots on your cheeks.";

  public static final String NECROTIC_STENCH = "You smell like rotting flesh.";

  public static final String SEVERE_BRUISING = "You suffer severe bruising over an extensive portion of your anatomy.";

  public static final String SMALL_SKIN_TUMOURS = "You develop several small, painless skin tumours.";

  public static final String DEPRESSION = "You are afflicted with intense apathy and depression.";

  public static final String SENSE_OF_SELF = "You have suffered damage to your sense of self.";

  public static final String UNHARMED = "You are unharmed by the attack!";

  public static final String DEAD = "You are dead!";
}
