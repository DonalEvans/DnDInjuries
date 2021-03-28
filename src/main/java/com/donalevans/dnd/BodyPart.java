package com.donalevans.dnd;

import java.io.Serializable;
import java.util.Objects;
import java.util.Random;

import static com.donalevans.dnd.constants.BodyZoneMaps.MAX_ROLL;
import static com.donalevans.dnd.constants.BodyZoneMaps.noDirection;

public class BodyPart implements Serializable {

  private static final long serialVersionUID = -6768894027811455681L;

  public enum BodyZone {
    HEAD,
    UPPER_TORSO_FRONT,
    UPPER_TORSO_BACK,
    LOWER_TORSO_FRONT,
    LOWER_TORSO_BACK,
    LEFT_ARM,
    RIGHT_ARM,
    LEFT_LEG,
    RIGHT_LEG
  }

  private String name;

  public BodyPart() {}

  public BodyPart(Injury.Direction direction) {
    Random rnd = new Random();
    int roll = rnd.nextInt(MAX_ROLL);
    BodyZone zone;
    switch (direction) {
      case ABOVE:
        // Head -> upper torso front/back -> left/right arm -> lower torso front/back -> left/right leg
        break;
      case ABOVE_LEFT:
        // Head -> upper torso front/back -> left arm -> lower torso front/back -> right arm -> left leg -> right leg
        break;
      case ABOVE_RIGHT:
        // Head -> upper torso front/back -> right arm -> lower torso front/back -> left arm -> right leg -> left leg
        break;
      case BELOW:
        // left/right leg -> lower torso front/back -> left/right arm -> upper torso front/back -> head
        break;
      case BELOW_LEFT:
        // left leg -> lower torso front/back -> left arm -> right leg -> upper torso front/back -> right arm -> head
        break;
      case BELOW_RIGHT:
        // right leg -> lower torso front/back -> right arm -> left leg -> upper torso front/back -> left arm -> head
        break;
      case FRONT:
        // upper/lower torso front -> left/right arm -> head -> left/right leg -> upper/lower torso back
        break;
      case FRONT_LEFT:
        // upper/lower torso front -> left arm -> head -> left leg -> upper/lower torso back -> right arm -> right leg
        break;
      case FRONT_RIGHT:
        // upper/lower torso front -> right arm -> head -> right leg -> upper/lower torso back -> left arm -> left leg
        break;
      case BEHIND:
        // upper/lower torso back -> left/right arm -> head -> left/right leg -> upper/lower torso front
        break;
      case BEHIND_LEFT:
        // upper/lower torso back -> left arm -> head -> left leg -> upper/lower torso front -> right arm -> right leg
        break;
      case BEHIND_RIGHT:
        // upper/lower torso back -> right arm -> head -> right leg -> upper/lower torso front -> left arm -> left leg
        break;
      case NONE:
        // All equal
        zone = noDirection.floorEntry(roll).getValue();
        break;
      default:
        // should not be possible
        throw new IllegalArgumentException("Invalid direction specified: " + direction);
    }
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
