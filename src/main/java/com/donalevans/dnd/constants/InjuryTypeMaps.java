package com.donalevans.dnd.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static com.donalevans.dnd.constants.InjuryType.*;

public class InjuryTypeMaps {
  public static final TreeMap<Integer, InjuryType> acidInjuries = new TreeMap<>(getAcidInjuryMap());

  private static Map<Integer, InjuryType> getAcidInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, MINOR_DISFIGUREMENT);
    acidInjuries.put(20, BLISTERS);
    acidInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    acidInjuries.put(50, MINOR_NEURALGIA);
    acidInjuries.put(65, MAJOR_NEURALGIA);
    acidInjuries.put(80, DESTROYED_FOOT_OR_LEG);
    acidInjuries.put(85, DESTROYED_HAND);
    acidInjuries.put(90, PARTIAL_BLINDNESS);
    acidInjuries.put(95, BLINDNESS);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> bludgeoningInjuries = new TreeMap<>(getBludgeoningInjuryMap());

  private static Map<Integer, InjuryType> getBludgeoningInjuryMap() {
    Map<Integer, InjuryType> bludgeoningInjuries = new HashMap<>();
    bludgeoningInjuries.put(Integer.MIN_VALUE, UNHARMED);
    bludgeoningInjuries.put(1, BROKEN_NOSE);
    bludgeoningInjuries.put(20, SEVERE_BRUISING);
    bludgeoningInjuries.put(35, MINOR_CONCUSSION);
    bludgeoningInjuries.put(50, MAJOR_CONCUSSION);
    bludgeoningInjuries.put(65, BROKEN_RIBS);
    bludgeoningInjuries.put(80, INTERNAL_INJURY);
    bludgeoningInjuries.put(85, BROKEN_ARM);
    bludgeoningInjuries.put(90, BROKEN_LEG);
    bludgeoningInjuries.put(95, BRAIN_INJURY);
    return bludgeoningInjuries;
  }

  public static final TreeMap<Integer, InjuryType> coldInjuries = new TreeMap<>(getColdInjuryMap());

  private static Map<Integer, InjuryType> getColdInjuryMap() {
    Map<Integer, InjuryType> coldInjuries = new HashMap<>();
    coldInjuries.put(Integer.MIN_VALUE, UNHARMED);
    coldInjuries.put(1, ANOSMIA);
    coldInjuries.put(20, MINOR_NEURALGIA);
    coldInjuries.put(35, FROSTBITTEN_HAND);
    coldInjuries.put(50, FROSTBITTEN_FOOT);
    coldInjuries.put(65, MAJOR_NEURALGIA);
    coldInjuries.put(80, DESTROYED_FOOT_OR_LEG);
    coldInjuries.put(85, DESTROYED_HAND);
    coldInjuries.put(90, SYSTEMIC_DAMAGE);
    coldInjuries.put(95, PARTIAL_BLINDNESS);
    return coldInjuries;
  }

  public static final TreeMap<Integer, InjuryType> fireInjuries = new TreeMap<>(getFireInjuryMap());

  private static Map<Integer, InjuryType> getFireInjuryMap() {
    Map<Integer, InjuryType> fireInjuries = new HashMap<>();
    fireInjuries.put(Integer.MIN_VALUE, UNHARMED);
    fireInjuries.put(1, FIRST_DEGREE_BURNS);
    fireInjuries.put(20, BLISTERS);
    fireInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    fireInjuries.put(50, MINOR_NEURALGIA);
    fireInjuries.put(65, MAJOR_NEURALGIA);
    fireInjuries.put(80, SECOND_DEGREE_BURNS);
    fireInjuries.put(85, THIRD_DEGREE_BURNS);
    fireInjuries.put(90, FOURTH_DEGREE_BURNS);
    fireInjuries.put(95, PARTIAL_BLINDNESS);
    return fireInjuries;
  }

  public static final TreeMap<Integer, InjuryType> lightningInjuries = new TreeMap<>(getLightningInjuryMap());

  private static Map<Integer, InjuryType> getLightningInjuryMap() {
    Map<Integer, InjuryType> lightningInjuries = new HashMap<>();
    lightningInjuries.put(Integer.MIN_VALUE, UNHARMED);
    lightningInjuries.put(1, FLASH_BURNS);
    lightningInjuries.put(20, MUSCLE_SPASMS);
    lightningInjuries.put(35, MUSCULAR_BREAKDOWN);
    lightningInjuries.put(50, CARDIAC_INJURY);
    lightningInjuries.put(65, MAJOR_NEURALGIA);
    lightningInjuries.put(80, KIDNEY_FAILURE);
    lightningInjuries.put(85, DESTROYED_FOOT_OR_LEG);
    lightningInjuries.put(90, DESTROYED_HAND);
    lightningInjuries.put(95, BRAIN_INJURY);
    return lightningInjuries;
  }

  public static final TreeMap<Integer, InjuryType> necroticInjuries = new TreeMap<>(getNecroticInjuryMap());

  private static Map<Integer, InjuryType> getNecroticInjuryMap() {
    Map<Integer, InjuryType> necroticInjuries = new HashMap<>();
    necroticInjuries.put(Integer.MIN_VALUE, UNHARMED);
    necroticInjuries.put(1, NECROTIC_DISCOLOURATION);
    necroticInjuries.put(20, INFLAMMATION);
    necroticInjuries.put(35, FESTERING_WOUND);
    necroticInjuries.put(50, NECROTIC_STENCH);
    necroticInjuries.put(65, MINOR_ORGAN_NECROSIS);
    necroticInjuries.put(80, MAJOR_ORGAN_NECROSIS);
    necroticInjuries.put(85, WITHERED_FOOT);
    necroticInjuries.put(90, WITHERED_HAND);
    necroticInjuries.put(95, SPIRITUAL_INJURY);
    return necroticInjuries;
  }

  public static final TreeMap<Integer, InjuryType> piercingInjuries = new TreeMap<>(getPiercingInjuryMap());

  private static Map<Integer, InjuryType> getPiercingInjuryMap() {
    Map<Integer, InjuryType> piercingInjuries = new HashMap<>();
    piercingInjuries.put(Integer.MIN_VALUE, UNHARMED);
    piercingInjuries.put(1, MINOR_DISFIGUREMENT);
    piercingInjuries.put(20, FESTERING_WOUND);
    piercingInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    piercingInjuries.put(50, PIERCED_STOMACH);
    piercingInjuries.put(65, ORGAN_DAMAGE);
    piercingInjuries.put(80, CARDIAC_INJURY);
    piercingInjuries.put(85, GROIN_INJURY);
    piercingInjuries.put(90, THROAT_INJURY);
    piercingInjuries.put(95, PARTIAL_BLINDNESS);
    return piercingInjuries;
  }

  public static final TreeMap<Integer, InjuryType> poisonInjuries = new TreeMap<>(getPoisonInjuryMap());

  private static Map<Integer, InjuryType> getPoisonInjuryMap() {
    Map<Integer, InjuryType> poisonInjuries = new HashMap<>();
    poisonInjuries.put(Integer.MIN_VALUE, UNHARMED);
    poisonInjuries.put(1, MINOR_NAUSEA);
    poisonInjuries.put(20, NAUSEA);
    poisonInjuries.put(35, VERTIGO);
    poisonInjuries.put(50, CARDIAC_INJURY);
    poisonInjuries.put(65, MINOR_KIDNEY_DAMAGE);
    poisonInjuries.put(80, KIDNEY_FAILURE);
    poisonInjuries.put(85, MINOR_LIVER_DAMAGE);
    poisonInjuries.put(90, MAJOR_LIVER_DAMAGE);
    poisonInjuries.put(95, SYSTEMIC_DAMAGE);
    return poisonInjuries;
  }

  public static final TreeMap<Integer, InjuryType> psychicInjuries = new TreeMap<>(getPsychicInjuryMap());

  private static Map<Integer, InjuryType> getPsychicInjuryMap() {
    Map<Integer, InjuryType> psychicInjuries = new HashMap<>();
    psychicInjuries.put(Integer.MIN_VALUE, UNHARMED);
    psychicInjuries.put(1, SHORT_TERM_MADNESS);
    psychicInjuries.put(20, INAPPROPRIATE_VOLUME);
    psychicInjuries.put(35, MINOR_HEADACHES);
    psychicInjuries.put(50, WEAK_PERSONA);
    psychicInjuries.put(65, LONG_TERM_MADNESS);
    psychicInjuries.put(80, PHOBIA);
    psychicInjuries.put(85, SEVERE_HEADACHES);
    psychicInjuries.put(90, INDEFINITE_MADNESS);
    psychicInjuries.put(95, BRAIN_INJURY);
    return psychicInjuries;
  }

  public static final TreeMap<Integer, InjuryType> radiantInjuries = new TreeMap<>(getRadiantInjuryMap());

  private static Map<Integer, InjuryType> getRadiantInjuryMap() {
    Map<Integer, InjuryType> radiantInjuries = new HashMap<>();
    radiantInjuries.put(Integer.MIN_VALUE, UNHARMED);
    radiantInjuries.put(1, HAIR_LOSS_AND_COSMETIC_DAMAGE);
    radiantInjuries.put(20, FIRST_DEGREE_BURNS);
    radiantInjuries.put(35, BLISTERS);
    radiantInjuries.put(50, SMALL_SKIN_TUMOURS);
    radiantInjuries.put(65, LARGE_SKIN_TUMOURS);
    radiantInjuries.put(80, SECOND_DEGREE_BURNS);
    radiantInjuries.put(85, THIRD_DEGREE_BURNS);
    radiantInjuries.put(90, PARTIAL_BLINDNESS);
    radiantInjuries.put(95, BLINDNESS);
    return radiantInjuries;
  }

  public static final TreeMap<Integer, InjuryType> slashingInjuries = new TreeMap<>(getSlashingInjuryMap());

  private static Map<Integer, InjuryType> getSlashingInjuryMap() {
    Map<Integer, InjuryType> slashingInjuries = new HashMap<>();
    slashingInjuries.put(Integer.MIN_VALUE, UNHARMED);
    slashingInjuries.put(1, MINOR_DISFIGUREMENT);
    slashingInjuries.put(20, FESTERING_WOUND);
    slashingInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    slashingInjuries.put(50, MINOR_INTERNAL_INJURY);
    slashingInjuries.put(65, INTERNAL_INJURY);
    slashingInjuries.put(80, HAMSTRUNG);
    slashingInjuries.put(85, DESTROYED_FOOT_OR_LEG);
    slashingInjuries.put(90, DESTROYED_HAND);
    slashingInjuries.put(95, PARTIAL_BLINDNESS);
    return slashingInjuries;
  }

  public static final TreeMap<Integer, InjuryType> thunderInjuries = new TreeMap<>(getThunderInjuryMap());

  private static Map<Integer, InjuryType> getThunderInjuryMap() {
    Map<Integer, InjuryType> thunderInjuries = new HashMap<>();
    thunderInjuries.put(Integer.MIN_VALUE, UNHARMED);
    thunderInjuries.put(1, SEVERE_BRUISING);
    thunderInjuries.put(20, MINOR_HEADACHES);
    thunderInjuries.put(35, MINOR_CONCUSSION);
    thunderInjuries.put(50, MAJOR_CONCUSSION);
    thunderInjuries.put(65, INTERNAL_INJURY);
    thunderInjuries.put(80, SEVERE_HEADACHES);
    thunderInjuries.put(85, PARTIAL_DEAFNESS);
    thunderInjuries.put(90, DEAFNESS);
    thunderInjuries.put(95, BRAIN_INJURY);
    return thunderInjuries;
  }
}
