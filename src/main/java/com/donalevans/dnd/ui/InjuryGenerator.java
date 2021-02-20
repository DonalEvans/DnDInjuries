package com.donalevans.dnd.ui;

import com.donalevans.dnd.Character;
import com.donalevans.dnd.Injury;
import com.donalevans.dnd.constants.InjuryType;
import com.donalevans.dnd.SaveFileIO;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.donalevans.dnd.SaveFileIO.FILE_EXTENSION;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class InjuryGenerator extends JFrame{

  private JTextPane outputArea;
  private JButton generateAndAddInjuryButton;
  private JTextField spilloverField;
  private JTextField maxHealthField;
  private JComboBox<Injury.DamageType> damageTypeSelector;
  private JPanel mainFrame;
  private JTextField rollValueField;
  private JCheckBox generateRollBox;
  private JComboBox<InjuryType> injuryDescriptionSelector;
  private JButton describeInjuryButton;
  private JComboBox<Character> characterSelector;
  private JButton loadCharacterButton;
  private JButton saveCharacterButton;
  private JTextField characterNameField;
  private JComboBox<Injury> existingInjuriesSelector;
  private JButton describeExistingInjuryButton;
  private JButton removeSelectedInjuryButton;
  private JButton generateInjuryButton;
  private JButton newCharacterButton;

  public static void main(String[] args) {
    JFrame mainWindow = new JFrame("DnD Injury Generator");
    mainWindow.setContentPane(new InjuryGenerator().mainFrame);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.pack();
    mainWindow.setResizable(false);
    mainWindow.setVisible(true);
  }

  public InjuryGenerator() {
    newCharacterButton.addActionListener(e -> addNewCharacter());

    generateInjuryButton.addActionListener(e -> generateInjury());

    generateAndAddInjuryButton.addActionListener(e -> generateAndAddInjury());

    describeExistingInjuryButton.addActionListener(e -> {
      Injury injury = (Injury) existingInjuriesSelector.getSelectedItem();
      if (injury != null) {
        setOutputAreaText(injury.getDescription());
      }
    });

    removeSelectedInjuryButton.addActionListener(e -> {
      Injury injury = (Injury) existingInjuriesSelector.getSelectedItem();
      if (injury != null) {
        removeInjuryFromCharacter(injury);
      }
    });

    describeInjuryButton.addActionListener(e -> {
      InjuryType injury = (InjuryType) injuryDescriptionSelector.getSelectedItem();
      if (injury != null) {
        setOutputAreaText(injury.getDescriptionFormatted());
      }
    });

    generateRollBox.addActionListener(e -> rollValueField.setEnabled(!isRollBoxSelected()));

    loadCharacterButton.addActionListener(e -> doLoadCharacter());

    saveCharacterButton.addActionListener(e -> doSaveCharacter());

    characterSelector.addActionListener(e -> characterSelected());

    initComponents();
  }

  private void initComponents() {
    Arrays.asList(Injury.DamageType.values()).forEach(item -> damageTypeSelector.addItem(item));
    Arrays.asList(InjuryType.values()).forEach(item -> {
      if (!item.equals(InjuryType.DEAD) && !item.equals(InjuryType.INVALID_INJURY))
      injuryDescriptionSelector.addItem(item);
    });
  }

  @NotNull
  Injury getInjuryFromSelectedCharacter() throws IllegalArgumentException {
    String invalidValueText = validateIntegerInputs();
    if (!invalidValueText.isEmpty()) {
      throw new IllegalArgumentException(invalidValueText + "Please enter an integer.");
    }
    Character character = getSelectedCharacter();
    if (character == null) {
      throw new IllegalArgumentException("Create new character before creating or removing injuries.");
    }
    int roll;
    if (isRollBoxSelected()) {
      Random rnd = getRandom();
      roll = rnd.nextInt(100);
    } else {
      roll = Integer.parseInt(getRollText());
    }
    Injury.DamageType damageType = getDamageType();
    return character.generateInjury(Integer.parseInt(getSpilloverText()), roll, damageType);
  }

  void removeInjuryFromCharacter(Injury injury) {
    Character character = getSelectedCharacter();
    if (character == null) {
      throw new IllegalArgumentException("Create new character before creating or removing injuries.");
    }
    character.removeInjury(injury);
    existingInjuriesSelector.removeItem(injury);
  }

  String validateIntegerInputs() {
    String errorText = "";
    if (isInvalidNumberInput(getMaxHealthText())) {
      errorText = errorText.concat("Invalid value specified for max health.\n");
    }
    if (isInvalidNumberInput(getSpilloverText())) {
      errorText = errorText.concat("Invalid value specified for spillover.\n");
    }
    if (!isRollBoxSelected() && isInvalidNumberInput(getRollText())) {
      errorText = errorText.concat("Invalid value specified for roll.\n");
    }
    return errorText;
  }

  boolean isInvalidNumberInput(String rollText) {
    if (rollText == null || rollText.isEmpty()) {
      return true;
    }
    try {
      Integer.parseInt(rollText);
    } catch (NumberFormatException ex) {
      return true;
    }
    return false;
  }

  void generateInjury() {
    Injury injury;
    try {
      injury = getInjuryFromSelectedCharacter();
    } catch (IllegalArgumentException ex) {
      setOutputAreaText(ex.getMessage());
      return;
    }
    setOutputAreaText(injury.getDescription());
  }

  void generateAndAddInjury() {
    Injury injury;
    try {
      injury = getInjuryFromSelectedCharacter();
    } catch (IllegalArgumentException ex) {
      setOutputAreaText(ex.getMessage());
      return;
    }
    Character character = Objects.requireNonNull(getSelectedCharacter());
    if (!injury.getInjuryType().equals(InjuryType.UNHARMED)) {
      character.addInjury(injury);
      addItemToSelector(injury);
      existingInjuriesSelector.setSelectedItem(injury);
    }
    setOutputAreaText(injury.getDescription());
  }

  void addNewCharacter() {
    String errorText = "";
    if (isInvalidNumberInput(getMaxHealthText())) {
      errorText = errorText.concat("Invalid value specified for max health.\n");
    }
    String name = getCharacterNameText();
    if (name == null || name.isEmpty()) {
      errorText = errorText.concat("Please enter a character name.\n");
    }
    if (!errorText.isEmpty()) {
      setOutputAreaText(errorText);
      return;
    }
    Character character = createCharacter();
    if (!selectorContainsItem(character)) {
      addItemToSelector(character);
      characterSelector.setSelectedItem(character);
    }
  }

  void doSaveCharacter() {
    final JFileChooser chooser = getFileChooser();
    chooser.setFileFilter(new DnDFileFilter());
    int returnVal = chooser.showSaveDialog(this);
    if (returnVal == APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      if (!file.getName().endsWith("." + FILE_EXTENSION)) {
        file = new File(file.getPath() + "." + FILE_EXTENSION);
      }
      Character characterToSave = getSelectedCharacter();
      if (characterToSave == null) {
        setOutputAreaText("Create new character before saving.");
        return;
      }
      boolean success = getSaveFileIO().saveCharacter(characterToSave, file);
      if (!success) {
        setOutputAreaText("Character file could not be saved.");
      }
      if (!selectorContainsItem(characterToSave)) {
        addItemToSelector(characterToSave);
        characterSelector.setSelectedItem(characterToSave);
      }
    }
  }

  void doLoadCharacter() {
    final JFileChooser chooser = getFileChooser();
    chooser.setFileFilter(new DnDFileFilter());
    int returnVal = chooser.showDialog(this, "Load");
    if (returnVal == APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      Character loadedCharacter = getSaveFileIO().loadCharacter(file);
      if (!selectorContainsItem(loadedCharacter)) {
        addItemToSelector(loadedCharacter);
      }
      characterSelector.setSelectedItem(loadedCharacter);
    }
  }

  void characterSelected() {
    Character character = Objects.requireNonNull(getSelectedCharacter());
    populateCharacterFields(character.getName(), character.getMaxHP(), character.getExistingInjuries());
  }

  void populateCharacterFields(String characterName, int maxHP, List<Injury> existingInjuries) {
    characterNameField.setText(characterName);
    maxHealthField.setText(String.valueOf(maxHP));
    existingInjuriesSelector.removeAllItems();
    existingInjuries.forEach(injury -> {
      if (!selectorContainsItem(injury)) {
        addItemToSelector(injury);
      }
    });
  }

  // Test helper methods
  @NotNull
  Character createCharacter() {
    String name = characterNameField.getText();
    return new Character(name, Integer.parseInt(getMaxHealthText()), new ArrayList<>());
  }

  @Nullable
  Character getSelectedCharacter() {
    return (Character) characterSelector.getSelectedItem();
  }

  @NotNull
  Random getRandom() {
    return new Random(System.nanoTime());
  }

  @NotNull
  Injury.DamageType getDamageType() {
    return Objects.requireNonNull((Injury.DamageType) damageTypeSelector.getSelectedItem());
  }

  String getMaxHealthText() {
    return maxHealthField.getText();
  }

  String getSpilloverText() {
    return spilloverField.getText();
  }

  String getRollText() {
    return rollValueField.getText();
  }

  String getCharacterNameText() {
    return characterNameField.getText();
  }

  boolean isRollBoxSelected() {
    return generateRollBox.isSelected();
  }

  void setOutputAreaText(String text) {
    outputArea.setText(text);
  }

  boolean selectorContainsItem(Object item) {
    boolean contains = false;
    if (item instanceof Character) {
      contains =  ((DefaultComboBoxModel<Character>) characterSelector.getModel()).getIndexOf(item) != -1;
    } else if (item instanceof Injury) {
      contains =  ((DefaultComboBoxModel<Injury>) existingInjuriesSelector.getModel()).getIndexOf(item) != -1;
    }
    return contains;
  }

  void addItemToSelector(Object item) {
    if (item instanceof Character) {
      characterSelector.addItem((Character) item);
    } else if (item instanceof Injury) {
      existingInjuriesSelector.addItem((Injury) item);
    }
  }

  @NotNull JFileChooser getFileChooser() {
    return new JFileChooser();
  }

  @NotNull SaveFileIO getSaveFileIO() {
    return new SaveFileIO();
  }

  private static class DnDFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
      return f.getName().endsWith(FILE_EXTENSION);
    }

    @Override
    public String getDescription() {
      return "Injury Calculator save files (*.dnd)";
    }
  }
}
