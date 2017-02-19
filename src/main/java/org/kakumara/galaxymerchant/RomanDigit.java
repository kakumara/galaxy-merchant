package org.kakumara.galaxymerchant;

import java.util.EnumSet;

import static java.lang.String.format;

public enum RomanDigit {
    I(1), V(5), X(10), L(50), C(100), D(500), M(1000);

    private final int value;

    RomanDigit(int value) {
        this.value = value;
    }

    public int decimalValue() {
        return value;
    }

    public static RomanDigit romanDigitOf(String romanChar) {
        for (RomanDigit romanDigit : EnumSet.allOf(RomanDigit.class)) {
            if (romanDigit.name().equals(romanChar.toUpperCase())) {
                return romanDigit;
            }
        }
        throw new IllegalArgumentException(format("%s not a valid romanDigitOf numeral", romanChar));
    }

    public static boolean isRoman(String romanChar) {
        try {
            romanDigitOf(romanChar);
            return true;
        } catch (IllegalArgumentException e){
            return false;
        }
    }

}
