package org.kakumara.galaxymerchant;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Optional.empty;
import static java.util.regex.Pattern.compile;
import static org.kakumara.galaxymerchant.RomanDigit.romanDigitOf;
import static org.kakumara.galaxymerchant.Statement.query;
import static org.kakumara.galaxymerchant.Statement.statement;

public class QueryParser {

    static final Pattern validQueryPattern = compile("how\\s+(many|much)(\\s+credits)?\\s+(is)\\s+(.*)\\s+(\\?)");

    static final Set<Pattern> validStatementPatterns = new HashSet<Pattern>() {{
        add(compile("(.*)\\s+(is)\\s+(i|v|x|l|c|d|m)"));
        add(compile("(.*)\\s+(is)\\s+(\\d+)\\s+(credits)"));
    }};

    public Optional<Statement> parse(String queryString) {
        String formattedInput = queryString.trim().toLowerCase().replaceAll("\\s+", " ");
        Matcher queryMatcher = validQueryPattern.matcher(formattedInput);

        if (queryMatcher.matches()) {
            return Optional.of(query(tokenizeInput(queryMatcher, queryMatcher.groupCount() - 1)));
        }

        for (Pattern pattern : validStatementPatterns) {
            Matcher stmtMatcher = pattern.matcher(formattedInput);
            if (stmtMatcher.matches()) {
                String value = stmtMatcher.group(3);
                String[] tokens = tokenizeInput(stmtMatcher, 1);
                return isNumeric(value)
                        .map(creditValue -> Optional.of(statement(tokens, creditValue)))
                        .orElseGet(() ->
                                RomanDigit.isRoman(value) ?
                                        Optional.of(statement(tokens, romanDigitOf(value))) : empty()
                        );
            }
        }
        return empty();
    }

    private String[] tokenizeInput(Matcher matcher, int groupIdx) {
        return matcher.group(groupIdx).split(" ");
    }

    private Optional<Integer> isNumeric(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return empty();
        }
    }
}
