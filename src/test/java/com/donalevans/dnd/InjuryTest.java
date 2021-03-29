package com.donalevans.dnd;

import org.junit.Test;

import static com.donalevans.dnd.constants.InjuryDescriptionStrings.MINOR_CONCUSSION_DESCRIPTION;
import static org.assertj.core.api.Assertions.assertThat;

public class InjuryTest {

  @Test
  public void constructorSetsValues() {
    Injury testInjury = new Injury(0, Injury.DamageType.ACID, Injury.Direction.NONE);
    assertThat(testInjury.getBodyPart()).isNotNull();
    assertThat(testInjury.getSeverity()).isNotNull();
    assertThat(testInjury.getDuration()).isNotNull();
    assertThat(testInjury.getDescription()).isEqualTo("Unharmed: You are unharmed by the attack!");
  }

  @Test
  public void test() {
    System.out.println(MINOR_CONCUSSION_DESCRIPTION);
  }
}
