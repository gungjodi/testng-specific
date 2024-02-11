package io.github.gungjodi.testngspecificstarter.commons;

import io.github.gungjodi.testngspecificstarter.TestNGMethodParser;
import io.qameta.allure.Allure;
import io.qameta.allure.AllureLifecycle;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.TestResult;
import org.testng.ITestResult;

import java.util.List;

/**
 * @author gungjodi
 * @version $Id: AllureUtil.java, v1.0 2023‐08‐24 04.49 gungjodi Exp $$
 */
public class AllureUtil {
    public static void addParameterToTestResult(TestResult testResult, Parameter parameter) {
        List<Parameter> parameters = testResult.getParameters();
        parameters.add(parameter);
        testResult.setParameters(parameters);
    }

    public static Parameter buildParameter(String paramName, String paramValue) {
        Parameter parameter = new Parameter();
        parameter.setName(paramName);
        parameter.setValue(paramValue);
        return parameter;
    }

    public static void updateCase(ITestResult iTestResult) {
        String caseId = TestNGMethodParser.getCaseNameFromTestNGMethod(iTestResult.getMethod());
        AllureLifecycle allureLifecycle = Allure.getLifecycle();

        allureLifecycle.updateTestCase(allureTestResult -> {
            allureTestResult.setName(caseId);
            AllureUtil.addParameterToTestResult(
                    allureTestResult,
                    AllureUtil.buildParameter("environment", System.getProperty("app.env"))
            );
            AllureUtil.addParameterToTestResult(
                    allureTestResult,
                    AllureUtil.buildParameter(
                            "method",
                            String.format("%s.%s()",
                                    iTestResult.getMethod().getTestClass().getName(),
                                    iTestResult.getName()
                            )
                    )
            );
        });
    }
}

