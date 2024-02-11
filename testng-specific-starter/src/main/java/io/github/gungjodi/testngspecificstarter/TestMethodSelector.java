package io.github.gungjodi.testngspecificstarter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IMethodSelector;
import org.testng.IMethodSelectorContext;
import org.testng.ITestNGMethod;

import java.util.List;

/**
 * Second method selector on test level that will include methods that depended upon selected tests
 * @author gungjodi
 * @version $Id: SuiteMethodSelector.java, v1.0 2023‐08‐19 02.03 gungjodi Exp $$
 */
public class TestMethodSelector implements IMethodSelector {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestMethodSelector.class);

    /**
     * filters package/class/method/caseId to be run from a set of tests (includes/excludes)
     * ex.
     * package -&gt; id.gungj.sdet.test -&gt; will run all tests inside this package
     * class -&gt; id.gungj.sdet.test.ExampleTest -&gt; will run all test methods this class
     * methodName -&gt; testSum -&gt; will run tests with this method name
     * caseId -&gt; TEST_CASE_ID_001 -&gt; will run tests that has TestCaseId annotation with this value
     */
    @Override
    public boolean includeMethod(IMethodSelectorContext context, ITestNGMethod method, boolean isTestMethod) {
        method.setIgnoreMissingDependencies(true);

        if (method.isBeforeTestConfiguration() || method.isAfterTestConfiguration()) {
            LOGGER.warn("@BeforeTest and @AfterTest is not suitable and will not be included on test run, "
                        + "modify them to @BeforeMethod(alwaysRun = true) or @AfterMethod(alwaysRun = true) instead");
            return false;
        }

        if (isTestMethod) {
            if (StringUtils.containsAny(method.getMethodName(), TestCaseHolder.methodDependencies.toArray(new String[0]))) {
                return true;
            }

            return SpecificTestProvider.isIncluded(method);
        }

        return true;
    }

    @Override
    public void setTestMethods(List<ITestNGMethod> testMethods) {}
}

