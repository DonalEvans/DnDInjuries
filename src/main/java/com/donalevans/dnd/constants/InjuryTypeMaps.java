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
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, BROKEN_NOSE);
    acidInjuries.put(20, SEVERE_BRUISING);
    acidInjuries.put(35, MINOR_CONCUSSION);
    acidInjuries.put(50, MAJOR_CONCUSSION);
    acidInjuries.put(65, BROKEN_RIBS);
    acidInjuries.put(80, INTERNAL_INJURY);
    acidInjuries.put(85, BROKEN_ARM);
    acidInjuries.put(90, BROKEN_LEG);
    acidInjuries.put(95, BRAIN_INJURY);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> coldInjuries = new TreeMap<>(getColdInjuryMap());

  private static Map<Integer, InjuryType> getColdInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, ANOSMIA);
    acidInjuries.put(20, MINOR_NEURALGIA);
    acidInjuries.put(35, FROSTBITTEN_HAND);
    acidInjuries.put(50, FROSTBITTEN_FOOT);
    acidInjuries.put(65, MAJOR_NEURALGIA);
    acidInjuries.put(80, DESTROYED_FOOT_OR_LEG);
    acidInjuries.put(85, DESTROYED_HAND);
    acidInjuries.put(90, SYSTEMIC_DAMAGE);
    acidInjuries.put(95, PARTIAL_BLINDNESS);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> fireInjuries = new TreeMap<>(getFireInjuryMap());

  private static Map<Integer, InjuryType> getFireInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, FIRST_DEGREE_BURNS);
    acidInjuries.put(20, BLISTERS);
    acidInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    acidInjuries.put(50, MINOR_NEURALGIA);
    acidInjuries.put(65, MAJOR_NEURALGIA);
    acidInjuries.put(80, SECOND_DEGREE_BURNS);
    acidInjuries.put(85, THIRD_DEGREE_BURNS);
    acidInjuries.put(90, FOURTH_DEGREE_BURNS);
    acidInjuries.put(95, PARTIAL_BLINDNESS);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> lightningInjuries = new TreeMap<>(getLightningInjuryMap());

  private static Map<Integer, InjuryType> getLightningInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, FLASH_BURNS);
    acidInjuries.put(20, MUSCLE_SPASMS);
    acidInjuries.put(35, MUSCULAR_BREAKDOWN);
    acidInjuries.put(50, CARDIAC_INJURY);
    acidInjuries.put(65, MAJOR_NEURALGIA);
    acidInjuries.put(80, KIDNEY_FAILURE);
    acidInjuries.put(85, DESTROYED_FOOT_OR_LEG);
    acidInjuries.put(90, DESTROYED_HAND);
    acidInjuries.put(95, BRAIN_INJURY);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> necroticInjuries = new TreeMap<>(getNecroticInjuryMap());

  private static Map<Integer, InjuryType> getNecroticInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, NECROTIC_DISCOLOURATION);
    acidInjuries.put(20, INFLAMMATION);
    acidInjuries.put(35, FESTERING_WOUND);
    acidInjuries.put(50, NECROTIC_STENCH);
    acidInjuries.put(65, MINOR_ORGAN_NECROSIS);
    acidInjuries.put(80, MAJOR_ORGAN_NECROSIS);
    acidInjuries.put(85, WITHERED_FOOT);
    acidInjuries.put(90, WITHERED_HAND);
    acidInjuries.put(95, SPIRITUAL_INJURY);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> piercingInjuries = new TreeMap<>(getPiercingInjuryMap());

  private static Map<Integer, InjuryType> getPiercingInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, MINOR_DISFIGUREMENT);
    acidInjuries.put(20, FESTERING_WOUND);
    acidInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    acidInjuries.put(50, PIERCED_STOMACH);
    acidInjuries.put(65, ORGAN_DAMAGE);
    acidInjuries.put(80, CARDIAC_INJURY);
    acidInjuries.put(85, GROIN_INJURY);
    acidInjuries.put(90, THROAT_INJURY);
    acidInjuries.put(95, PARTIAL_BLINDNESS);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> poisonInjuries = new TreeMap<>(getPoisonInjuryMap());

  private static Map<Integer, InjuryType> getPoisonInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, MINOR_NAUSEA);
    acidInjuries.put(20, NAUSEA);
    acidInjuries.put(35, VERTIGO);
    acidInjuries.put(50, CARDIAC_INJURY);
    acidInjuries.put(65, MINOR_KIDNEY_DAMAGE);
    acidInjuries.put(80, KIDNEY_FAILURE);
    acidInjuries.put(85, MINOR_LIVER_DAMAGE);
    acidInjuries.put(90, MAJOR_LIVER_DAMAGE);
    acidInjuries.put(95, SYSTEMIC_DAMAGE);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> psychicInjuries = new TreeMap<>(getPsychicInjuryMap());

  private static Map<Integer, InjuryType> getPsychicInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, SHORT_TERM_MADNESS);
    acidInjuries.put(20, INAPPROPRIATE_VOLUME);
    acidInjuries.put(35, MINOR_HEADACHES);
    acidInjuries.put(50, WEAK_PERSONA);
    acidInjuries.put(65, LONG_TERM_MADNESS);
    acidInjuries.put(80, PHOBIA);
    acidInjuries.put(85, SEVERE_HEADACHES);
    acidInjuries.put(90, INDEFINITE_MADNESS);
    acidInjuries.put(95, BRAIN_INJURY);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> radiantInjuries = new TreeMap<>(getRadiantInjuryMap());

  private static Map<Integer, InjuryType> getRadiantInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, HAIR_LOSS_AND_COSMETIC_DAMAGE);
    acidInjuries.put(20, FIRST_DEGREE_BURNS);
    acidInjuries.put(35, BLISTERS);
    acidInjuries.put(50, SMALL_SKIN_TUMOURS);
    acidInjuries.put(65, LARGE_SKIN_TUMOURS);
    acidInjuries.put(80, SECOND_DEGREE_BURNS);
    acidInjuries.put(85, THIRD_DEGREE_BURNS);
    acidInjuries.put(90, PARTIAL_BLINDNESS);
    acidInjuries.put(95, BLINDNESS);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> slashingInjuries = new TreeMap<>(getSlashingInjuryMap());

  private static Map<Integer, InjuryType> getSlashingInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, MINOR_DISFIGUREMENT);
    acidInjuries.put(20, FESTERING_WOUND);
    acidInjuries.put(35, HORRIBLE_DISFIGUREMENT);
    acidInjuries.put(50, MINOR_INTERNAL_INJURY);
    acidInjuries.put(65, INTERNAL_INJURY);
    acidInjuries.put(80, HAMSTRUNG);
    acidInjuries.put(85, DESTROYED_FOOT_OR_LEG);
    acidInjuries.put(90, DESTROYED_HAND);
    acidInjuries.put(95, PARTIAL_BLINDNESS);
    return acidInjuries;
  }

  public static final TreeMap<Integer, InjuryType> thunderInjuries = new TreeMap<>(getThunderInjuryMap());

  private static Map<Integer, InjuryType> getThunderInjuryMap() {
    Map<Integer, InjuryType> acidInjuries = new HashMap<>();
    acidInjuries.put(Integer.MIN_VALUE, UNHARMED);
    acidInjuries.put(1, SEVERE_BRUISING);
    acidInjuries.put(20, MINOR_HEADACHES);
    acidInjuries.put(35, MINOR_CONCUSSION);
    acidInjuries.put(50, MAJOR_CONCUSSION);
    acidInjuries.put(65, INTERNAL_INJURY);
    acidInjuries.put(80, SEVERE_HEADACHES);
    acidInjuries.put(85, PARTIAL_DEAFNESS);
    acidInjuries.put(90, DEAFNESS);
    acidInjuries.put(95, BRAIN_INJURY);
    return acidInjuries;
  }
}
