package com.donalevans.dnd.ui;

import com.donalevans.dnd.Character;
import com.donalevans.dnd.Injury;
import com.donalevans.dnd.Party;
import com.donalevans.dnd.SaveFileIO;
import com.donalevans.dnd.constants.InjuryType;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.donalevans.dnd.SaveFileIO.PARTY_FILE_EXTENSION;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.CANCEL_OPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InjuryGeneratorTest {

  private InjuryGenerator injuryGenerator;

  @Before
  public void setUp() {
    injuryGenerator = spy(new InjuryGenerator());
  }

  @Test
  public void createPartySetsErrorTextIfPartyNameIsNull() {
    doReturn(null).when(injuryGenerator).getPartyNameText();

    injuryGenerator.createParty();

    verify(injuryGenerator).setOutputAreaText("Please enter a party name.");
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void createPartySetsErrorTextIfPartyNameIsEmptyString() {
    doReturn("").when(injuryGenerator).getPartyNameText();

    injuryGenerator.createParty();

    verify(injuryGenerator).setOutputAreaText("Please enter a party name.");
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void createPartyAddsPartyToSelectorIfNotPresent() {
    String partyName = "Droop Troop";
    doReturn(partyName).when(injuryGenerator).getPartyNameText();
    doReturn(false).when(injuryGenerator).partySelectorContainsParty(any(Party.class));

    injuryGenerator.createParty();

    ArgumentCaptor<Party> partyCaptor = ArgumentCaptor.forClass(Party.class);
    verify(injuryGenerator).addItemToSelector(partyCaptor.capture());
    assertThat(partyName).isEqualTo(partyCaptor.getValue().getName());
  }

  @Test
  public void createPartyDoesNotAddPartyToSelectorIfPresent() {
    doReturn("Droop Troop").when(injuryGenerator).getPartyNameText();
    doReturn(true).when(injuryGenerator).partySelectorContainsParty(any(Party.class));

    injuryGenerator.createParty();

    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void getInjuryFromSelectedCharacterReturnsInjury() {
    doReturn("").when(injuryGenerator).validateIntegerInputs();
    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    String rollText = "1";
    int expectedRoll = Integer.parseInt(rollText);
    doReturn(rollText).when(injuryGenerator).getRollText();
    String spilloverText = "2";
    int expectedSpillover = Integer.parseInt(spilloverText);
    doReturn(spilloverText).when(injuryGenerator).getSpilloverText();
    Injury.DamageType expectedDamageType = Injury.DamageType.ACID;
    doReturn(expectedDamageType).when(injuryGenerator).getDamageType();

    Injury injuryMock = mock(Injury.class);
    when(characterMock.generateInjury(expectedSpillover, expectedRoll, expectedDamageType))
        .thenReturn(injuryMock);

    assertThat(injuryGenerator.getInjuryFromSelectedCharacter()).isEqualTo(injuryMock);
    verify(characterMock).generateInjury(expectedSpillover, expectedRoll, expectedDamageType);
  }

  @Test
  public void
      getInjuryFromSelectedCharacterReturnsInjurySetsRollValueWhenAutoGenerateRollIsSelected() {
    doReturn("").when(injuryGenerator).validateIntegerInputs();
    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    doReturn(true).when(injuryGenerator).isRollBoxSelected();
    int generatedRoll = 42;
    Random randomMock = mock(Random.class);
    when(randomMock.nextInt(anyInt())).thenReturn(generatedRoll);
    doReturn(randomMock).when(injuryGenerator).getRandom();

    String spilloverText = "2";
    int expectedSpillover = Integer.parseInt(spilloverText);
    doReturn(spilloverText).when(injuryGenerator).getSpilloverText();
    Injury.DamageType expectedDamageType = Injury.DamageType.ACID;
    doReturn(expectedDamageType).when(injuryGenerator).getDamageType();

    Injury injuryMock = mock(Injury.class);
    when(characterMock.generateInjury(expectedSpillover, generatedRoll, expectedDamageType))
        .thenReturn(injuryMock);

    assertThat(injuryGenerator.getInjuryFromSelectedCharacter()).isEqualTo(injuryMock);
    verify(characterMock).generateInjury(expectedSpillover, generatedRoll, expectedDamageType);
    verify(injuryGenerator).setRollText(String.valueOf(generatedRoll));
  }

  @Test
  public void getInjuryFromSelectedCharacterThrowsWhenValidateIntegerInputsReturnsNonEmptyString() {
    String invalidValueText = "Error text";
    doReturn(invalidValueText).when(injuryGenerator).validateIntegerInputs();
    assertThatThrownBy(() -> injuryGenerator.getInjuryFromSelectedCharacter())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage(invalidValueText + "Please enter an integer.");
  }

  @Test
  public void getInjuryFromSelectedCharacterThrowsWhenGetSelectedCharacterReturnsNull() {
    doReturn("").when(injuryGenerator).validateIntegerInputs();
    assertThatThrownBy(() -> injuryGenerator.getInjuryFromSelectedCharacter())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Create new character before creating or removing injuries.");
  }

  @Test
  public void addSelectedInjuryToCharacterThrowsIfNoCharacterIsSelected() {
    doReturn(null).when(injuryGenerator).getSelectedCharacter();
    assertThatThrownBy(() -> injuryGenerator.addSelectedInjuryToCharacter())
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Add character to party before adding or removing injuries.");
    verify(injuryGenerator, never()).addItemToSelector(any(Injury.class));
  }

  @Test
  public void addSelectedInjuryToCharacterAddsInjuryToCharacterAndSelector() {
    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    Injury injuryMock = mock(Injury.class);
    doReturn(injuryMock).when(injuryGenerator).getSelectedInjury();

    injuryGenerator.addSelectedInjuryToCharacter();

    verify(characterMock).addInjury(injuryMock);
    verify(injuryGenerator).characterUpdated();
    verify(injuryGenerator).addItemToSelector(injuryMock);
  }

  @Test
  public void validateIntegerInputsReturnsTrueWhenInputsAreValid() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs()).isEmpty();
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenMaxHealthInputIsInvalid() {
    doReturn(true, false, false).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs())
        .isEqualTo("Invalid value specified for max health.\n");
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenSpilloverInputIsInvalid() {
    doReturn(false, true, false).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs())
        .isEqualTo("Invalid value specified for spillover.\n");
  }

  @Test
  public void
      validateIntegerInputsReturnsFalseAndSetsOutputTextWhenRollInputIsInvalidAndRollBoxIsSelected() {
    doReturn(false).when(injuryGenerator).isRollBoxSelected();
    doReturn(false, false, true).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs())
        .isEqualTo("Invalid value specified for roll.\n");
  }

  @Test
  public void validateIntegerInputsReturnsTrueWhenRollInputIsInvalidAndRollBoxIsNotSelected() {
    doReturn(true).when(injuryGenerator).isRollBoxSelected();
    doReturn(false, false, true).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs()).isEmpty();
  }

  @Test
  public void validateIntegerInputsReturnsFalseAndSetsOutputTextWhenAllInputsAreInvalid() {
    doReturn(false).when(injuryGenerator).isRollBoxSelected();
    doReturn(true, true, true).when(injuryGenerator).isInvalidNumberInput(anyString());
    assertThat(injuryGenerator.validateIntegerInputs())
        .isEqualTo(
            "Invalid value specified for max health.\n"
                + "Invalid value specified for spillover.\n"
                + "Invalid value specified for roll.\n"
                + "");
  }

  @Test
  public void isInvalidNumberInputReturnsTrueForNullString() {
    assertThat(injuryGenerator.isInvalidNumberInput(null)).isTrue();
  }

  @Test
  public void isInvalidNumberInputReturnsTrueForNonParsableStrings() {
    assertThat(injuryGenerator.isInvalidNumberInput("")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput(" ")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput("a")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput("one")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput("1a")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput("a1")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput(" 1")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput("1 ")).isTrue();
    assertThat(injuryGenerator.isInvalidNumberInput("1.5")).isTrue();
  }

  @Test
  public void isInvalidNumberInputReturnsFalseForParsableStrings() {
    assertThat(injuryGenerator.isInvalidNumberInput("1")).isFalse();
    assertThat(injuryGenerator.isInvalidNumberInput("0")).isFalse();
    assertThat(injuryGenerator.isInvalidNumberInput("-1")).isFalse();
  }

  @Test
  public void generateInjurySetsOutputAreaText() {
    Injury injuryMock = mock(Injury.class);
    String descriptionString = "description";
    when(injuryMock.getDescription()).thenReturn(descriptionString);
    doReturn(injuryMock).when(injuryGenerator).getInjuryFromSelectedCharacter();

    injuryGenerator.generateInjury();

    verify(injuryGenerator).setOutputAreaText(descriptionString);
  }

  @Test
  public void generateInjurySetsOutputAreaErrorTextWhenExceptionIsThrown() {
    String exceptionMessage = "exception message";
    IllegalArgumentException ex = new IllegalArgumentException(exceptionMessage);
    doThrow(ex).when(injuryGenerator).getInjuryFromSelectedCharacter();

    injuryGenerator.generateInjury();

    verify(injuryGenerator).setOutputAreaText(exceptionMessage);
    verify(injuryGenerator, times(1)).setOutputAreaText(anyString());
  }

  @Test
  public void generateAndAddInjuryAddsInjuryToCharacterAndSetsOutputAreaText() {
    Injury injuryMock = mock(Injury.class);
    String descriptionString = "description";
    when(injuryMock.getDescription()).thenReturn(descriptionString);
    when(injuryMock.getInjuryType()).thenReturn(InjuryType.BRAIN_INJURY);
    doReturn(injuryMock).when(injuryGenerator).getInjuryFromSelectedCharacter();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    injuryGenerator.generateAndAddInjury();

    verify(injuryGenerator).setOutputAreaText(descriptionString);
    verify(characterMock).addInjury(injuryMock);
    verify(injuryGenerator).addItemToSelector(injuryMock);
  }

  @Test
  public void generateAndAddInjurySetsOutputAreaTextAndDoesNotAddInjuryWhenInjuryTypeIsUNHARMED() {
    Injury injuryMock = mock(Injury.class);
    String descriptionString = "description";
    when(injuryMock.getDescription()).thenReturn(descriptionString);
    when(injuryMock.getInjuryType()).thenReturn(InjuryType.UNHARMED);
    doReturn(injuryMock).when(injuryGenerator).getInjuryFromSelectedCharacter();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    injuryGenerator.generateAndAddInjury();

    verify(injuryGenerator).setOutputAreaText(descriptionString);
    verify(characterMock, never()).addInjury(injuryMock);
    verify(injuryGenerator, never()).addItemToSelector(injuryMock);
  }

  @Test
  public void
      generateAndAddInjurySetsOutputAreaErrorTextAndDoesNotAddInjuryWhenExceptionIsThrown() {
    String exceptionMessage = "exception message";
    IllegalArgumentException ex = new IllegalArgumentException(exceptionMessage);
    doThrow(ex).when(injuryGenerator).getInjuryFromSelectedCharacter();

    injuryGenerator.generateAndAddInjury();

    verify(injuryGenerator).setOutputAreaText(exceptionMessage);
    verify(injuryGenerator, times(1)).setOutputAreaText(anyString());
    verify(injuryGenerator, never()).getSelectedCharacter();
  }

  @Test
  public void addCharacterToPartyAddsCharacterToPartyIfNotPresent() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    String characterName = "name";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).createCharacter();

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    injuryGenerator.addCharacterToParty();

    verify(partyMock).add(characterMock);
    verify(injuryGenerator).partyUpdated();
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void addCharacterToPartyDoesNotAddDuplicateCharacterToSelectorIfPresent() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    String characterName = "name";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).createCharacter();

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    injuryGenerator.addCharacterToParty();

    verify(injuryGenerator, never()).addItemToSelector(characterMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void addCharacterToPartySetsOutputErrorWhenNoPartySelected() {
    injuryGenerator.addCharacterToParty();

    verify(injuryGenerator).setOutputAreaText("Create or load Party before adding a character.");
  }

  @Test
  public void
      addCharacterToPartyDoesNotAddCharacterToSelectorAndSetsOutputErrorTextIfMaxHealthIsInvalid() {
    doReturn(true).when(injuryGenerator).isInvalidNumberInput(anyString());
    doReturn(mock(Party.class)).when(injuryGenerator).getSelectedParty();
    String characterName = "name";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    injuryGenerator.addCharacterToParty();

    verify(injuryGenerator, never()).createCharacter();
    verify(injuryGenerator, never()).addItemToSelector(any());
    verify(injuryGenerator).setOutputAreaText("Invalid value specified for max health.\n");
  }

  @Test
  public void
      addCharacterToPartyDoesNotAddCharacterToSelectorAndSetsOutputErrorTextIfCharacterNameIsInvalid() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    doReturn(mock(Party.class)).when(injuryGenerator).getSelectedParty();
    String emptyString = "";
    doReturn(null, emptyString).when(injuryGenerator).getCharacterNameText();

    // Character name == null
    injuryGenerator.addCharacterToParty();
    // Character name == ""
    injuryGenerator.addCharacterToParty();

    verify(injuryGenerator, never()).addItemToSelector(any());
    verify(injuryGenerator, times(2)).setOutputAreaText("Please enter a character name.\n");
  }

  @Test
  public void
      addCharacterToPartyDoesNotAddCharacterToSelectorAndSetsOutputErrorTextIfMaxHealthAndCharacterNameAreInvalid() {
    doReturn(true).when(injuryGenerator).isInvalidNumberInput(anyString());
    doReturn(mock(Party.class)).when(injuryGenerator).getSelectedParty();
    String characterName = "";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    injuryGenerator.addCharacterToParty();

    verify(injuryGenerator, never()).createCharacter();
    verify(injuryGenerator, never()).addItemToSelector(any());
    verify(injuryGenerator)
        .setOutputAreaText(
            "Invalid value specified for max health.\n" + "Please enter a character name.\n");
  }

  @Test
  public void removeCharacterFromPartyDoesNothingIfNoPartySelected() {
    doReturn(null).when(injuryGenerator).getSelectedParty();

    injuryGenerator.removeCharacterFromParty();

    verify(injuryGenerator, never()).partyUpdated();
  }

  @Test
  public void removeCharacterFromPartyDoesNothingIfNoCharacterSelected() {
    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    doReturn(null).when(injuryGenerator).getSelectedCharacter();

    injuryGenerator.removeCharacterFromParty();

    verify(injuryGenerator, never()).partyUpdated();
  }

  @Test
  public void removeCharacterFromPartyRemovesSelectedCharacterFromParty() {
    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    doReturn("69").when(injuryGenerator).getMaxHealthText();

    injuryGenerator.removeCharacterFromParty();

    verify(partyMock).remove(characterMock);
    verify(injuryGenerator).partyUpdated();
  }

  @Test
  public void doSavePartySavesPartyAndAddsToSelectorIfNotPresent() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveParty(partyMock, fileMock)).thenReturn(true);
    doReturn(false).when(injuryGenerator).partySelectorContainsParty(partyMock);

    injuryGenerator.doSaveParty();

    verify(saveFileIOMock).saveParty(partyMock, fileMock);
    verify(injuryGenerator).addItemToSelector(partyMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void doSavePartySavesPartyAndDoesNotAddToSelectorIfPresent() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveParty(partyMock, fileMock)).thenReturn(true);
    doReturn(true).when(injuryGenerator).partySelectorContainsParty(partyMock);

    injuryGenerator.doSaveParty();

    verify(saveFileIOMock).saveParty(partyMock, fileMock);
    verify(injuryGenerator, never()).addItemToSelector(partyMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void doSavePartyStoresPreviousSaveLoadLocationOnSuccess() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);
    File parentFile = mock(File.class);
    when(fileMock.getParentFile()).thenReturn(parentFile);

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveParty(partyMock, fileMock)).thenReturn(true);

    assertThat(injuryGenerator.getPreviousSaveLoadLocation()).isNull();

    injuryGenerator.doSaveParty();

    assertThat(injuryGenerator.getPreviousSaveLoadLocation()).isEqualTo(parentFile);
  }

  @Test
  public void doSavePartyEnsuresCorrectFileExtension() {
    String fileNameWithExtension = "test." + PARTY_FILE_EXTENSION;
    String fileNameWithoutExtension = "test";
    String expectedFileName = "test.pty";

    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    // Try with file extension present
    injuryGenerator.doSaveParty();

    ArgumentCaptor<File> fileArgumentCaptor = ArgumentCaptor.forClass(File.class);
    verify(saveFileIOMock).saveParty(eq(partyMock), fileArgumentCaptor.capture());
    assertThat(fileArgumentCaptor.getValue()).hasName(expectedFileName);

    when(fileMock.getName()).thenReturn(fileNameWithoutExtension);
    when(fileMock.getPath()).thenReturn(fileNameWithoutExtension);

    // Try without file extension present
    injuryGenerator.doSaveParty();

    verify(saveFileIOMock, times(2)).saveParty(eq(partyMock), fileArgumentCaptor.capture());
    assertThat(fileArgumentCaptor.getValue()).hasName(expectedFileName);
  }

  @Test
  public void
      doSavePartyOutputsErrorMessageAndAddsPartyToSelectorIfNotPresentWhenFileCannotBeSaved() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Party partyMock = mock(Party.class);
    doReturn(partyMock).when(injuryGenerator).getSelectedParty();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveParty(partyMock, fileMock)).thenReturn(false);
    doReturn(false).when(injuryGenerator).partySelectorContainsParty(partyMock);

    injuryGenerator.doSaveParty();

    verify(saveFileIOMock).saveParty(partyMock, fileMock);
    verify(injuryGenerator).addItemToSelector(partyMock);
    verify(injuryGenerator).setOutputAreaText("Party file could not be saved.");
  }

  @Test
  public void doSavePartyOutputsErrorMessageWhenNoPartySelected() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    injuryGenerator.doSaveParty();

    verify(injuryGenerator).setOutputAreaText("Create new party before saving.");
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void doSavePartyDoesNothingIfFileSelectionIsCancelled() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(CANCEL_OPTION);

    injuryGenerator.doSaveParty();

    verify(chooserMock, never()).getSelectedFile();
    verify(injuryGenerator, never()).getSelectedParty();
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void doLoadPartyLoadsPartyAndAddsToSelectorIfNotPresent()
      throws IOException, ClassNotFoundException {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    String validPartyFileName = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(validPartyFileName);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();
    Party partyMock = mock(Party.class);
    when(partyMock.isEmpty()).thenReturn(true);
    when(saveFileIOMock.loadParty(fileMock)).thenReturn(partyMock);

    doReturn(false).when(injuryGenerator).partySelectorContainsParty(partyMock);

    injuryGenerator.doLoadParty();

    verify(saveFileIOMock).loadParty(fileMock);
    verify(injuryGenerator).addItemToSelector(partyMock);
  }

  @Test
  public void doLoadPartyLoadsPartyAndDoesNotAddToSelectorIfPresent()
      throws IOException, ClassNotFoundException {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    String validPartyFileName = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(validPartyFileName);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();
    Party partyMock = mock(Party.class);
    when(partyMock.isEmpty()).thenReturn(true);
    when(saveFileIOMock.loadParty(fileMock)).thenReturn(partyMock);

    doReturn(true).when(injuryGenerator).partySelectorContainsParty(partyMock);

    injuryGenerator.doLoadParty();

    verify(saveFileIOMock).loadParty(fileMock);
    verify(injuryGenerator, never()).addItemToSelector(partyMock);
  }

  @Test
  public void doLoadPartySelectsCharacterFromPartyIfNotEmpty()
      throws IOException, ClassNotFoundException {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    String validPartyFileName = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(validPartyFileName);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    Party testParty = new Party("Droop Troop");
    Character characterMock = mock(Character.class);
    testParty.add(characterMock);

    when(saveFileIOMock.loadParty(fileMock)).thenReturn(testParty);

    doReturn(false).when(injuryGenerator).partySelectorContainsParty(testParty);

    injuryGenerator.doLoadParty();

    assertThat(injuryGenerator.getSelectedCharacter()).isEqualTo(characterMock);
  }

  @Test
  public void doLoadPartyStoresPreviousSaveLoadLocationOnSuccess()
      throws IOException, ClassNotFoundException {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    String validPartyFileName = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(validPartyFileName);
    File parentFile = mock(File.class);
    when(fileMock.getParentFile()).thenReturn(parentFile);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    Party partyMock = mock(Party.class);
    when(partyMock.isEmpty()).thenReturn(true);
    when(saveFileIOMock.loadParty(fileMock)).thenReturn(partyMock);

    assertThat(injuryGenerator.getPreviousSaveLoadLocation()).isNull();

    injuryGenerator.doLoadParty();

    assertThat(injuryGenerator.getPreviousSaveLoadLocation()).isEqualTo(parentFile);
  }

  @Test
  public void doLoadPartyDoesNothingIfFileSelectionIsCancelled() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(CANCEL_OPTION);

    injuryGenerator.doLoadParty();

    verify(chooserMock, never()).getSelectedFile();
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void doLoadPartySetsErrorOutputIfInvalidFileSelected() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    String noFileExtensionName = "noFileExtension";
    when(fileMock.getName()).thenReturn(noFileExtensionName);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    injuryGenerator.doLoadParty();

    verify(injuryGenerator).setOutputAreaText("Invalid file selected.");
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any(Party.class));

    String wrongFileExtensionName = "test.dnd";
    when(fileMock.getName()).thenReturn(wrongFileExtensionName);

    injuryGenerator.doLoadParty();

    verify(injuryGenerator, times(2)).setOutputAreaText("Invalid file selected.");
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any(Party.class));
  }

  @Test
  public void doLoadCharacterSetsErrorOutputWhenExceptionIsThrownWhenLoadingFile()
      throws IOException, ClassNotFoundException {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    String validPartyFileName = "test." + PARTY_FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(validPartyFileName);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doThrow(new ClassNotFoundException()).when(saveFileIOMock).loadParty(fileMock);

    injuryGenerator.doLoadParty();

    verify(injuryGenerator).setOutputAreaText("Selected file could not be loaded.");
    verify(injuryGenerator, never()).addItemToSelector(any(Party.class));
  }

  @Test
  public void characterSelectedCallsPopulateCharacterFieldsWithCorrectValues() {
    String characterName = "name";
    int maxHP = 12;
    Injury injuryMock = mock(Injury.class);
    List<Injury> existingInjuries = new ArrayList<>();
    existingInjuries.add(injuryMock);

    Character characterMock = mock(Character.class);
    when(characterMock.getName()).thenReturn(characterName);
    when(characterMock.getMaxHP()).thenReturn(maxHP);
    when(characterMock.getExistingInjuries()).thenReturn(existingInjuries);

    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    injuryGenerator.characterSelected();

    verify(injuryGenerator).populateCharacterFields(characterName, maxHP, existingInjuries);
    verify(injuryGenerator).addItemToSelector(injuryMock);
  }

  @Test
  public void removeSelectedInjuryRemovesInjuryFromCharacter() {
    Injury injuryMock = mock(Injury.class);
    doReturn(injuryMock).when(injuryGenerator).getSelectedExistingInjury();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    injuryGenerator.removeSelectedInjury();

    verify(characterMock).removeInjury(injuryMock);
    verify(injuryGenerator).characterUpdated();
  }

  @Test
  public void removeSelectedInjurySetsErrorOutputWhenSelectedCharacterIsNull() {
    Injury injuryMock = mock(Injury.class);
    doReturn(injuryMock).when(injuryGenerator).getSelectedExistingInjury();

    doReturn(null).when(injuryGenerator).getSelectedCharacter();

    injuryGenerator.removeSelectedInjury();

    verify(injuryGenerator)
        .setOutputAreaText("Create new character before creating or removing injuries.");
    verify(injuryGenerator, never()).characterUpdated();
  }

  @Test
  public void removeSelectedInjuryDoesNothingWhenNoInjurySelected() {
    doReturn(null).when(injuryGenerator).getSelectedExistingInjury();

    injuryGenerator.removeSelectedInjury();

    verify(injuryGenerator, never()).setOutputAreaText(anyString());
    verify(injuryGenerator, never()).characterUpdated();
  }

  @Test
  public void partySelectorContainsPartyReturnsCorrectly() {
    Party partyMock = mock(Party.class);

    assertThat(injuryGenerator.partySelectorContainsParty(partyMock)).isFalse();

    injuryGenerator.addItemToSelector(partyMock);

    assertThat(injuryGenerator.partySelectorContainsParty(partyMock)).isTrue();
  }

  @Test
  public void characterSelectorContainsCharacterReturnsCorrectly() {
    Character characterMock = mock(Character.class);

    assertThat(injuryGenerator.characterSelectorContainsCharacter(characterMock)).isFalse();

    injuryGenerator.addItemToSelector(characterMock);

    assertThat(injuryGenerator.characterSelectorContainsCharacter(characterMock)).isTrue();
  }

  @Test
  public void existingInjuriesSelectorContainsInjuryReturnsCorrectly() {
    Injury injuryMock = mock(Injury.class);

    assertThat(injuryGenerator.existingInjuriesSelectorContainsInjury(injuryMock)).isFalse();

    injuryGenerator.addItemToSelector(injuryMock);

    assertThat(injuryGenerator.existingInjuriesSelectorContainsInjury(injuryMock)).isTrue();
  }
}
