package com.donalevans;

import java.util.Arrays;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;

public class Util {

  @NotNull
  public static String formatName(String name) {
    if (name == null || name.isEmpty()) {
      return "";
    }

    return Arrays.stream(name.split("_"))
            .map(String::toLowerCase)
            .map(Util::firstCharToUppercase)
            .collect(Collectors.joining(" "))
            .replaceAll("\\s+", " ");
  }

  @NotNull
  public static String firstCharToUppercase(String word) {
    if (word== null || word.isEmpty()) {
      return "";
    }
    return java.lang.Character.toString(word.charAt(0))
            .toUpperCase()
            .concat(word.substring(1));
  }
}
