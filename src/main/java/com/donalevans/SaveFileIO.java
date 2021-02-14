package com.donalevans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveFileIO {
  public static final String FILE_EXTENSION = "dnd";

  public Character loadCharacter(File saveFile) {
    try (FileInputStream fis = new FileInputStream(saveFile);
    ObjectInputStream ois = new ObjectInputStream(fis)) {
      return (Character) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return new Character();
  }

  public boolean saveCharacter(Character character, File saveFile) {
    try (FileOutputStream fos = new FileOutputStream(saveFile);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(character);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
