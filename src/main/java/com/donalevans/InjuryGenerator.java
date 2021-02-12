package com.donalevans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javax.swing.*;

public class InjuryGenerator extends JFrame{
  List<Character> characters = new ArrayList<>();

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

  public InjuryGenerator() {
    generateInjuryButton.addActionListener(
        actionEvent -> {
          Character character = new Character("", Integer.parseInt(maxHealthField.getText()));
          int roll;
          if (generateRollBox.isSelected()) {
            Random rnd = new Random(System.nanoTime());
            roll = rnd.nextInt(100);
          } else {
            roll = Integer.parseInt(rollValue.getText());
          }
          int spillover = Integer.parseInt(spilloverField.getText());
          Injury.DamageType damageType = (Injury.DamageType) damageTypeSelector.getSelectedItem();
          Injury injury = character.generateInjury(spillover, roll, damageType, null);
          outputArea.setText(injury.getDescription());
        });
    initComponents();

    generateRollBox.addActionListener(e -> rollValue.setEnabled(!generateRollBox.isSelected()));

    describeInjuryButton.addActionListener(e -> {
      InjuryType injury = (InjuryType) injuryDescriptionSelector.getSelectedItem();
      if (injury != null) {
        outputArea.setText(injury.getDescriptionFormatted());
      }
    });
  }

  private void initComponents() {
    Arrays.asList(Injury.DamageType.values()).forEach(item -> this.damageTypeSelector.addItem(item));
    Arrays.asList(InjuryType.values()).forEach(item -> this.injuryDescriptionSelector.addItem(item));
  }

  public static void main(String[] args) {
    JFrame mainWindow = new JFrame("InjuryGenerator");
    mainWindow.setContentPane(new InjuryGenerator().mainFrame);
    mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainWindow.pack();
    mainWindow.setResizable(false);
    mainWindow.setVisible(true);
  }
}
