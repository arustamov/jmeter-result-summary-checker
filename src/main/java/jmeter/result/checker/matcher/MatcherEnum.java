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
        public <T> void assertMatches(T actual, T expected) {
            Matcher<T> matcher = Matchers.equalTo(expected);
            assertThat(actual, matcher);
        }
    },
    EQUAL_TO_IGNORING_CASE("equalToIgnoringCase") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<String> matcher = Matchers.equalToIgnoringCase(String.valueOf(expected));
            assertThat(String.valueOf(actual), matcher);
        }
    },
    CONTAINS_STRING("containsString") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<String> matcher = Matchers.containsString(String.valueOf(expected));
            assertThat(String.valueOf(actual), matcher);
        }
    },
    STARTS_WITH("startsWith") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<String> matcher = Matchers.startsWith(String.valueOf(expected));
            assertThat(String.valueOf(actual), matcher);
        }
    },
    ENDS_WITH("endsWith") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<String> matcher = Matchers.endsWith(String.valueOf(expected));
            assertThat(String.valueOf(actual), matcher);
        }
    },
    STRING_CONTAINS_IN_ORDER("stringContainsInOrder") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            List<String> stringsInOrder = Arrays.asList(String.valueOf(expected).split(" "));
            Matcher<String> matcher = Matchers.stringContainsInOrder(stringsInOrder);
            assertThat(String.valueOf(actual), matcher);
        }
    },
    GREATER_THAN("greaterThan") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<Double> matcher = Matchers.greaterThan(Double.valueOf(String.valueOf(expected)));
            assertThat(Double.valueOf(String.valueOf(actual)), matcher);
        }
    },
    GREATER_THAN_OR_EQUAL_TO("greaterThanOrEqualTo") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<Double> matcher = Matchers.greaterThanOrEqualTo(Double.valueOf(String.valueOf(expected)));
            assertThat(Double.valueOf(String.valueOf(actual)), matcher);
        }
    },
    LESS_THAN("lessThan") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<Double> matcher = Matchers.lessThan(Double.valueOf(String.valueOf(expected)));
            assertThat(Double.valueOf(String.valueOf(actual)), matcher);
        }
    },
    LESS_THAN_OR_EQUAL_TO("lessThanOrEqualTo") {
        @Override
        public <T> void assertMatches(T actual, T expected) {
            Matcher<Double> matcher = Matchers.lessThanOrEqualTo(Double.valueOf(String.valueOf(expected)));
            assertThat(Double.valueOf(String.valueOf(actual)), matcher);
        }
    };

    private String matcherMethodName;

    MatcherEnum(String methodName) {
        matcherMethodName = methodName;
    }

    public String getMatcherMethodName() {
        return matcherMethodName;
    }

    public abstract <T> void assertMatches(T actual, T expected);

    public static MatcherEnum fromMatcherMethodName(String methodName) {
        for (MatcherEnum m : MatcherEnum.values()) {
            if (methodName.equalsIgnoreCase(m.matcherMethodName)) {
                return m;
            }
        }
        return null;
    }
}
