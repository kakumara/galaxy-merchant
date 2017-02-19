package org.kakumara.galaxymerchant;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RomanNumberValidationsTest {

    @Test(expected = NumberFormatException.class)
    public void shouldNotAllow4ConsecutiveChars() throws Exception {
        RomanNumber.romanNumber("XXXX");
    }

    @Test(expected = NumberFormatException.class)
    public void shouldNotAllow4ConsecutiveChars_2() throws Exception {
        RomanNumber.romanNumber("IIII");
    }

    @Ignore("D,L,V should not be allowed to repeat twice. Need to find the correct regex")
    @Test(expected = NumberFormatException.class)
    public void shouldNotAllow4ConsecutiveChars_3() throws Exception {
        RomanNumber.romanNumber("DD");
    }

    @Test
    public void shouldAllowThreeConsecutiveChars() throws Exception {
        assertThat( RomanNumber.romanNumber("XXX").toInt(), is(30));
    }
}
