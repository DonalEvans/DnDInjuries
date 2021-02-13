package com.donalevans;

import java.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class InjuryTypeTest {

  @Parameterized.Parameters(name= "{index}: input={0}; expected={1}")
  public static Iterable<Object[]> testStrings() {
    return Arrays.asList(new Object[][] {
            { "TEST_NAME", "Test Name" },
            { "test_name", "Test Name" },
            { "test__name", "Test Name" },
            { "TESTNAME", "Testname" },
            { "testname", "Testname" },
            { "$testname", "$testname" },
            { "T_t", "T T" },
            { "T_", "T" },
            { "T", "T" },
            { "t", "T" },
            { " ", " " },
            { "    ", " " },
            { "_", "" },
            { "__", "" },
            { "", "" },
            { null, ""}
    });
  }

  @Parameterized.Parameter
  public String inputString;

  @Parameterized.Parameter(1)
  public String expectedString;

  @Test
  @Parameterized.Parameters()
  public void getNameFormattedReturnsCorrectlyFormattedName() {
    final String formattedString = InjuryType.UNHARMED.formatName(inputString);
    assertThat(formattedString, equalTo(expectedString));
  }
}
