package com.donalevans.dnd;

import com.donalevans.dnd.Character;
import com.donalevans.dnd.Injury;
import com.donalevans.dnd.SaveFileIO;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static com.donalevans.dnd.SaveFileIO.FILE_EXTENSION;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SaveFileIOTest {
  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void saveCharacterAndLoadCharacterWritesAndReadsFile() throws IOException {
    SaveFileIO saveFileIO = new SaveFileIO();
    final String characterName = "Character Name";
    final int maxHP = 100;

    final int severity1 = 12;
    final Injury.DamageType damageType1 = Injury.DamageType.ACID;
    final Injury.Direction direction1 = Injury.Direction.NONE;
    final Injury injury1 = new Injury(severity1, damageType1, direction1);

    final int severity2 = 24;
    final Injury.DamageType damageType2 = Injury.DamageType.COLD;
    final Injury.Direction direction2 = Injury.Direction.FRONT_LEFT;
    final Injury injury2 = new Injury(severity2, damageType2, direction2);

    final Injury injury3 = new Injury.Dead();

    final List<Injury> existingInjuries = new ArrayList<>();
    existingInjuries.add(injury1);
    existingInjuries.add(injury2);
    existingInjuries.add(injury3);

    final Character testCharacter = new Character(characterName,maxHP, existingInjuries);
    File saveFile = folder.newFile("test" + FILE_EXTENSION);

    assertTrue(saveFileIO.saveCharacter(testCharacter, saveFile));

    Character loadedCharacter = saveFileIO.loadCharacter(saveFile);

    assertEquals(loadedCharacter, testCharacter);
  }
}
