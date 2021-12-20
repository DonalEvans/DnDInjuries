package com.donalevans.dnd;

import java.util.Objects;

public class CustomInjury extends Injury {
  private static final long serialVersionUID = -6800234705000271661L;
  private final String description;
  private final String name;

  public CustomInjury(String name, String description) {
    this.name = name;
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    CustomInjury that = (CustomInjury) o;
    return Objects.equals(description, that.description) && Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), description, name);
  }
}
