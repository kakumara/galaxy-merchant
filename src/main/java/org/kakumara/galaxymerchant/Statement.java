package org.kakumara.galaxymerchant;

import java.util.Arrays;
import java.util.List;

public class Statement {

    private final List<String> tokens;
    private Integer creditValue;
    private final RomanDigit roman;
    private boolean query;

    public static Statement query(String[] tokens) {
        return new Statement(Arrays.asList(tokens), true, null, null);
    }

    public static Statement statement(String[] tokens, Integer creditValue) {
        return new Statement(Arrays.asList(tokens), false, creditValue, null);
    }

    public static Statement statement(String[] tokens, RomanDigit romanValue) {
        return new Statement(Arrays.asList(tokens), false, null, romanValue);
    }

    private Statement(List<String> tokens, boolean isQuery, Integer creditValue, RomanDigit roman) {
        this.query = isQuery;
        this.tokens = tokens;
        this.creditValue = creditValue;
        this.roman = roman;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public boolean isQuery() {
        return query;
    }

    public boolean isRateAdjustment() {
        return creditValue != null;
    }

    public boolean isLiteralUpdate() {
        return roman != null;
    }

    public Integer getCreditValue() {
        return creditValue;
    }

    public RomanDigit getRoman() {
        return roman;
    }
}
