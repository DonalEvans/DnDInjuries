package com.donalevans.dnd;

import com.donalevans.dnd.Injury;
import org.junit.Test;

import static com.donalevans.dnd.constants.InjuryDescriptionStrings.MINOR_CONCUSSION_DESCRIPTION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class InjuryTest {

  @Test
  public void constructorSetsValues() {
    Injury testInjury = new Injury(0, Injury.DamageType.ACID, Injury.Direction.NONE);
    assertThat(testInjury.getBodyPart(), notNullValue());
    assertThat(testInjury.getSeverity(), notNullValue());
    assertThat(testInjury.getDuration(), notNullValue());
    assertThat(testInjury.getDescription(), equalTo("Unharmed: You are unharmed by the attack!"));
  }

  @Test
  public void test() {
    System.out.println(MINOR_CONCUSSION_DESCRIPTION);
  }
}
