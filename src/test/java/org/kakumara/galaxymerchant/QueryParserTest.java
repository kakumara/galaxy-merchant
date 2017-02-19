package org.kakumara.galaxymerchant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class QueryParserTest {

    static final Boolean IS_A_QUERY = TRUE;
    static final Boolean VALID = TRUE;
    static final Boolean INVALID = FALSE;
    static final Boolean IS_A_STATEMENT = FALSE;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"glob  is   I", VALID, IS_A_STATEMENT, 1},
                {"prok   is   V", VALID, IS_A_STATEMENT, 1},
                {"pish is X", VALID, IS_A_STATEMENT, 1},
                {"tegj is L", VALID, IS_A_STATEMENT, 1},
                {"glob  glob  Silver  is 34  Credits ", VALID, IS_A_STATEMENT, 3},
                {"glob prok Gold is 57800 Credits", VALID, IS_A_STATEMENT, 3},
                {"pish pish Iron is 3910 Credits", VALID, IS_A_STATEMENT, 3},
                {"how much is pish tegj glob glob ?", VALID, IS_A_QUERY, 4},
                {"how much is pish tegj glob glob ?", VALID, IS_A_QUERY, 4},
                {"how   many Credits is glob prok Silver ?", VALID, IS_A_QUERY, 3},
                {"how many Credits is glob prok Gold ?", VALID, IS_A_QUERY, 3},
                {"how many Credits is glob prok Iron ?", VALID, IS_A_QUERY, 3},
                {"how much wood could a woodchuck chuck if a woodchuck could chuck wood ?", INVALID, FALSE, 0}
        });
    }

    @Parameter
    public String statement;

    @Parameter(1)
    public Boolean expectedValid;

    @Parameter(2)
    public Boolean isQuery;

    @Parameter(3)
    public Integer expectedNoOfTokens;

    @Test
    public void shouldParseQueriesCorrectly() throws Exception {
        QueryParser queryParser = new QueryParser();
        Optional<Statement> statementOptional = queryParser.parse(statement);
        if (expectedValid) {
            assertTrue(statementOptional.isPresent());
            assertThat(statementOptional.get().isQuery(), is(isQuery));
            assertThat(statementOptional.get().getTokens().size(), is(expectedNoOfTokens));
        } else {
            assertFalse(statementOptional.isPresent());
        }
    }

}
