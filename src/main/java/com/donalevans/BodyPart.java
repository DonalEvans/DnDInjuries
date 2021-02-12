package com.donalevans;

public class BodyPart {

  public enum BodyZone {
    HEAD, TORSO, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG
  }

  private String name = "none";

  public BodyPart() {}

  public BodyPart(Injury.Direction direction) {

  }
}
