package jmeter.result.checker.assertion;

import jmeter.result.checker.matcher.MatcherEnum;
import org.apache.commons.csv.CSVRecord;

/**
 * Created by Anton Rustamov (arustamov) on 7/31/2015.
 */
public class Assertion {

    private static final String PART_DELIM = ":";

    private String description;

    private String keyHeaderName;
    private MatcherEnum keyMatcher;
    private String keyHeaderValue;

    private String assertHeaderName;
    private MatcherEnum assertMatcher;
    private String assertHeaderValue;

    public Assertion() {}

    public static Assertion fromString(String assertionString) {
        String[] parts = assertionString.replaceAll("\"","").split(PART_DELIM);
        if (parts.length != 7) {
            throw new RuntimeException("Incorrect assertion string format");
        }
        Assertion assertion = new Assertion();
        assertion.setDescription(parts[0]);
        assertion.setKeyHeaderName(parts[1]);
        assertion.setKeyMatcher(MatcherEnum.fromMatcherMethodName(parts[2]));
        assertion.setKeyHeaderValue(parts[3]);
        assertion.setAssertHeaderName(parts[4]);
        assertion.setAssertMatcher(MatcherEnum.fromMatcherMethodName(parts[5]));
        assertion.setAssertHeaderValue(parts[6]);
        return assertion;
    }

    public boolean appliesTo(CSVRecord csvRecord) {
        String keyHeaderValue = csvRecord.get(keyHeaderName);
        try {
            keyMatcher.assertMatches(keyHeaderValue, this.keyHeaderValue);
            return true;
        }
        catch (AssertionError error) {
            return false;
        }
    }

    public void applyTo(CSVRecord csvRecord) {
        String assertHeaderValue = csvRecord.get(assertHeaderName);
        assertMatcher.assertMatches(assertHeaderValue, this.assertHeaderValue);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeyHeaderName() {
        return keyHeaderName;
    }

    public void setKeyHeaderName(String keyHeaderName) {
        this.keyHeaderName = keyHeaderName;
    }

    public MatcherEnum getKeyMatcher() {
        return keyMatcher;
    }

    public void setKeyMatcher(MatcherEnum keyMatcher) {
        this.keyMatcher = keyMatcher;
    }

    public String getKeyHeaderValue() {
        return keyHeaderValue;
    }

    public void setKeyHeaderValue(String keyHeaderValue) {
        this.keyHeaderValue = keyHeaderValue;
    }

    public String getAssertHeaderName() {
        return assertHeaderName;
    }

    public void setAssertHeaderName(String assertHeaderName) {
        this.assertHeaderName = assertHeaderName;
    }

    public MatcherEnum getAssertMatcher() {
        return assertMatcher;
    }

    public void setAssertMatcher(MatcherEnum assertMatcher) {
        this.assertMatcher = assertMatcher;
    }

    public String getAssertHeaderValue() {
        return assertHeaderValue;
    }

    public void setAssertHeaderValue(String assertHeaderValue) {
        this.assertHeaderValue = assertHeaderValue;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(keyHeaderName)
                .append(PART_DELIM)
                .append(keyMatcher.getMatcherMethodName())
                .append(PART_DELIM)
                .append(keyHeaderValue)
                .append(PART_DELIM)
                .append(assertHeaderName)
                .append(PART_DELIM)
                .append(assertMatcher.getMatcherMethodName())
                .append(PART_DELIM)
                .append(assertHeaderValue)
                .toString();
    }
}
