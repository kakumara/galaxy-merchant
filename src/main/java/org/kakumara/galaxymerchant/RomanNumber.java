package org.kakumara.galaxymerchant;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static java.util.Arrays.copyOf;
import static org.kakumara.galaxymerchant.RomanDigit.*;

public class RomanNumber {

    public static RomanNumber romanNumber(String romanString) {
        return new RomanNumber(romanString);
    }

private static final Pattern CONSECUTIVE_CHARS = Pattern.compile("(.*)\\1\\1\\1");

    private static final Map<RomanDigit, RomanDigit> VALID_PRE_DIGITS = new HashMap<RomanDigit, RomanDigit>() {{
        put(V, I);
        put(X, I);
        put(L, X);
        put(C, X);
        put(D, C);
        put(M, C);
    }};
    private final String romanString;

    RomanNumber(String romanString) {
        if(CONSECUTIVE_CHARS.matcher(romanString).matches()){
            throw new NumberFormatException("You cannot have 4 consecutive roman literals");
        }
        this.romanString = romanString;
    }

    public Integer toInt() {
        return convert(romanString.toCharArray(), 0);
    }

    private Integer convert(char[] romanChars, int total) {
        if (romanChars.length == 1) {
            char lastChar = romanChars[0];
            return total + romanDigitOf(String.valueOf(lastChar)).decimalValue();
        } else {
            RomanDigit lastRoman = romanDigitOf(String.valueOf(romanChars[romanChars.length - 1]));
            RomanDigit prevRoman = romanDigitOf(String.valueOf(romanChars[romanChars.length - 2]));

            if (prevRoman.equals(VALID_PRE_DIGITS.get(lastRoman))) {
                total += (lastRoman.decimalValue() - prevRoman.decimalValue());
                if (romanChars.length == 2) {
                    return total;
                } else {
                    return convert(copyOf(romanChars, romanChars.length - 2), total);
                }
            }
            return convert(copyOf(romanChars, romanChars.length - 1), total + lastRoman.decimalValue());
        }
    }
}
