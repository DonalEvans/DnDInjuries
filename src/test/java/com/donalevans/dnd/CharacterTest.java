package com.donalevans.dnd;

import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class CharacterTest {
  private final int maxHP = 10;
  private final int maxRoll = 100;
  private final String characterName = "Test";
  private Character testCharacter;
  private final Injury mockInjury = mock(Injury.class);

  @Before
  public void setUp() {
    List<Injury> injuryList = Collections.singletonList(mockInjury);
    testCharacter = spy(new Character(characterName, maxHP, injuryList));
  }

  @Test
  public void constructorPopulatesFields() {
    assertThat(testCharacter.getName()).isEqualTo(characterName);
    assertThat(testCharacter.getMaxHP()).isEqualTo(maxHP);
    assertThat(testCharacter.getExistingInjuries()).hasSize(1).contains(mockInjury);
  }

  @Test
  public void generateInjuryReturnsDeadWhenSpilloverIsMoreThanMaxHealth() {
    Injury injury = testCharacter.generateInjury(maxHP + 1, maxRoll, Injury.DamageType.ACID);
    assertThat(injury.getDescription()).isEqualTo("Dead: You are dead!");
  }

  @Test
  public void generateInjuryCalculatesSpilloverWhenNoSpecified() {
    final int currentHP = 10;
    final int damage = 15;
    final int expectedSpillover = currentHP - damage;
    Injury.DamageType type = Injury.DamageType.ACID;

    // Use method signature with no direction specified
    testCharacter.generateInjury(currentHP, damage, maxRoll, type);
    verify(testCharacter, times(1))
        .generateInjury(expectedSpillover, maxRoll, type, Injury.Direction.NONE);

    // Use method signature with direction specified
    testCharacter.generateInjury(currentHP, damage, maxRoll, type, Injury.Direction.NONE);
    verify(testCharacter, times(2))
        .generateInjury(expectedSpillover, maxRoll, type, Injury.Direction.NONE);
  }

  @Test
  public void addAndRemoveInjuryAddsAndRemovesInjuries() {
    Injury mockInjury_2 = mock(Injury.class);
    testCharacter.addInjury(mockInjury_2);

    assertThat(testCharacter.getExistingInjuries()).hasSize(2).contains(mockInjury, mockInjury_2);

    testCharacter.removeInjury(mockInjury);

    assertThat(testCharacter.getExistingInjuries()).hasSize(1).contains(mockInjury_2);
  }
}
