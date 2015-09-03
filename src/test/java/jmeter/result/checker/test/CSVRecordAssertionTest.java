package jmeter.result.checker.test;

import jmeter.result.checker.ResultChecker;
import jmeter.result.checker.assertion.Assertion;
import jmeter.result.checker.junit.ParameterizedTestNameRunnerFactory;
import org.apache.commons.csv.CSVRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

/**
 * Created by Anton Rustamov (arustamov) on 8/19/2015.
 */
@RunWith(Parameterized.class)
@Parameterized.UseParametersRunnerFactory(ParameterizedTestNameRunnerFactory.class)
public class CSVRecordAssertionTest {

    public static ResultChecker resultChecker = new ResultChecker();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return resultChecker.getCSVRecordAssertionPairs();
    }

    @Parameterized.Parameter(0)
    public CSVRecord csvRecord;

    @Parameterized.Parameter(1)
    public Assertion assertion;

    public CSVRecordAssertionTest() {}

    @Test
    public void testCSVRecordAssertion() {
        assertion.applyTo(csvRecord);
    }
}