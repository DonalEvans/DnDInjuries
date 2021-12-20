package com.donalevans.dnd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SaveFileIO {
  public static final String PARTY_FILE_EXTENSION = "pty";

  public Party loadParty(File saveFile) throws IOException, ClassNotFoundException {
    try (FileInputStream fis = new FileInputStream(saveFile);
    ObjectInputStream ois = new ObjectInputStream(fis)) {
      return (Party) ois.readObject();
    }
  }

  public boolean saveParty(Party party, File saveFile) {
    try (FileOutputStream fos = new FileOutputStream(saveFile);
         ObjectOutputStream oos = new ObjectOutputStream(fos)) {
      oos.writeObject(party);
    } catch (IOException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
