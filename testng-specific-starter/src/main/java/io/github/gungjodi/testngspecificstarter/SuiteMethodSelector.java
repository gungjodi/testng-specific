package io.github.gungjodi.testngspecificstarter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.IMethodSelector;
import org.testng.IMethodSelectorContext;
import org.testng.ITestNGMethod;

import java.util.Arrays;
import java.util.List;

/**
 * First method selector to get method that depended on other methods
 * @author gungjodi
 * @version $Id: SuiteMethodSelector.java, v1.0 2023‐08‐19 02.03 gungjodi Exp $$
 */
public class SuiteMethodSelector implements IMethodSelector {
    private static final Logger LOGGER = LoggerFactory.getLogger(SuiteMethodSelector.class);

    static {
        SpecificTestProvider.getSpecificTests();
        SpecificTestProvider.getExcludedTests();
    }
    /**
     * filters package/class/method/caseId to be run from a set of tests (includes/excludes)
     * ex.
     * package -> id.gungj.sdet.test -> will run all tests inside this package
     * class -> id.gungj.sdet.test.ExampleTest -> will run all test methods this class
     * methodName -> sumTest -> will run tests with this method name
     * caseId -> TEST_CASE_ID_001 -> will run tests that has TestCaseId annotation with this value
     */
    @Override
    public boolean includeMethod(IMethodSelectorContext context, ITestNGMethod method, boolean isTestMethod) {
        method.setIgnoreMissingDependencies(true);

        if (isTestMethod) {
            String caseName = TestNGMethodParser.getCaseNameFromTestNGMethod(method);
            TestCaseHolder.addCaseNameToAll(caseName);
            boolean isIncluded = SpecificTestProvider.isIncluded(method);

            if (method.isBeforeTestConfiguration() || method.isAfterTestConfiguration()) {
                LOGGER.warn("@BeforeTest and @AfterTest is not suitable and will not be included on test run, "
                            + "modify them to @BeforeMethod(alwaysRun = true) or @AfterMethod(alwaysRun = true) instead");
                return false;
            }

            if (isIncluded) {
                List<String> methodDependencies = Arrays.asList(method.getMethodsDependedUpon());
                methodDependencies.forEach(methodName -> {
                    String[] methodNameSplit = methodName.split("\\.");
                    String depends = methodNameSplit[methodNameSplit.length - 1];
                    TestCaseHolder.methodDependencies.add(depends);
                });
            }
            return isIncluded;
        }

        return true;
    }

    @Override
    public void setTestMethods(List<ITestNGMethod> testMethods) {}
}

