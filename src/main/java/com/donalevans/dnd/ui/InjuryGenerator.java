package com.donalevans.dnd.ui;

import com.donalevans.dnd.Character;
import com.donalevans.dnd.Injury;
import com.donalevans.dnd.Party;
import com.donalevans.dnd.SaveFileIO;
import com.donalevans.dnd.constants.InjuryType;
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

import static com.donalevans.dnd.SaveFileIO.PARTY_FILE_EXTENSION;
import static javax.swing.JFileChooser.APPROVE_OPTION;

public class InjuryGenerator extends JFrame {

  private JPanel mainFrame;
  private JTextField partyNameField;
  private JButton createPartyButton;
  private JButton savePartyButton;
  private JButton loadPartyButton;
  private JComboBox<Party> partySelector;
  private JTextField characterNameField;
  private JTextField maxHealthField;
  private JButton addCharacterToPartyButton;
  private JButton removeCharacterFromPartyButton;
  private JButton updateCharacterButton;
  private JComboBox<Character> characterSelector;
  private JComboBox<Injury> existingInjuriesSelector;
  private JButton describeExistingInjuryButton;
  private JButton removeSelectedInjuryButton;
  private JTextField spilloverField;
  private JTextField rollValueField;
  private JComboBox<Injury.DamageType> damageTypeSelector;
  private JCheckBox generateRollBox;
  private JButton generateAndAddInjuryButton;
  private JButton generateInjuryButton;
  private JComboBox<InjuryType> injuryDescriptionSelector;
  private JButton describeInjuryButton;
  private JButton addInjuryButton;
  private JTextPane outputArea;

  private File previousSaveLoadLocation;

  public static void main(String[] args) {
    JFrame mainWindow = new JFrame("DnD Injury Generator");
    mainWindow.setContentPane(new InjuryGenerator().mainFrame);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.pack();
    mainWindow.setResizable(false);
    mainWindow.setVisible(true);
  }

  public InjuryGenerator() {
    createPartyButton.addActionListener(e -> createParty());

    loadPartyButton.addActionListener(e -> doLoadParty());

    savePartyButton.addActionListener(e -> doSaveParty());

    partySelector.addActionListener(e -> partyUpdated());

    addCharacterToPartyButton.addActionListener(e -> addCharacterToParty());

    removeCharacterFromPartyButton.addActionListener(e -> removeCharacterFromParty());

    updateCharacterButton.addActionListener(e -> characterUpdated());

    characterSelector.addActionListener(e -> characterSelected());

    describeExistingInjuryButton.addActionListener(
            e -> {
              Injury injury = getSelectedExistingInjury();
              if (injury != null) {
                setOutputAreaText(injury.getDescription());
              }
            });

    removeSelectedInjuryButton.addActionListener(e -> removeSelectedInjury());

    generateRollBox.addActionListener(e -> rollValueField.setEnabled(!isRollBoxSelected()));

    generateAndAddInjuryButton.addActionListener(e -> generateAndAddInjury());

    generateInjuryButton.addActionListener(e -> generateInjury());

    describeInjuryButton.addActionListener(
            e -> {
              InjuryType injury = (InjuryType) injuryDescriptionSelector.getSelectedItem();
              if (injury != null) {
                setOutputAreaText(injury.getDescriptionFormatted());
              }
            });

    addInjuryButton.addActionListener(e -> addSelectedInjuryToCharacter());

    initComponents();
  }

  private void initComponents() {
    Arrays.asList(Injury.DamageType.values()).forEach(item -> damageTypeSelector.addItem(item));
    Arrays.asList(InjuryType.values())
            .forEach(
                    item -> {
                      if (!item.equals(InjuryType.DEAD)
                              && !item.equals(InjuryType.INVALID_INJURY)
                              && !item.equals(InjuryType.UNHARMED)) injuryDescriptionSelector.addItem(item);
                    });
  }

  void createParty() {
    String name = getPartyNameText();
    if (name == null || name.isEmpty()) {
      setOutputAreaText("Please enter a party name.");
      return;
    }
    Party party = new Party(name);
    if (!partySelectorContainsParty(party)) {
      addItemToSelector(party);
      partySelector.setSelectedItem(party);
    }
  }

