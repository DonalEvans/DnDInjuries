package com.donalevans;

import java.util.ArrayList;
import java.util.List;

public class Character {
  private String name;
  private int maxHP;
  private List<Injury> existingInjuries;

  public Character() {
  }

  public Character(String name, int maxHP) {
    this.name = name;
    this.maxHP = maxHP;
    this.existingInjuries = new ArrayList<>();
  }

  public Character(String name, int maxHP, List<Injury> existingInjuries) {
    this.name = name;
    this.maxHP = maxHP;
    this.existingInjuries = existingInjuries;
  }

  public Injury generateInjury(int spillover, int roll, Injury.DamageType type, Injury.Direction direction) {
    if (spillover >= maxHP) {
      // Dead
      return new Injury();
    }
    int severity = 100 - (roll * maxHP) / spillover;
    return new Injury(severity, type);
  }

  public Injury generateInjury(int currentHP, int damage, int roll, Injury.DamageType type, Injury.Direction direction) {
    int spillover = currentHP - damage;
    return generateInjury(spillover, roll, type, direction);
  }

  public void addInjury(Injury injuryToAdd) {
    existingInjuries.add(injuryToAdd);
  }

  public void removeInjury(Injury injuryToRemove) {
    existingInjuries.removeIf(injury -> injury.equals(injuryToRemove));
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getMaxHP() {
    return maxHP;
  }

  public void setMaxHP(int maxHP) {
    this.maxHP = maxHP;
  }

  public List<Injury> getExistingInjuries() {
    return existingInjuries;
  }

  public void setExistingInjuries(List<Injury> existingInjuries) {
    this.existingInjuries = existingInjuries;
  }
}
