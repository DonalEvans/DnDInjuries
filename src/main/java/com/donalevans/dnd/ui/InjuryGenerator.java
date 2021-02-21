package com.donalevans.dnd.ui;

import com.donalevans.dnd.Character;
import com.donalevans.dnd.Injury;
import com.donalevans.dnd.constants.InjuryType;
import com.donalevans.dnd.SaveFileIO;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.donalevans.dnd.SaveFileIO.FILE_EXTENSION;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class InjuryGenerator extends JFrame {

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
      contains = ((DefaultComboBoxModel<Character>) characterSelector.getModel()).getIndexOf(item) != -1;
    } else if (item instanceof Injury) {
      contains = ((DefaultComboBoxModel<Injury>) existingInjuriesSelector.getModel()).getIndexOf(item) != -1;
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

  {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
    $$$setupUI$$$();
  }

  /**
   * Method generated by IntelliJ IDEA GUI Designer
   * >>> IMPORTANT!! <<<
   * DO NOT edit this method OR call it in your code!
   *
   * @noinspection ALL
   */
  private void $$$setupUI$$$() {
    mainFrame = new JPanel();
    mainFrame.setLayout(new GridBagLayout());
    mainFrame.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
    final JPanel spacer1 = new JPanel();
    GridBagConstraints gbc;
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 12;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.VERTICAL;
    mainFrame.add(spacer1, gbc);
    injuryDescriptionSelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 10;
    gbc.gridwidth = 4;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(injuryDescriptionSelector, gbc);
    characterNameField = new JTextField();
    characterNameField.setMinimumSize(new Dimension(64, 30));
    characterNameField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(characterNameField, gbc);
    existingInjuriesSelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.gridwidth = 4;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(existingInjuriesSelector, gbc);
    final JPanel spacer2 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 5;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer2, gbc);
    loadCharacterButton = new JButton();
    loadCharacterButton.setText("Load Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(loadCharacterButton, gbc);
    saveCharacterButton = new JButton();
    saveCharacterButton.setText("Save Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 2;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(saveCharacterButton, gbc);
    final JLabel label1 = new JLabel();
    label1.setText("Existing Injuries");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.gridwidth = 4;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label1, gbc);
    final JLabel label2 = new JLabel();
    label2.setText("Character Name");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label2, gbc);
    generateAndAddInjuryButton = new JButton();
    generateAndAddInjuryButton.setText("Generate Injury and Add To Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 8;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(generateAndAddInjuryButton, gbc);
    outputArea = new JTextPane();
    outputArea.setEditable(false);
    outputArea.setPreferredSize(new Dimension(450, 120));
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 11;
    gbc.gridwidth = 10;
    gbc.fill = GridBagConstraints.BOTH;
    mainFrame.add(outputArea, gbc);
    final JPanel spacer3 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 0;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.VERTICAL;
    mainFrame.add(spacer3, gbc);
    final JLabel label3 = new JLabel();
    label3.setRequestFocusEnabled(true);
    label3.setText("All Possible Injuries");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 9;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label3, gbc);
    characterSelector = new JComboBox();
    characterSelector.setEditable(false);
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 2;
    gbc.gridwidth = 5;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(characterSelector, gbc);
    final JPanel spacer4 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer4, gbc);
    final JPanel spacer5 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 11;
    gbc.gridy = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer5, gbc);
    final JPanel spacer6 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 7;
    gbc.gridy = 4;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer6, gbc);
    spilloverField = new JTextField();
    spilloverField.setMinimumSize(new Dimension(64, 30));
    spilloverField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spilloverField, gbc);
    final JLabel label4 = new JLabel();
    label4.setText("Spillover");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label4, gbc);
    rollValueField = new JTextField();
    rollValueField.setMinimumSize(new Dimension(64, 30));
    rollValueField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(rollValueField, gbc);
    final JLabel label5 = new JLabel();
    label5.setText("Roll");
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label5, gbc);
    final JPanel spacer7 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 3;
    gbc.weightx = 0.1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer7, gbc);
    describeInjuryButton = new JButton();
    describeInjuryButton.setText("Describe Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 10;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(describeInjuryButton, gbc);
    removeSelectedInjuryButton = new JButton();
    removeSelectedInjuryButton.setText("Remove Selected Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 8;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(removeSelectedInjuryButton, gbc);
    describeExistingInjuryButton = new JButton();
    describeExistingInjuryButton.setText("Describe Selected Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 8;
    gbc.gridwidth = 3;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(describeExistingInjuryButton, gbc);
    final JLabel label6 = new JLabel();
    label6.setText("Damage Type");
    gbc = new GridBagConstraints();
    gbc.gridx = 10;
    gbc.gridy = 3;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label6, gbc);
    damageTypeSelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 10;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(damageTypeSelector, gbc);
    final JPanel spacer8 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 9;
    gbc.gridy = 4;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer8, gbc);
    generateRollBox = new JCheckBox();
    generateRollBox.setSelected(false);
    generateRollBox.setText("Auto-generate Roll");
    gbc = new GridBagConstraints();
    gbc.gridx = 7;
    gbc.gridy = 6;
    gbc.gridwidth = 4;
    mainFrame.add(generateRollBox, gbc);
    final JPanel spacer9 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridy = 7;
    gbc.fill = GridBagConstraints.VERTICAL;
    mainFrame.add(spacer9, gbc);
    generateInjuryButton = new JButton();
    generateInjuryButton.setText("Generate Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 9;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(generateInjuryButton, gbc);
    final JLabel label7 = new JLabel();
    label7.setText("Current Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label7, gbc);
    maxHealthField = new JTextField();
    maxHealthField.setEditable(true);
    maxHealthField.setMinimumSize(new Dimension(64, 30));
    maxHealthField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(maxHealthField, gbc);
    final JLabel label8 = new JLabel();
    label8.setText("Max Health");
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 3;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label8, gbc);
    newCharacterButton = new JButton();
    newCharacterButton.setText("New Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridy = 4;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(newCharacterButton, gbc);
  }

  /**
   * @noinspection
   */
  public JComponent $$$getRootComponent$$$() {
    return mainFrame;
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
