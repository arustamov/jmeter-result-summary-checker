package jmeter.result.checker.matcher;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Anton Rustamov (arustamov) on 7/31/2015.
 */
public enum MatcherEnum {
    EQUAL_TO("equalTo") {
        @Override
        public void assertMatches(String actual, String expected) {
            Matcher<String> matcher = Matchers.equalTo(expected);
            assertThat(actual, matcher);
        }
    },
    EQUAL_TO_IGNORING_CASE("equalToIgnoringCase") {
        @Override
        public void assertMatches(String actual, String expected) {
            Matcher<String> matcher = Matchers.equalToIgnoringCase(expected);
            assertThat(actual, matcher);
        }
    },
    CONTAINS_STRING("containsString") {
        @Override
        public void assertMatches(String actual, String expected) {
            Matcher<String> matcher = Matchers.containsString(expected);
            assertThat(actual, matcher);
        }
    },
    STARTS_WITH("startsWith") {
        @Override
        public void assertMatches(String actual, String expected) {
            Matcher<String> matcher = Matchers.startsWith(expected);
            assertThat(actual, matcher);
        }
    },
    ENDS_WITH("endsWith") {
        @Override
        public void assertMatches(String actual, String expected) {
            Matcher<String> matcher = Matchers.endsWith(expected);
            assertThat(actual, matcher);
        }
    },
    STRING_CONTAINS_IN_ORDER("stringContainsInOrder") {
        @Override
        public void assertMatches(String actual, String expected) {
            List<String> stringsInOrder = Arrays.asList(expected.split(" "));
            Matcher<String> matcher = Matchers.stringContainsInOrder(stringsInOrder);
            assertThat(actual, matcher);
        }
    },
    GREATER_THAN("greaterThan") {
        @Override
        public void assertMatches(String actual, String expected) {
            actual = actual.replaceAll("%","");
            expected = expected.replaceAll("%","");
            Matcher<Double> matcher = Matchers.greaterThan(Double.valueOf(expected));
            assertThat(Double.valueOf(actual), matcher);
        }
    },
    GREATER_THAN_OR_EQUAL_TO("greaterThanOrEqualTo") {
        @Override
        public void assertMatches(String actual, String expected) {
            actual = actual.replaceAll("%","");
            expected = expected.replaceAll("%","");
            Matcher<Double> matcher = Matchers.greaterThanOrEqualTo(Double.valueOf(expected));
            assertThat(Double.valueOf(actual), matcher);
        }
    },
    LESS_THAN("lessThan") {
        @Override
        public void assertMatches(String actual, String expected) {
            actual = actual.replaceAll("%","");
            expected = expected.replaceAll("%","");
            Matcher<Double> matcher = Matchers.lessThan(Double.valueOf(expected));
            assertThat(Double.valueOf(actual), matcher);
        }
    },
    LESS_THAN_OR_EQUAL_TO("lessThanOrEqualTo") {
        @Override
        public void assertMatches(String actual, String expected) {
            actual = actual.replaceAll("%","");
            expected = expected.replaceAll("%","");
            Matcher<Double> matcher = Matchers.lessThanOrEqualTo(Double.valueOf(expected));
            assertThat(Double.valueOf(actual), matcher);
        }
    };

    private String matcherMethodName;

    MatcherEnum(String methodName) {
        matcherMethodName = methodName;
    }

    public String getMatcherMethodName() {
        return matcherMethodName;
    }

    public abstract void assertMatches(String actual, String expected);

    public static MatcherEnum fromMatcherMethodName(String methodName) {
        for (MatcherEnum m : MatcherEnum.values()) {
            if (methodName.equalsIgnoreCase(m.matcherMethodName)) {
                return m;
            }
        }
        return null;
    }
}
