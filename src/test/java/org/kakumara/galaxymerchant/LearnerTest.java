package org.kakumara.galaxymerchant;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.kakumara.galaxymerchant.RomanDigit.*;

public class LearnerTest {

    private Learner learner;

    @Before
    public void before() throws Exception {
        learner = new Learner();
        learner.feed(Statement.statement(new String[]{"aaa"}, I));
        learner.feed(Statement.statement(new String[]{"bbb"}, V));
        learner.feed(Statement.statement(new String[]{"ccc"}, X));
        learner.feed(Statement.statement(new String[]{"ddd"}, L));
        learner.feed(Statement.statement(new String[]{"fff"}, C));
        learner.feed(Statement.statement(new String[]{"ggg"}, D));
        learner.feed(Statement.statement(new String[]{"hhh"}, M));
    }

    @Test
    public void shouldFeedIn_RateAdjustments_forValidStatements() throws Exception {

        Statement rateAdjustment = Statement.statement(new String[]{"ccc", "aaa", "aaa", "Silver"}, 1200);
        learner.feed(rateAdjustment);

        assertThat(learner.METAL_PRICES.get("Silver"), is(100.0));
    }

    @Test
    public void shouldDetermineCorrectMetalPrice() throws Exception {
        Statement rateAdjustment = Statement.statement(new String[]{"ccc", "aaa", "aaa", "Silver"}, 1200);
        learner.feed(rateAdjustment);

        Optional<String> answer = learner.ask(Statement.query(new String[]{"fff", "aaa", "bbb", "Silver"}));
        assertTrue(answer.isPresent());
        assertThat(answer.get(), is("10400"));
    }

    @Test
    public void shouldNotAnswerIfNotUnderstood() throws Exception {

        Optional<String> answer = learner.ask(Statement.statement(new String[]{"fff", "aaa", "bbb", "Silver"}, 12));

        assertFalse(answer.isPresent());
    }
}
