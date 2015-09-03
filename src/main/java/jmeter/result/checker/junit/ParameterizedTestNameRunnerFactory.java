package jmeter.result.checker.junit;

import jmeter.result.checker.assertion.Assertion;
import org.junit.runner.Runner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.parameterized.BlockJUnit4ClassRunnerWithParameters;
import org.junit.runners.parameterized.ParametersRunnerFactory;
import org.junit.runners.parameterized.TestWithParameters;

import java.util.List;

/**
 * Created by ANRU on 8/21/2015.
 */
public class ParameterizedTestNameRunnerFactory implements ParametersRunnerFactory {
    @Override
    public Runner createRunnerForTestWithParameters(TestWithParameters testWithParameters) throws InitializationError {
        String name = testWithParameters.getName();
        List<Object> parameters = testWithParameters.getParameters();
        for (Object parameter : parameters) {
            if (parameter instanceof Assertion) {
                Assertion assertion = Assertion.class.cast(parameter);
                String description = assertion.getDescription();
                name = description.isEmpty()? assertion.toString() : description;
            }
        }
        final String testName = name;
        return new BlockJUnit4ClassRunnerWithParameters(testWithParameters) {
            protected String testName(FrameworkMethod method) {
                return testName;
            }
        };
    }
}
