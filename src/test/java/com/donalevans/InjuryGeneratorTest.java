package com.donalevans;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InjuryGeneratorTest {

  private InjuryGenerator injuryGenerator;

  @Before
  public void setUp() {
    injuryGenerator = spy(new InjuryGenerator());
  }

  @Test
  public void generateInjuryCreatesCharacterAndGeneratesInjuryIfInputsAreValid() {
    doReturn("").when(injuryGenerator).validateIntegerInputs();
    int expectedRoll = 1;
    doReturn(String.valueOf(expectedRoll)).when(injuryGenerator).getRollText();
    int expectedSpillover = 2;
    doReturn(String.valueOf(expectedSpillover)).when(injuryGenerator).getSpilloverText();
    doReturn(false).when(injuryGenerator).isRollBoxSelected();

    Character mockCharacter = mock(Character.class);
    doReturn(mockCharacter).when(injuryGenerator).createCharacter();

    Injury mockInjury = mock(Injury.class);
    String testDescription = "testDescription";
    when(mockInjury.getDescription()).thenReturn(testDescription);

    Injury.DamageType expectedDamageType = Injury.DamageType.ACID;
    doReturn(expectedDamageType).when(injuryGenerator).getDamageType();

    when(mockCharacter.generateInjury(expectedSpillover, expectedRoll, expectedDamageType)).thenReturn(mockInjury);

    injuryGenerator.generateInjury();

    verify(injuryGenerator).createCharacter();
    verify(mockCharacter).generateInjury(expectedSpillover, expectedRoll, expectedDamageType);
    verify(injuryGenerator).setOutputAreaText(testDescription);
  }

  @Test
  public void generateInjuryGeneratesRandomRollIfRollBoxIsSelected() {
    doReturn("").when(injuryGenerator).validateIntegerInputs();
    doReturn(true).when(injuryGenerator).isRollBoxSelected();
    doReturn("1").when(injuryGenerator).getSpilloverText();

    Character mockCharacter = mock(Character.class);
    doReturn(mockCharacter).when(injuryGenerator).createCharacter();

    Injury mockInjury = mock(Injury.class);
    when(mockCharacter.generateInjury(anyInt(), anyInt(), any())).thenReturn(mockInjury);

    injuryGenerator.generateInjury();

    verify(injuryGenerator).getRandom();
    verify(injuryGenerator, never()).getRollText();
  }

  @Test
  public void generateInjuryReturnsEarlyAndSetsOutputTextIfValidateIntegerInputsReturnsNonEmptyString() {
    String testErrorText = "Error.";
    doReturn(testErrorText).when(injuryGenerator).validateIntegerInputs();
    injuryGenerator.generateInjury();
    verify(injuryGenerator).setOutputAreaText(testErrorText + "Please enter an integer.");
    verify(injuryGenerator, never()).createCharacter();
  }

  @Test
  public void validateIntegerInputsReturnsTrueWhenInputsAreValid() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs(), equalTo(""));
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenMaxHealthInputIsInvalid() {
    doReturn(true, false, false).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs(), equalTo("Invalid value specified for max health.\n"));
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenSpilloverInputIsInvalid() {
    doReturn(false, true, false).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs(), equalTo("Invalid value specified for spillover.\n"));
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenRollInputIsInvalidAndRollBoxIsSelected() {
    doReturn(false).when(injuryGenerator).isRollBoxSelected();
    doReturn(false, false, true).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs(), equalTo("Invalid value specified for roll.\n"));
  }

  @Test
  public void validateIntegerInputsReturnsTrueWhenRollInputIsInvalidAndRollBoxIsNotSelected() {
    doReturn(true).when(injuryGenerator).isRollBoxSelected();
    doReturn(false, false, true).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs(), equalTo(""));
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenAllInputsAreInvalid() {
    doReturn(false).when(injuryGenerator).isRollBoxSelected();
    doReturn(true, true, true).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs(), equalTo("Invalid value specified for max health.\n"
            + "Invalid value specified for spillover.\n"
            + "Invalid value specified for roll.\n"
            + ""));
  }

  @Test
  public void isInvalidNumberInputReturnsTrueForNullString() {
    assertTrue(injuryGenerator.isInvalidNumberInput(null));
  }

  @Test
  public void isInvalidNumberInputReturnsTrueForNonParsableStrings() {
    assertTrue(injuryGenerator.isInvalidNumberInput(""));
    assertTrue(injuryGenerator.isInvalidNumberInput(" "));
    assertTrue(injuryGenerator.isInvalidNumberInput("a"));
    assertTrue(injuryGenerator.isInvalidNumberInput("one"));
    assertTrue(injuryGenerator.isInvalidNumberInput("1a"));
    assertTrue(injuryGenerator.isInvalidNumberInput("a1"));
    assertTrue(injuryGenerator.isInvalidNumberInput(" 1"));
    assertTrue(injuryGenerator.isInvalidNumberInput("1 "));
    assertTrue(injuryGenerator.isInvalidNumberInput("1.5"));
  }

  @Test
  public void isInvalidNumberInputReturnsFalseForParsableStrings() {
    assertFalse(injuryGenerator.isInvalidNumberInput("1"));
    assertFalse(injuryGenerator.isInvalidNumberInput("0"));
    assertFalse(injuryGenerator.isInvalidNumberInput("-1"));
  }
}
