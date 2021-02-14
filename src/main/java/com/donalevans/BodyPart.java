package com.donalevans;

import java.io.Serializable;
import java.util.Objects;

public class BodyPart implements Serializable {

  private static final long serialVersionUID = -6768894027811455681L;

  public enum BodyZone {
    HEAD, UPPER_TORSO, LOWER_TORSO, LEFT_ARM, RIGHT_ARM, LEFT_LEG, RIGHT_LEG
  }

  private String name;

  public BodyPart() {}

  public BodyPart(Injury.Direction direction) {

  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BodyPart bodyPart = (BodyPart) o;
    return Objects.equals(name, bodyPart.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
