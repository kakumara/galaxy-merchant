package org.kakumara.galaxymerchant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.kakumara.galaxymerchant.RomanNumber.romanNumber;


@RunWith(Parameterized.class)
public class RomanNumberTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"I", 1},
                {"II", 2},
                {"X", 10},
                {"VIII", 8},
                {"XII", 12},
                {"IX", 9},
                {"IV", 4},
                {"XXX", 30},
                {"XLIV", 44},
                {"XLVI", 46},
                {"XC", 90},
                {"D", 500},
                {"CD", 400},
                {"CM", 900},
                {"CMXCIX", 999},
                {"MCIII", 1103},
                {"MMM", 3000}
        });
    }

    @Parameterized.Parameter
    public String roman;

    @Parameterized.Parameter(1)
    public Integer decimal;

    @Test
    public void shouldConvertRomanToDecimal() throws Exception {
        assertThat(romanNumber(roman).toInt(), is(decimal));
    }
}

