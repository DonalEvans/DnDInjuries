package com.donalevans.dnd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Character implements Serializable {
  private static final long serialVersionUID = 396016709724933486L;
  private String name;
  private int maxHP;
  private List<Injury> existingInjuries;

  public Character() {
  }

  public Character(String name, int maxHP) {
    this.name = name;
    this.maxHP = maxHP;
    existingInjuries = new ArrayList<>();
  }

  public Character(String name, int maxHP, List<Injury> existingInjuries) {
    this.name = name;
    this.maxHP = maxHP;
    this.existingInjuries = new ArrayList<>(existingInjuries);
  }

  public Injury generateInjury(int spillover, int roll, Injury.DamageType type) {
    return generateInjury(spillover, roll, type, Injury.Direction.NONE);
  }

  public Injury generateInjury(int currentHP, int damage, int roll, Injury.DamageType type) {
    int spillover = currentHP - damage;
    return generateInjury(spillover, roll, type, Injury.Direction.NONE);
  }

  public Injury generateInjury(int spillover, int roll, Injury.DamageType type, Injury.Direction direction) {
    if (spillover >= maxHP) {
      return new Injury.Dead();
    }
    int severity = 100 - (roll * maxHP) / spillover;
    return new Injury(severity, type, direction);
  }

  public Injury generateInjury(int currentHP, int damage, int roll, Injury.DamageType type, Injury.Direction direction) {
    int spillover = currentHP - damage;
    return generateInjury(spillover, roll, type, direction);
  }

  public void addInjury(Injury injuryToAdd) {
    existingInjuries.add(injuryToAdd);
  }

  public void removeInjury(Injury injuryToRemove) {
    existingInjuries.remove(injuryToRemove);
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

  @Override
  public String toString() {
    return name + ", Max HP = " + maxHP;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Character character = (Character) o;
    return maxHP == character.getMaxHP() && name.equals(character.getName()) && existingInjuries.equals(character.getExistingInjuries());
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, maxHP, existingInjuries);
  }
}
