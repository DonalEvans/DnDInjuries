package com.donalevans.dnd;

import java.util.Arrays;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static com.donalevans.dnd.Util.firstCharToUppercase;
import static com.donalevans.dnd.Util.formatName;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Enclosed.class)
public class UtilTest {

  @RunWith(Parameterized.class)
  public static class FormatNameTest {
    @Parameterized.Parameters(name = "{index}: input={0}; expected={1}")
    public static Iterable<Object[]> testStrings() {
      return Arrays.asList(
          new Object[][] {
            {"TEST_NAME", "Test Name"},
            {"test_name", "Test Name"},
            {"test__name", "Test Name"},
            {"TESTNAME", "Testname"},
            {"testname", "Testname"},
            {"$testname", "$testname"},
            {"T_t", "T T"},
            {"T_", "T"},
            {"T", "T"},
            {"t", "T"},
            {" ", " "},
            {"    ", " "},
            {"_", ""},
            {"__", ""},
            {"", ""},
            {null, ""}
          });
    }

    @Parameterized.Parameter public String inputString;

    @Parameterized.Parameter(1)
    public String expectedString;

    @Test
    @Parameterized.Parameters()
    public void formatNameReturnsCorrectlyFormattedName() {
      assertThat(formatName(inputString)).isEqualTo(expectedString);
    }
  }

  @RunWith(Parameterized.class)
  public static class FirstCharToUppercaseTest {
    @Parameterized.Parameters(name = "{index}: input={0}; expected={1}")
    public static Iterable<Object[]> testStrings() {
      return Arrays.asList(
          new Object[][] {
            {"TESTNAME", "TESTNAME"},
            {"testname", "Testname"},
            {"$testname", "$testname"},
            {"T_t", "T_t"},
            {"T", "T"},
            {"t", "T"},
            {" ", " "},
            {"    ", "    "},
            {"!", "!"},
            {"", ""},
            {null, ""}
          });
    }

    @Parameterized.Parameter public String inputString;

    @Parameterized.Parameter(1)
    public String expectedString;

    @Test
    public void firstCharToUppercaseReturnsCorrectly() {
      assertThat(firstCharToUppercase(inputString)).isEqualTo(expectedString);
    }
  }
}
