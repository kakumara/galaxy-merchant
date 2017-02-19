package org.kakumara.galaxymerchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.kakumara.galaxymerchant.RomanNumber.romanNumber;

public class Learner {

    static final ConcurrentMap<RomanDigit, List<String>> ROMAN_DIGIT_LITERALS = new ConcurrentHashMap<>();
    static final ConcurrentMap<String, Double> METAL_PRICES = new ConcurrentHashMap<>();

    public void feed(Statement statement) {
        if (statement.isLiteralUpdate()) {
            updateDigitLiterals(statement.getRoman(), statement.getTokens().get(0));
        } else if (statement.isRateAdjustment()) {
            updateMetalPrices(statement.getTokens(), statement.getCreditValue());
        }
    }

    public Optional<String> ask(Statement query) {
        if(query.isQuery()){
            List<String> tokensWithMetal = query.getTokens();
            String metal = tokensWithMetal.get(tokensWithMetal.size() - 1);
            String romanString = makeRomanString(tokensWithMetal);
            Integer noOfUnits = romanNumber(romanString).toInt();
            Double price = METAL_PRICES.get(metal) * noOfUnits;
            return  Optional.of(String.valueOf(price.intValue()));
        } else {
            return Optional.empty();
        }
    }

    private  void updateMetalPrices(List<String> tokensWithMetal, Integer creditForUnits) {
        String metal = tokensWithMetal.get(tokensWithMetal.size() - 1);
        String romanString = makeRomanString(tokensWithMetal);

        Integer noOfUnits = romanNumber(romanString).toInt();
        METAL_PRICES.put(metal, (double) (creditForUnits / noOfUnits));
    }

    private String makeRomanString(List<String> tokensWithMetal) {
        List<String> tokens = tokensWithMetal.subList(0, tokensWithMetal.size() - 1);
        StringBuffer romanString = new StringBuffer();

        tokens.stream().flatMap(token ->
                ROMAN_DIGIT_LITERALS.entrySet().stream()
                        .filter(literalEntry -> literalEntry.getValue().contains(token))
                        .map(entry -> entry.getKey().name()))
                .forEach(literal -> romanString.append(literal));
        return romanString.toString();
    }

    private void updateDigitLiterals(RomanDigit roman, String literal) {
        List<String> literals = ROMAN_DIGIT_LITERALS.getOrDefault(roman, new ArrayList<>());
        literals.add(literal);
        ROMAN_DIGIT_LITERALS.put(roman, literals);
    }
}
