package com.donalevans.dnd.ui;

import com.donalevans.dnd.Character;
import com.donalevans.dnd.Injury;
import com.donalevans.dnd.SaveFileIO;
import com.donalevans.dnd.constants.InjuryType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static com.donalevans.dnd.SaveFileIO.FILE_EXTENSION;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JFileChooser.CANCEL_OPTION;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    when(characterMock.generateInjury(expectedSpillover, expectedRoll, expectedDamageType)).thenReturn(injuryMock);

    assertThat(injuryGenerator.getInjuryFromSelectedCharacter(), equalTo(injuryMock));
    verify(characterMock).generateInjury(expectedSpillover, expectedRoll, expectedDamageType);
  }

  @Test
  public void getInjuryFromSelectedCharacterThrowsWhenValidateIntegerInputsReturnsNonEmptyString() {
    String invalidValueText = "Error text";
    doReturn(invalidValueText).when(injuryGenerator).validateIntegerInputs();
    Exception ex = assertThrows(IllegalArgumentException.class, () -> injuryGenerator.getInjuryFromSelectedCharacter());
    assertThat(ex.getMessage(), equalTo(invalidValueText + "Please enter an integer."));
  }

  @Test
  public void getInjuryFromSelectedCharacterThrowsWhenGetSelectedCharacterReturnsNull() {
    doReturn("").when(injuryGenerator).validateIntegerInputs();
    Exception ex = assertThrows(IllegalArgumentException.class, () -> injuryGenerator.getInjuryFromSelectedCharacter());
    assertThat(ex.getMessage(), equalTo("Create new character before creating or removing injuries."));
  }

  @Test
  public void removeInjuryFromCharacterRemovesInjuryFromCharacter() {
    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();
    Injury injuryMock = mock(Injury.class);

    injuryGenerator.removeInjuryFromCharacter(injuryMock);

    verify(characterMock).removeInjury(injuryMock);
  }

  @Test
  public void removeInjuryFromCharacterThrowsWhenSelectedCharacterIsNull() {
    doReturn(null).when(injuryGenerator).getSelectedCharacter();
    Injury injuryMock = mock(Injury.class);

    Exception ex = assertThrows(IllegalArgumentException.class, () -> injuryGenerator.removeInjuryFromCharacter(injuryMock));
    assertThat(ex.getMessage(), equalTo("Create new character before creating or removing injuries."));
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
  public void generateAndAddInjurySetsOutputAreaErrorTextAndDoesNotAddInjuryWhenExceptionIsThrown() {
    String exceptionMessage = "exception message";
    IllegalArgumentException ex = new IllegalArgumentException(exceptionMessage);
    doThrow(ex).when(injuryGenerator).getInjuryFromSelectedCharacter();

    injuryGenerator.generateAndAddInjury();

    verify(injuryGenerator).setOutputAreaText(exceptionMessage);
    verify(injuryGenerator, times(1)).setOutputAreaText(anyString());
    verify(injuryGenerator, never()).getSelectedCharacter();
  }

  @Test
  public void addNewCharacterAddsCharacterToSelectorIfNotPresent() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    String characterName = "name";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).createCharacter();

    doReturn(false).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.addNewCharacter();

    verify(injuryGenerator).addItemToSelector(characterMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void addNewCharacterDoesNotAddCharacterToSelectorIfPresent() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    String characterName = "name";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).createCharacter();

    doReturn(true).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.addNewCharacter();

    verify(injuryGenerator, never()).addItemToSelector(characterMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void addNewCharacterDoesNotAddCharacterToSelectorAndSetsOutputErrorTextIfMaxHealthIsInvalid() {
    doReturn(true).when(injuryGenerator).isInvalidNumberInput(anyString());
    String characterName = "name";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    injuryGenerator.addNewCharacter();

    verify(injuryGenerator, never()).createCharacter();
    verify(injuryGenerator, never()).addItemToSelector(any());
    verify(injuryGenerator).setOutputAreaText("Invalid value specified for max health.\n");
  }

  @Test
  public void addNewCharacterDoesNotAddCharacterToSelectorAndSetsOutputErrorTextIfCharacterNameIsInvalid() {
    doReturn(false).when(injuryGenerator).isInvalidNumberInput(anyString());
    String emptyString = "";
    doReturn(null, emptyString).when(injuryGenerator).getCharacterNameText();

    // Character name == null
    injuryGenerator.addNewCharacter();
    // Character name == ""
    injuryGenerator.addNewCharacter();

    verify(injuryGenerator, never()).addItemToSelector(any());
    verify(injuryGenerator, times(2)).setOutputAreaText("Please enter a character name.\n");
  }

  @Test
  public void addNewCharacterDoesNotAddCharacterToSelectorAndSetsOutputErrorTextIfMaxHealthAndCharacterNameAreInvalid() {
    doReturn(true).when(injuryGenerator).isInvalidNumberInput(anyString());
    String characterName = "";
    doReturn(characterName).when(injuryGenerator).getCharacterNameText();

    injuryGenerator.addNewCharacter();

    verify(injuryGenerator, never()).createCharacter();
    verify(injuryGenerator, never()).addItemToSelector(any());
    verify(injuryGenerator).setOutputAreaText("Invalid value specified for max health.\n" +
            "Please enter a character name.\n");
  }

  @Test
  public void doSaveCharacterSavesCharacterAndAddsToSelectorIfNotPresent() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveCharacter(characterMock, fileMock)).thenReturn(true);
    doReturn(false).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.doSaveCharacter();

    verify(saveFileIOMock).saveCharacter(characterMock, fileMock);
    verify(injuryGenerator).addItemToSelector(characterMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void doSaveCharacterSavesCharacterAndDoesNotAddToSelectorIfPresent() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveCharacter(characterMock, fileMock)).thenReturn(true);
    doReturn(true).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.doSaveCharacter();

    verify(saveFileIOMock).saveCharacter(characterMock, fileMock);
    verify(injuryGenerator, never()).addItemToSelector(characterMock);
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
  }

  @Test
  public void doSaveCharacterEnsuresCorrectFileExtension() {
    String fileNameWithExtension = "test." + FILE_EXTENSION;
    String fileNameWithoutExtension = "test";
    String expectedFileName = "test.dnd";

    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    // Try with file extension present
    injuryGenerator.doSaveCharacter();

    ArgumentCaptor<File> fileArgumentCaptor = ArgumentCaptor.forClass(File.class);
    verify(saveFileIOMock).saveCharacter(eq(characterMock), fileArgumentCaptor.capture());
    assertEquals(expectedFileName, fileArgumentCaptor.getValue().getName());

    when(fileMock.getName()).thenReturn(fileNameWithoutExtension);
    when(fileMock.getPath()).thenReturn(fileNameWithoutExtension);

    // Try without file extension present
    injuryGenerator.doSaveCharacter();

    verify(saveFileIOMock, times(2)).saveCharacter(eq(characterMock), fileArgumentCaptor.capture());
    assertEquals(expectedFileName, fileArgumentCaptor.getValue().getName());
  }

  @Test
  public void doSaveCharacterOutputsErrorMessageAndAddsCharacterToSelectorIfNotPresentWhenFileCannotBeSaved() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    Character characterMock = mock(Character.class);
    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();

    when(saveFileIOMock.saveCharacter(characterMock, fileMock)).thenReturn(false);
    doReturn(false).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.doSaveCharacter();

    verify(saveFileIOMock).saveCharacter(characterMock, fileMock);
    verify(injuryGenerator).addItemToSelector(characterMock);
    verify(injuryGenerator).setOutputAreaText("Character file could not be saved.");
  }

  @Test
  public void doSaveCharacterOutputsErrorMessageWhenNoCharacterSelected() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);
    String fileNameWithExtension = "test." + FILE_EXTENSION;
    when(fileMock.getName()).thenReturn(fileNameWithExtension);

    injuryGenerator.doSaveCharacter();

    verify(injuryGenerator).setOutputAreaText("Create new character before saving.");
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void doSaveCharacterDoesNothingIfFileSelectionIsCancelled() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showSaveDialog(any())).thenReturn(CANCEL_OPTION);

    injuryGenerator.doSaveCharacter();

    verify(chooserMock, never()).getSelectedFile();
    verify(injuryGenerator, never()).getSelectedCharacter();
    verify(injuryGenerator, never()).setOutputAreaText(anyString());
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void doLoadCharacterLoadsCharacterAndAddsToSelectorIfNotPresent() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();
    Character characterMock = mock(Character.class);
    when(saveFileIOMock.loadCharacter(fileMock)).thenReturn(characterMock);

    doReturn(false).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.doLoadCharacter();

    verify(saveFileIOMock).loadCharacter(fileMock);
    verify(injuryGenerator).addItemToSelector(characterMock);
  }

  @Test
  public void doLoadCharacterLoadsCharacterAndDoesNotAddToSelectorIfPresent() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(APPROVE_OPTION);

    File fileMock = mock(File.class);
    when(chooserMock.getSelectedFile()).thenReturn(fileMock);

    SaveFileIO saveFileIOMock = mock(SaveFileIO.class);
    doReturn(saveFileIOMock).when(injuryGenerator).getSaveFileIO();
    Character characterMock = mock(Character.class);
    when(saveFileIOMock.loadCharacter(fileMock)).thenReturn(characterMock);

    doReturn(true).when(injuryGenerator).selectorContainsItem(characterMock);

    injuryGenerator.doLoadCharacter();

    verify(saveFileIOMock).loadCharacter(fileMock);
    verify(injuryGenerator, never()).addItemToSelector(characterMock);
  }

  @Test
  public void doLoadCharacterDoesNothingIfFileSelectionIsCancelled() {
    JFileChooser chooserMock = mock(JFileChooser.class);
    doReturn(chooserMock).when(injuryGenerator).getFileChooser();
    when(chooserMock.showDialog(any(), anyString())).thenReturn(CANCEL_OPTION);

    injuryGenerator.doLoadCharacter();

    verify(chooserMock, never()).getSelectedFile();
    verify(injuryGenerator, never()).getSaveFileIO();
    verify(injuryGenerator, never()).addItemToSelector(any());
  }

  @Test
  public void characterSelectedCallsPopulateCharacterFieldsWithCorrectValues() {
    String characterName = "name";
    int maxHP = 12;
    Injury injuryMockPresent = mock(Injury.class);
    Injury injuryMockNotPresent = mock(Injury.class);
    List<Injury> existingInjuries = new ArrayList<>();
    existingInjuries.add(injuryMockPresent);
    existingInjuries.add(injuryMockNotPresent);

    Character characterMock = mock(Character.class);
    when(characterMock.getName()).thenReturn(characterName);
    when(characterMock.getMaxHP()).thenReturn(maxHP);
    when(characterMock.getExistingInjuries()).thenReturn(existingInjuries);

    doReturn(characterMock).when(injuryGenerator).getSelectedCharacter();
    doReturn(false).when(injuryGenerator).selectorContainsItem(injuryMockPresent);
    doReturn(true).when(injuryGenerator).selectorContainsItem(injuryMockNotPresent);

    injuryGenerator.characterSelected();

    verify(injuryGenerator).populateCharacterFields(characterName, maxHP, existingInjuries);
    verify(injuryGenerator).addItemToSelector(injuryMockPresent);
    verify(injuryGenerator, never()).addItemToSelector(injuryMockNotPresent);
  }
}
