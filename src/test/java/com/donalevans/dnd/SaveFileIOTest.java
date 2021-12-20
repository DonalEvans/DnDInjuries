package com.donalevans.dnd;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static com.donalevans.dnd.SaveFileIO.PARTY_FILE_EXTENSION;
import static org.assertj.core.api.Assertions.assertThat;

public class SaveFileIOTest {
  @Rule public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void savePartyAndLoadPartyWritesAndReadsFile() throws IOException, ClassNotFoundException {
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

    final Character testCharacter = new Character(characterName, maxHP, existingInjuries);
    final String partyName = "Droop troop";
    final Party testParty = new Party(partyName);
    testParty.add(testCharacter);
    File saveFile = folder.newFile("test" + PARTY_FILE_EXTENSION);

    assertThat(saveFileIO.saveParty(testParty, saveFile)).isTrue();

    Party loadedParty = saveFileIO.loadParty(saveFile);

    assertThat(testParty).isEqualTo(loadedParty);
  }
}
