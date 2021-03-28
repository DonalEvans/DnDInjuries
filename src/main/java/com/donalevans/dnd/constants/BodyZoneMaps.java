package com.donalevans.dnd.constants;

import com.donalevans.dnd.BodyPart;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@SuppressWarnings("PointlessArithmeticExpression")
public class BodyZoneMaps {
  public static final int NUMBER_OF_BODY_ZONES = BodyPart.BodyZone.values().length;
  public static final int MAX_ROLL = NUMBER_OF_BODY_ZONES * 100;

  public static final TreeMap<Integer, BodyPart.BodyZone> noDirection = new TreeMap<>(getNoDirectionZones());

  private static Map<Integer, BodyPart.BodyZone> getNoDirectionZones() {
    Map<Integer, BodyPart.BodyZone> bodyZoneMap = new HashMap<>();
    bodyZoneMap.put(0, BodyPart.BodyZone.HEAD);
    bodyZoneMap.put(1 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.UPPER_TORSO_FRONT);
    bodyZoneMap.put(2 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.UPPER_TORSO_BACK);
    bodyZoneMap.put(3 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LOWER_TORSO_FRONT);
    bodyZoneMap.put(4 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LOWER_TORSO_BACK);
    bodyZoneMap.put(5 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LEFT_ARM);
    bodyZoneMap.put(6 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.RIGHT_ARM);
    bodyZoneMap.put(7 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LEFT_LEG);
    bodyZoneMap.put(8 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.RIGHT_LEG);
    return bodyZoneMap;
  }

  public static final TreeMap<Integer, BodyPart.BodyZone> above = new TreeMap<>(getNoDirectionZones());

  private static Map<Integer, BodyPart.BodyZone> getAboveZones() {
    Map<Integer, BodyPart.BodyZone> bodyZoneMap = new HashMap<>();
    bodyZoneMap.put(0, BodyPart.BodyZone.HEAD);
    bodyZoneMap.put(1 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.UPPER_TORSO_FRONT);
    bodyZoneMap.put(2 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.UPPER_TORSO_BACK);
    bodyZoneMap.put(3 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LOWER_TORSO_FRONT);
    bodyZoneMap.put(4 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LOWER_TORSO_BACK);
    bodyZoneMap.put(5 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LEFT_ARM);
    bodyZoneMap.put(6 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.RIGHT_ARM);
    bodyZoneMap.put(7 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.LEFT_LEG);
    bodyZoneMap.put(8 * MAX_ROLL / NUMBER_OF_BODY_ZONES, BodyPart.BodyZone.RIGHT_LEG);
    return bodyZoneMap;
  }
}
