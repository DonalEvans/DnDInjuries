package com.donalevans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InjuryGenerator extends JFrame{

  private JTextPane outputArea;
  private JButton generateInjuryButton;
  private JTextField spilloverField;
  private JTextField maxHealthField;
  private JComboBox<Injury.DamageType> damageTypeSelector;
  private JPanel mainFrame;
  private JTextField rollValue;
  private JCheckBox generateRollBox;
  private JComboBox<InjuryType> injuryDescriptionSelector;
  private JButton describeInjuryButton;

  private List<Character> characters = new ArrayList<>();

  public static void main(String[] args) {
    JFrame mainWindow = new JFrame("InjuryGenerator");
    mainWindow.setContentPane(new InjuryGenerator().mainFrame);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.pack();
    mainWindow.setResizable(false);
    mainWindow.setVisible(true);
  }

  public InjuryGenerator() {
    generateInjuryButton.addActionListener(actionEvent -> generateInjury());

    generateRollBox.addActionListener(e -> rollValue.setEnabled(!isRollBoxSelected()));

    describeInjuryButton.addActionListener(e -> {
      InjuryType injury = (InjuryType) injuryDescriptionSelector.getSelectedItem();
      if (injury != null) {
        setOutputAreaText(injury.getDescriptionFormatted());
      }
    });
    initComponents();
  }

  private void initComponents() {
    Arrays.asList(Injury.DamageType.values()).forEach(item -> damageTypeSelector.addItem(item));
    Arrays.asList(InjuryType.values()).forEach(item -> {
      if (!item.equals(InjuryType.DEAD) && !item.equals(InjuryType.INVALID_INJURY))
      injuryDescriptionSelector.addItem(item);
    });
  }

  void generateInjury() {
    String invalidValueText = validateIntegerInputs();
    if (!invalidValueText.isEmpty()) {
      setOutputAreaText(invalidValueText + "Please enter an integer.");
      return;
    }
    Character character = createCharacter();
    int roll;
    if (isRollBoxSelected()) {
      Random rnd = getRandom();
      roll = rnd.nextInt(100);
    } else {
      roll = Integer.parseInt(getRollText());
    }
    Injury.DamageType damageType = getDamageType();
    Injury injury = character.generateInjury(Integer.parseInt(getSpilloverText()), roll, damageType);
    setOutputAreaText(injury.getDescription());
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
    if (rollText == null) {
      return true;
    }
    try {
      Integer.parseInt(rollText);
    } catch (NumberFormatException ex) {
      return true;
    }
    return false;
  }
  
  // Test helper methods

  @NotNull
  Random getRandom() {
    return new Random(System.nanoTime());
  }

  @NotNull
  Character createCharacter() {
    return new Character("", Integer.parseInt(getMaxHealthText()));
  }

  @Nullable
  Injury.DamageType getDamageType() {
    return (Injury.DamageType) damageTypeSelector.getSelectedItem();
  }

  String getMaxHealthText() {
    return maxHealthField.getText();
  }

  String getSpilloverText() {
    return spilloverField.getText();
  }

  String getRollText() {
    return rollValue.getText();
  }

  boolean isRollBoxSelected() {
    return generateRollBox.isSelected();
  }

  void setOutputAreaText(String text) {
    outputArea.setText(text);
  }
}