  void partyUpdated() {
    Party party = Objects.requireNonNull(getSelectedParty());
    characterSelector.removeAllItems();
    party.forEach(this::addItemToSelector);
    partySelector.repaint();
    characterUpdated();
  }

  @NotNull
  Injury getInjuryFromSelectedCharacter() throws IllegalArgumentException {
    String invalidValueText = validateIntegerInputs();
    if (!invalidValueText.isEmpty()) {
      throw new IllegalArgumentException(invalidValueText + "Please enter an integer.");
    }
    Character character = getSelectedCharacter();
    if (character == null) {
      throw new IllegalArgumentException(
              "Create new character before creating or removing injuries.");
    }
    int roll;
    if (isRollBoxSelected()) {
      Random rnd = getRandom();
      roll = rnd.nextInt(100);
      setRollText(String.valueOf(roll));
    } else {
      roll = Integer.parseInt(getRollText());
    }
    Injury.DamageType damageType = getDamageType();
    return character.generateInjury(Integer.parseInt(getSpilloverText()), roll, damageType);
  }

  void addSelectedInjuryToCharacter() {
    Character character = getSelectedCharacter();
    if (character == null) {
      throw new IllegalArgumentException(
              "Add character to party before adding or removing injuries.");
    }
    Injury injury = getSelectedInjury();
    character.addInjury(injury);
    characterUpdated();
    addItemToSelector(injury);
    existingInjuriesSelector.setSelectedItem(injury);
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
      characterUpdated();
      addItemToSelector(injury);
      existingInjuriesSelector.setSelectedItem(injury);
    }
    setOutputAreaText(injury.getDescription());
  }

  void addCharacterToParty() {
    Party currentParty = getSelectedParty();
    if (currentParty == null) {
      setOutputAreaText("Create or load Party before adding a character.");
      return;
    }

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
    currentParty.add(character);
    partyUpdated();
  }

  void removeCharacterFromParty() {
    Party currentParty = getSelectedParty();
    if (currentParty == null) {
      return;
    }
    Character currentCharacter = getSelectedCharacter();
    if (currentCharacter == null) {
      return;
    }
    currentParty.remove(currentCharacter);
    partyUpdated();
  }

  void doSaveParty() {
    final JFileChooser chooser = getFileChooser();
    chooser.setFileFilter(new DnDFileFilter());
    int returnVal = chooser.showSaveDialog(this);
    if (returnVal == APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      if (!file.getName().endsWith("." + PARTY_FILE_EXTENSION)) {
        file = new File(file.getPath() + "." + PARTY_FILE_EXTENSION);
      }
      Party partyToSave = getSelectedParty();
      if (partyToSave == null) {
        setOutputAreaText("Create new party before saving.");
        return;
      }
      boolean success = getSaveFileIO().saveParty(partyToSave, file);
      if (!success) {
        setOutputAreaText("Party file could not be saved.");
      }
      previousSaveLoadLocation = file.getParentFile();
      if (!partySelectorContainsParty(partyToSave)) {
        addItemToSelector(partyToSave);
        partySelector.setSelectedItem(partyToSave);
      }
    }
  }

  void doLoadParty() {
    final JFileChooser chooser = getFileChooser();
    chooser.setFileFilter(new DnDFileFilter());
    int returnVal = chooser.showDialog(this, "Load");
    if (returnVal == APPROVE_OPTION) {
      File file = chooser.getSelectedFile();
      if (!file.getName().endsWith("." + PARTY_FILE_EXTENSION)) {
        setOutputAreaText("Invalid file selected.");
        return;
      }
      Party loadedParty;
      try {
        loadedParty = getSaveFileIO().loadParty(file);
      } catch (Exception ex) {
        setOutputAreaText("Selected file could not be loaded.");
        return;
      }
      previousSaveLoadLocation = file.getParentFile();
      if (!partySelectorContainsParty(loadedParty)) {
        addItemToSelector(loadedParty);
      }
      partySelector.setSelectedItem(loadedParty);
      if (!loadedParty.isEmpty()) {
        characterSelector.setSelectedItem(loadedParty.iterator().next());
      }
    }
  }

  void characterSelected() {
    Character character = getSelectedCharacter();
    if (character == null) {
      return;
    }
    populateCharacterFields(
            character.getName(), character.getMaxHP(), character.getExistingInjuries());
  }

  void populateCharacterFields(String characterName, int maxHP, List<Injury> existingInjuries) {
    characterNameField.setText(characterName);
    maxHealthField.setText(String.valueOf(maxHP));
    existingInjuriesSelector.removeAllItems();
    existingInjuries.forEach(this::addItemToSelector);
  }

  void characterUpdated() {
    Character character = getSelectedCharacter();
    if (character == null) {
      return;
    }

    Party party = getSelectedParty();
    if (party == null) {
      return;
    }

    character.setMaxHP(Integer.parseInt(getMaxHealthText()));

    character.setName(getCharacterNameText());

    characterSelector.repaint();
  }

  void removeSelectedInjury() {
    Injury injury = getSelectedExistingInjury();
    if (injury != null) {
      Character character = getSelectedCharacter();
      if (character == null) {
        setOutputAreaText("Create new character before creating or removing injuries.");
        return;
      }
      character.removeInjury(injury);
      characterUpdated();
      existingInjuriesSelector.removeItem(injury);
      if (existingInjuriesSelector.getItemCount() > 0) {
        existingInjuriesSelector.setSelectedIndex(0);
      }
    }
  }

  // Test helper methods
  @NotNull
  Character createCharacter() {
    String name = characterNameField.getText();
    return new Character(name, Integer.parseInt(getMaxHealthText()), new ArrayList<>());
  }

  @Nullable
  Party getSelectedParty() {
    return (Party) partySelector.getSelectedItem();
  }

  @Nullable
  Character getSelectedCharacter() {
    return (Character) characterSelector.getSelectedItem();
  }

  @Nullable
  Injury getSelectedInjury() {
    return new Injury((InjuryType) injuryDescriptionSelector.getSelectedItem());
  }

  @Nullable
  Injury getSelectedExistingInjury() {
    return (Injury) existingInjuriesSelector.getSelectedItem();
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

  void setRollText(String value) {
    rollValueField.setText(value);
  }

  String getCharacterNameText() {
    return characterNameField.getText();
  }

  String getPartyNameText() {
    return partyNameField.getText();
  }

  boolean isRollBoxSelected() {
    return generateRollBox.isSelected();
  }

  void setOutputAreaText(String text) {
    outputArea.setText(text);
  }

  File getPreviousSaveLoadLocation() {
    return previousSaveLoadLocation;
  }

  boolean partySelectorContainsParty(Party party) {
    return ((DefaultComboBoxModel<Party>) partySelector.getModel()).getIndexOf(party) != -1;
  }

  boolean characterSelectorContainsCharacter(Character character) {
    return ((DefaultComboBoxModel<Character>) characterSelector.getModel()).getIndexOf(character)
            != -1;
  }

  boolean existingInjuriesSelectorContainsInjury(Injury injury) {
    return ((DefaultComboBoxModel<Injury>) existingInjuriesSelector.getModel()).getIndexOf(injury)
            != -1;
  }

  void addItemToSelector(Object item) {
    if (item instanceof Party) {
      partySelector.addItem((Party) item);
    } else if (item instanceof Character) {
      characterSelector.addItem((Character) item);
    } else if (item instanceof Injury) {
      existingInjuriesSelector.addItem((Injury) item);
    }
  }

  @NotNull
  JFileChooser getFileChooser() {
    return new JFileChooser(previousSaveLoadLocation);
  }

  @NotNull
  SaveFileIO getSaveFileIO() {
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
    gbc.gridy = 13;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.VERTICAL;
    mainFrame.add(spacer1, gbc);
    injuryDescriptionSelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 11;
    gbc.gridwidth = 3;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(injuryDescriptionSelector, gbc);
    characterNameField = new JTextField();
    characterNameField.setMinimumSize(new Dimension(64, 30));
    characterNameField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 5;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(characterNameField, gbc);
    existingInjuriesSelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 8;
    gbc.gridwidth = 3;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(existingInjuriesSelector, gbc);
    final JLabel label1 = new JLabel();
    label1.setText("Existing Injuries");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 7;
    gbc.gridwidth = 6;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label1, gbc);
    final JLabel label2 = new JLabel();
    label2.setText("Character Name");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label2, gbc);
    outputArea = new JTextPane();
    outputArea.setEditable(false);
    outputArea.setPreferredSize(new Dimension(450, 120));
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 12;
    gbc.gridwidth = 12;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(5, 0, 0, 0);
    mainFrame.add(outputArea, gbc);
    final JPanel spacer2 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.VERTICAL;
    mainFrame.add(spacer2, gbc);
    final JPanel spacer3 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 7;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer3, gbc);
    final JPanel spacer4 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 13;
    gbc.gridy = 7;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer4, gbc);
    final JLabel label3 = new JLabel();
    label3.setRequestFocusEnabled(true);
    label3.setText("All Possible Injuries");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 10;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label3, gbc);
    partyNameField = new JTextField();
    partyNameField.setMinimumSize(new Dimension(64, 30));
    partyNameField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(partyNameField, gbc);
    final JSeparator separator1 = new JSeparator();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 9;
    gbc.gridwidth = 6;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(5, 0, 5, 0);
    mainFrame.add(separator1, gbc);
    final JSeparator separator2 = new JSeparator();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 3;
    gbc.gridwidth = 12;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(5, 0, 5, 0);
    mainFrame.add(separator2, gbc);
    final JLabel label4 = new JLabel();
    label4.setText("Current Party");
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label4, gbc);
    partySelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 2;
    gbc.gridwidth = 5;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(partySelector, gbc);
    final JLabel label5 = new JLabel();
    label5.setText("Party Name");
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 1;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label5, gbc);
    createPartyButton = new JButton();
    createPartyButton.setText("Create Party");
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 2;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(createPartyButton, gbc);
    maxHealthField = new JTextField();
    maxHealthField.setEditable(true);
    maxHealthField.setMaximumSize(new Dimension(50, 30));
    maxHealthField.setMinimumSize(new Dimension(50, 30));
    maxHealthField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(maxHealthField, gbc);
    spilloverField = new JTextField();
    spilloverField.setMinimumSize(new Dimension(64, 30));
    spilloverField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 8;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spilloverField, gbc);
    rollValueField = new JTextField();
    rollValueField.setMinimumSize(new Dimension(64, 30));
    rollValueField.setPreferredSize(new Dimension(64, 30));
    gbc = new GridBagConstraints();
    gbc.gridx = 10;
    gbc.gridy = 8;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(rollValueField, gbc);
    damageTypeSelector = new JComboBox();
    gbc = new GridBagConstraints();
    gbc.gridx = 12;
    gbc.gridy = 8;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(damageTypeSelector, gbc);
    final JPanel spacer5 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 11;
    gbc.gridy = 8;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer5, gbc);
    final JPanel spacer6 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 9;
    gbc.gridy = 8;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer6, gbc);
    final JLabel label6 = new JLabel();
    label6.setText("Spillover");
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 7;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label6, gbc);
    final JLabel label7 = new JLabel();
    label7.setText("Roll");
    gbc = new GridBagConstraints();
    gbc.gridx = 10;
    gbc.gridy = 7;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label7, gbc);
    final JLabel label8 = new JLabel();
    label8.setText("Damage Type");
    gbc = new GridBagConstraints();
    gbc.gridx = 12;
    gbc.gridy = 7;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label8, gbc);
    characterSelector = new JComboBox();
    characterSelector.setEditable(false);
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 5;
    gbc.gridwidth = 5;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(characterSelector, gbc);
    final JLabel label9 = new JLabel();
    label9.setText("Current Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label9, gbc);
    final JLabel label10 = new JLabel();
    label10.setText("Max Health");
    gbc = new GridBagConstraints();
    gbc.gridx = 2;
    gbc.gridy = 4;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(label10, gbc);
    addCharacterToPartyButton = new JButton();
    addCharacterToPartyButton.setActionCommand("Add Character To Party");
    addCharacterToPartyButton.setHorizontalTextPosition(0);
    addCharacterToPartyButton.setText("Add To Party");
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 5;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(addCharacterToPartyButton, gbc);
    savePartyButton = new JButton();
    savePartyButton.setText("Save Party");
    gbc = new GridBagConstraints();
    gbc.gridx = 5;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(savePartyButton, gbc);
    loadPartyButton = new JButton();
    loadPartyButton.setText("Load Party");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(loadPartyButton, gbc);
    describeInjuryButton = new JButton();
    describeInjuryButton.setText("Describe Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridy = 11;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(describeInjuryButton, gbc);
    addInjuryButton = new JButton();
    addInjuryButton.setText("Add Injury To Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 11;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(addInjuryButton, gbc);
    final JSeparator separator3 = new JSeparator();
    gbc = new GridBagConstraints();
    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.gridwidth = 12;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(5, 0, 5, 0);
    mainFrame.add(separator3, gbc);
    final JSeparator separator4 = new JSeparator();
    separator4.setOrientation(1);
    gbc = new GridBagConstraints();
    gbc.gridx = 7;
    gbc.gridy = 7;
    gbc.gridheight = 5;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(0, 5, 0, 5);
    mainFrame.add(separator4, gbc);
    final JSeparator separator5 = new JSeparator();
    separator5.setOrientation(1);
    gbc = new GridBagConstraints();
    gbc.gridx = 7;
    gbc.gridy = 4;
    gbc.gridheight = 2;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(0, 5, 0, 5);
    mainFrame.add(separator5, gbc);
    final JPanel spacer7 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 7;
    gbc.gridy = 1;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer7, gbc);
    final JSeparator separator6 = new JSeparator();
    separator6.setOrientation(1);
    gbc = new GridBagConstraints();
    gbc.gridx = 7;
    gbc.gridy = 2;
    gbc.fill = GridBagConstraints.BOTH;
    gbc.insets = new Insets(0, 5, 0, 5);
    mainFrame.add(separator6, gbc);
    final JPanel spacer8 = new JPanel();
    gbc = new GridBagConstraints();
    gbc.gridx = 3;
    gbc.gridy = 1;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(spacer8, gbc);
    generateAndAddInjuryButton = new JButton();
    generateAndAddInjuryButton.setHorizontalTextPosition(0);
    generateAndAddInjuryButton.setText("Generate Injury And Add To Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 10;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(generateAndAddInjuryButton, gbc);
    generateInjuryButton = new JButton();
    generateInjuryButton.setText("Generate Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 8;
    gbc.gridy = 11;
    gbc.gridwidth = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(generateInjuryButton, gbc);
    removeSelectedInjuryButton = new JButton();
    removeSelectedInjuryButton.setText("Remove Selected Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 8;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(removeSelectedInjuryButton, gbc);
    describeExistingInjuryButton = new JButton();
    describeExistingInjuryButton.setText("Describe Selected Injury");
    gbc = new GridBagConstraints();
    gbc.gridx = 4;
    gbc.gridy = 8;
    gbc.gridwidth = 2;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(describeExistingInjuryButton, gbc);
    generateRollBox = new JCheckBox();
    generateRollBox.setSelected(false);
    generateRollBox.setText("Auto-generate Roll");
    gbc = new GridBagConstraints();
    gbc.gridx = 10;
    gbc.gridy = 9;
    gbc.gridwidth = 4;
    gbc.anchor = GridBagConstraints.WEST;
    mainFrame.add(generateRollBox, gbc);
    removeCharacterFromPartyButton = new JButton();
    removeCharacterFromPartyButton.setText("Remove From Party");
    gbc = new GridBagConstraints();
    gbc.gridx = 5;
    gbc.gridy = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(removeCharacterFromPartyButton, gbc);
    updateCharacterButton = new JButton();
    updateCharacterButton.setText("Update Character");
    gbc = new GridBagConstraints();
    gbc.gridx = 6;
    gbc.gridy = 5;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    mainFrame.add(updateCharacterButton, gbc);
    label2.setLabelFor(characterNameField);
    label3.setLabelFor(injuryDescriptionSelector);
    label4.setLabelFor(partySelector);
    label5.setLabelFor(partyNameField);
    label6.setLabelFor(spilloverField);
    label7.setLabelFor(rollValueField);
    label8.setLabelFor(damageTypeSelector);
    label9.setLabelFor(characterSelector);
    label10.setLabelFor(maxHealthField);
  }

  /**
   * @noinspection ALL
   */
  public JComponent $$$getRootComponent$$$() {
    return mainFrame;
  }

  private static class DnDFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
      return f.getName().endsWith(PARTY_FILE_EXTENSION) || f.isDirectory();
    }

    @Override
    public String getDescription() {
      return "Injury Calculator save files (*.pty)";
    }
  }
}
