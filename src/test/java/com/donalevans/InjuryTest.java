package com.donalevans;

import org.junit.Test;

import static com.donalevans.InjuryDescriptionStrings.MINOR_CONCUSSION_DESCRIPTION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjuryTest {

  @Test
  public void constructorSetsValues() {
    Injury testInjury = new Injury(0, Injury.DamageType.ACID, Injury.Direction.NONE);
    assertThat(testInjury.getBodyPart(), nullValue());
    assertThat(testInjury.getSeverity(), nullValue());
    assertThat(testInjury.getDuration(), nullValue());
    assertThat(testInjury.getDescription(), equalTo("Unharmed: You are unharmed by the attack!"));
  }

  @Test
  public void test() {
    System.out.println(MINOR_CONCUSSION_DESCRIPTION);
  }
}
