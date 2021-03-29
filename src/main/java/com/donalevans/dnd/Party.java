package com.donalevans.dnd;

import java.util.HashSet;
import java.util.Objects;

public class Party extends HashSet<Character> {
  private static final long serialVersionUID = 2542378399518900126L;

  private final String name;

  public Party(String name) {
    super();
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name + ", Characters = " + size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Party that = (Party) o;
    if (!Objects.equals(name, that.name)) {
      return false;
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), name);
  }
}
